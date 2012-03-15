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
import android.location.Location;
import com.franceaoc.app.Constants;
import com.franceaoc.app.model.Commune;
import com.franceaoc.app.model.SortablePOI;
import com.google.android.maps.GeoPoint;
import java.util.*;

/**
 *
 * @author pierre
 */
public class CommuneService
{

    private static Map< String, Commune> registry = new HashMap< String, Commune>();

    public static void register(Commune commune)
    {
        registry.put(commune.getId(), commune);
    }

    public static boolean isRegistered(String id)
    {
        return registry.containsKey(id);
    }

    public static Commune get(String insee)
    {
        return registry.get(insee);
    }

    public static Collection<Commune> getCommunesList()
    {
        return registry.values();
    }

    public static List<Commune> getNearestPOI(Collection<Commune> list, double latitude, double longitude, int max, long radius)
    {

        List<SortablePOI> listSort = new ArrayList<SortablePOI>();
        for (Commune poi : list)
        {
            SortablePOI t = new SortablePOI(poi, latitude, longitude);
            if (t.dist < radius)
            {
                listSort.add(t);
            }
        }

        Collections.sort(listSort);

        List<Commune> listNearest = new ArrayList<Commune>();
        int count = (listSort.size() > max) ? max : listSort.size();
        for (int i = 0; i < count; i++)
        {
            SortablePOI sp = listSort.get(i);
            Commune poi = (Commune) sp.poi;

            poi.setDistance(sp.dist);
            listNearest.add(poi);
        }

        return listNearest;
    }

    public static List<Commune> getNearestCommunes( Context context , int max )
    {
        Collection<Commune> all = CommuneService.getCommunesList();
        
        double lat, lon;
        if( Constants.DEMO )
        {
            lat = Constants.DEMO_LAT;
            lon = Constants.DEMO_LON;
        }
        else
        {
            Location location = LocationService.getLocation( context );
            lat = location.getLatitude();
            lon = location.getLongitude();
        }
        return CommuneService.getNearestPOI(all, lat, lon, max , 1000000);

    }
    
    public static List<Commune> getNearestCommunes( Context context , GeoPoint point, int max )
    {
        Collection<Commune> all = CommuneService.getCommunesList();
 
        double lat = ( (double) point.getLatitudeE6()) / 1E6;
        double lon = ( (double) point.getLongitudeE6()) / 1E6;
        
        return CommuneService.getNearestPOI(all, lat, lon, max , 1000000);
   }

}
