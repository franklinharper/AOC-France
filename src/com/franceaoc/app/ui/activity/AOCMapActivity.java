/* Copyright (c) 2012 Michele Roohani, Frank Harper, Pierre Gros, Pierre LEVY
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.franceaoc.app.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.franceaoc.app.Constants;
import com.franceaoc.app.R;
import com.franceaoc.app.map.CommuneOverlay;
import com.franceaoc.app.map.POIMapView;
import com.franceaoc.app.map.POIOverlayResources;
import com.google.android.maps.*;
import java.util.List;

/**
 *
 * @author pierre
 */
public class AOCMapActivity extends MapActivity implements CommuneOverlay.OnTapListener, POIMapView.OnPanChangeListener
{

    private static final int ZOOM_GLOBAL = 10;
    private static final int ZOOM_FOCUSED = 13;
    private static final boolean MODE_SATELLITE = false;
    private static final int UPDATE = 1;
    private POIMapView mMapView;
    private MapController mMapController;
    private LocationManager mLocationManager;
    private GeoPoint mMapCenter;
    private List<Overlay> mMapOverlays;
    private CommuneOverlay mItemizedOverlay;
    private int mZoom;
    private Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        double intentLatitude = getIntent().getDoubleExtra(Constants.EXTRA_POINT_LAT, 0.0);
        double intentLongitude = getIntent().getDoubleExtra(Constants.EXTRA_POINT_LON, 0.0);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mZoom = ZOOM_GLOBAL;

        if ((intentLatitude != 0.0) && (intentLongitude != 0.0))
        {
            // the activity has been lauched with a commune's coordinate in extras
            mMapCenter = convertLatLon(intentLatitude, intentLongitude);
            mZoom = ZOOM_FOCUSED;

        }
        else if (Constants.DEMO)
        {
            double lat = 47.07;
            double lon = 4.88;
            mMapCenter = convertLatLon(lat, lon);
        }
        else
        {
            String provider = mLocationManager.getBestProvider(criteria, true);
            Location location = mLocationManager.getLastKnownLocation(provider);
            if (location != null)
            {
                mMapCenter = convertGeoPoint(location);
            }
        }
        mMapView = (POIMapView) findViewById(R.id.mapview);
        mMapView.setBuiltInZoomControls(true);
        mMapController = mMapView.getController();
        mMapController.setZoom(mZoom);
        mMapView.setSatellite(MODE_SATELLITE);
        mMapView.setOnPanChangeListener(this);

        mMapController.animateTo(mMapCenter);
        mHandler = new UIHandler();

        initOverlay();

        mMapView.invalidate();



    }

    public void onTap(String ci)
    {
        startCommuneActivity(ci);
    }

    public void onPanChange(GeoPoint newCenter, GeoPoint oldCenter)
    {

        mMapCenter = newCenter;

        if (distance(newCenter, oldCenter) > 3000.0)
        {
            updateUI();
            Log.i(Constants.TAG, "Rebuild overlay");
        }
    }

    private void updateUI()
    {
        Message msg = new Message();
        msg.what = UPDATE;
        mHandler.sendMessage(msg);
    }

    private long calculateRadius()
    {
        GeoPoint center = mMapView.getMapCenter();
        GeoPoint a = new GeoPoint( center.getLatitudeE6() + ( mMapView.getLatitudeSpan() / 2 ), center.getLongitudeE6() + ( mMapView.getLongitudeSpan() / 2 ));
        GeoPoint b = new GeoPoint( center.getLatitudeE6() - ( mMapView.getLatitudeSpan() / 2 ), center.getLongitudeE6() - ( mMapView.getLongitudeSpan() / 2 ));
        return (long) ( distance( a , b ) / 2 );
    }

    class UIHandler extends Handler
    {
 
        @Override
        public void handleMessage(Message msg)
        {
            if( msg.what == UPDATE )
            {
                    updateOverlay();
            }
        }
    }

    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }

    @Override
    protected void onPause()
    {
        mMapView.cancelUpdateTask();
        super.onPause();
    }

    private GeoPoint convertGeoPoint(Location location)
    {
        if (location != null)
        {
            return convertLatLon(location.getLatitude(), location.getLongitude());
        }
        return null;

    }

    private GeoPoint convertLatLon(double latitude, double longitude)
    {
        int lat = (int) (latitude * 1E6);
        int lng = (int) (longitude * 1E6);
        return new GeoPoint(lat, lng);

    }
    private void initOverlay()
    {
        mMapOverlays = mMapView.getOverlays();
        Drawable marker = getResources().getDrawable(R.drawable.wine_marker);

        POIOverlayResources res = new POIOverlayResources();
        res.setBottomOffset(50);
        res.setCloseId(R.id.balloon_close);
        res.setInnerLayoutId(R.id.balloon_inner_layout);
        res.setItemSnippetId(R.id.balloon_item_snippet);
        res.setItemTitleId(R.id.balloon_item_title);
        res.setLayout(R.layout.balloon_overlay);
        res.setMainLayoutId(R.id.balloon_main_layout);

        mItemizedOverlay = new CommuneOverlay(marker, mMapView, res, this);
        mMapOverlays.add(mItemizedOverlay);
        updateOverlay();
        
    }
    
    private void updateOverlay()
    {
        long radius = calculateRadius();
        int inRadius = mItemizedOverlay.updateOverlay(this, mMapCenter , radius );
        if( inRadius == Constants.MAX_POI_MAP )
        {
            Log.d(Constants.TAG, "Radius = " + radius +" In radius : " + inRadius );
            String msg = String.format( getString(R.string.message_too_many_markers ) , Constants.MAX_POI_MAP );
            Toast.makeText(this, msg , Toast.LENGTH_LONG).show();
        }
        mMapView.postInvalidate();
    }

    private void startCommuneActivity(String ci)
    {
        Intent intent = new Intent(Constants.ACTION_COMMUNE_AOC);
        intent.putExtra(Constants.EXTRA_COMMUNE_CI, ci);

        startActivity(intent);
    }

    private float distance(GeoPoint p1, GeoPoint p2)
    {
        float[] results = new float[3];
        Location.distanceBetween(p1.getLatitudeE6() / 1E6, p1.getLongitudeE6() / 1E6, p2.getLatitudeE6() / 1E6, p2.getLongitudeE6() / 1E6, results);
        return results[0];
    }
}
