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
package com.franceaoc.app.service;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import com.franceaoc.app.model.POI;

/**
 *
 * @author pierre
 */
public class LocationService
{

    private static Location mLocation;

    /**
     * Gets the location
     * @param context The context
     * @return The location
     */
    public static Location getLocation(Context context)
    {
        if (mLocation == null)
        {
            mLocation = getDefaultLocation(context);
        }
        return mLocation;
    }

    /**
     * Sets the location
     * @param location The location
     */
    public static void setLocation(Location location)
    {
        mLocation = location;
    }

    private static Location getDefaultLocation(Context context)
    {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        String bestProvider = manager.getBestProvider(criteria, true);
        Location location = null;
        if( bestProvider != null )
        {
            location = manager.getLastKnownLocation(bestProvider);
        }
        else
        {
            // Try with a coarse accuracy
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            bestProvider = manager.getBestProvider(criteria, true);
            if( bestProvider != null )
            {
                location = manager.getLastKnownLocation(bestProvider);
            }
        }
        return location;

    }

    public static long getDistance(POI poi, double latitude, double longitude)
    {
        float[] dist = new float[3];
        Location.distanceBetween( poi.getLatitude(), poi.getLongitude(), latitude, longitude, dist );
        return (long) dist[0];
    }
}
