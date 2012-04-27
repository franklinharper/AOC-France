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
package com.franceaoc.app.map;

import android.location.Location;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

/**
 *
 * @author Pierre Levy
 */
public class GeoUtils
{
    public static GeoPoint convertGeoPoint(Location location)
    {
        if (location != null)
        {
            return convertLatLon(location.getLatitude(), location.getLongitude());
        }
        return null;

    }

    public static GeoPoint convertLatLon(double latitude, double longitude)
    {
        int lat = (int) (latitude * 1E6);
        int lng = (int) (longitude * 1E6);
        return new GeoPoint(lat, lng);

    }

    public static long calculateRadius( MapView mapView )
    {
        GeoPoint center = mapView.getMapCenter();
        GeoPoint a = new GeoPoint( center.getLatitudeE6() + ( mapView.getLatitudeSpan() / 2 ), center.getLongitudeE6() + ( mapView.getLongitudeSpan() / 2 ));
        GeoPoint b = new GeoPoint( center.getLatitudeE6() - ( mapView.getLatitudeSpan() / 2 ), center.getLongitudeE6() - ( mapView.getLongitudeSpan() / 2 ));
        return (long) ( distance( a , b ) / 2 );
    }

    public static float distance(GeoPoint p1, GeoPoint p2)
    {
        float[] results = new float[3];
        Location.distanceBetween(p1.getLatitudeE6() / 1E6, p1.getLongitudeE6() / 1E6, p2.getLatitudeE6() / 1E6, p2.getLongitudeE6() / 1E6, results);
        return results[0];
    }

    
}
