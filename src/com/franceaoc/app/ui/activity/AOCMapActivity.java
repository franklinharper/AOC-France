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
import com.franceaoc.app.Constants;
import com.franceaoc.app.R;
import com.franceaoc.app.map.CommuneOverlayItem;
import com.franceaoc.app.map.CommuneOverlay;
import com.franceaoc.app.map.POIOverlayResources;
import com.franceaoc.app.model.Commune;
import com.franceaoc.app.service.CommuneService;
import com.google.android.maps.*;
import java.util.List;

/**
 *
 * @author pierre
 */
public class AOCMapActivity extends MapActivity implements CommuneOverlay.OnTapListener
{
    private static final int ZOOM_DEFAULT = 15;
    private static final boolean MODE_SATELLITE = false;

    private MapView mMapView;
    private MapController mMapController;
    private LocationManager mLocationManager;
    private GeoPoint mPoint;
    private List<Overlay> mMapOverlays;
    private CommuneOverlay mItemizedOverlay;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.map_activity );

        mMapView = (MapView) findViewById( R.id.mapview );
        mMapView.setBuiltInZoomControls(true);
        mMapController = mMapView.getController();
        mMapController.setZoom( getZoom());
        mMapView.setSatellite( getSatellite() );
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        
        if( Constants.DEMO )
        {
            double lat = 47.07;
            double lon = 4.88;
            mPoint = convertLatLon(lat, lon);
        }
        else
        {
            String provider = mLocationManager.getBestProvider(criteria, true);
            Location location = mLocationManager.getLastKnownLocation(provider);
            if (location != null)
            {
                mPoint = convertGeoPoint(location);
            }
        }
        mMapController.animateTo(mPoint);

        setPOIOverlay();

        mMapView.invalidate();
    }

    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }


    protected int getZoom()
    {
        return ZOOM_DEFAULT;
    }
    
    protected boolean getSatellite()
    {
        return MODE_SATELLITE;
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

    private void setPOIOverlay()
    {
        mMapOverlays = mMapView.getOverlays();
        Drawable marker = getResources().getDrawable( R.drawable.wine_marker );
        
        POIOverlayResources res = new POIOverlayResources();
        res.setBottomOffset( 50 );
        res.setCloseId(R.id.balloon_close);
        res.setInnerLayoutId(R.id.balloon_inner_layout);
        res.setItemSnippetId(R.id.balloon_item_snippet);
        res.setItemTitleId(R.id.balloon_item_title);
        res.setLayout(R.layout.balloon_overlay);
        res.setMainLayoutId(R.id.balloon_main_layout);
        
        mItemizedOverlay = new CommuneOverlay(marker , mMapView , res , this );

        for (Commune commune : CommuneService.getNearestCommunes() )
        {
            GeoPoint point = new GeoPoint( (int) (commune.getLatitude() * 1E6) , (int) (commune.getLongitude() * 1E6) );
            mItemizedOverlay.addOverlay( new CommuneOverlayItem( point , commune.getTitle() , commune.getDesciption(), commune.getId()));
        }
        mMapOverlays.add(mItemizedOverlay);

        mMapView.postInvalidate();
    }

    public void onTap(String ci)
    {
        startCommuneActivity( ci );
    }
    
    private void startCommuneActivity( String ci )
    {
        Intent intent = new Intent(Constants.ACTION_COMMUNE_AOC);
        intent.putExtra(Constants.EXTRA_COMMUNE_CI, ci );

        startActivity(intent);
    }



}
