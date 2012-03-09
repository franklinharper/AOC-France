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

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import com.franceaoc.app.Constants;
import com.franceaoc.app.R;
import com.franceaoc.app.ar.CommuneMarker;
import com.franceaoc.app.ar.CommunesDataSource;
import com.jwetherell.augmented_reality.activity.AugmentedReality;
import com.jwetherell.augmented_reality.data.ARData;
import com.jwetherell.augmented_reality.data.NetworkDataSource;
import com.jwetherell.augmented_reality.ui.Marker;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.*;

/**
 *
 * @author pierre
 */
public class ARActivity extends AugmentedReality
{

    private static final String locale = Locale.getDefault().getLanguage();
    private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
    private static final ThreadPoolExecutor exeService = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, queue);
    private static final Map<String, NetworkDataSource> sources = new ConcurrentHashMap<String, NetworkDataSource>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Local
        CommunesDataSource localData = new CommunesDataSource( this , R.drawable.wine_marker );
        
        ARData.addMarkers(localData.getMarkers());

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onStart()
    {
        super.onStart();

        Location last = ARData.getCurrentLocation();
        
        if( Constants.DEMO )
        {
            forceDemoLocation();
        }
        else
        {
            updateData(last.getLatitude(), last.getLongitude(), last.getAltitude());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLocationChanged(Location location)
    {
        super.onLocationChanged(location);

        if( Constants.DEMO )
        {
            forceDemoLocation();
        }
        else
        {
            updateData(location.getLatitude(), location.getLongitude(), location.getAltitude());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void markerTouched(Marker marker)
    {
        CommuneMarker cm = (CommuneMarker) marker; 
        startCommuneActivty( cm.getCI() );
    }
    
    private void startCommuneActivty( String ci )
    {
        Intent intent = new Intent(Constants.ACTION_COMMUNE_AOC);
        intent.putExtra(Constants.EXTRA_COMMUNE_CI, ci );

        startActivity(intent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void updateDataOnZoom()
    {
        super.updateDataOnZoom();
        Location last = ARData.getCurrentLocation();
        if( Constants.DEMO )
        {
            forceDemoLocation();
        }
        else
        {
            updateData(last.getLatitude(), last.getLongitude(), last.getAltitude());
        }
    }

    private void updateData(final double lat, final double lon, final double alt)
    {
        try
        {
            exeService.execute(
                    new Runnable()
                    {

                        @Override
                        public void run()
                        {
                            for (NetworkDataSource source : sources.values())
                            {
                                download(source, lat, lon, alt);
                            }
                        }
                    });
        }
        catch (RejectedExecutionException rej)
        {
            Log.w( Constants.TAG, "Not running new download Runnable, queue is full.");
        }
        catch (Exception e)
        {
            Log.e( Constants.TAG, "Exception running download Runnable.", e);
        }
    }

    private static boolean download(NetworkDataSource source, double lat, double lon, double alt)
    {
        if (source == null)
        {
            return false;
        }

        String url;
        try
        {
            url = source.createRequestURL(lat, lon, alt, ARData.getRadius(), locale);
        }
        catch (NullPointerException e)
        {
            return false;
        }

        List<Marker> markers;
        try
        {
            markers = source.parse(url);
        }
        catch (NullPointerException e)
        {
            return false;
        }

        ARData.addMarkers(markers);
        return true;
    }

    private void forceDemoLocation()
    {
        Location location = new Location("ALT");
        location.setLatitude(Constants.DEMO_LAT);
        location.setLongitude(Constants.DEMO_LON);
        ARData.setCurrentLocation( location );
        updateData( Constants.DEMO_LAT, Constants.DEMO_LON, Constants.DEMO_ALT );
    }

    
}
