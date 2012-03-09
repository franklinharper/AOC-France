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
package com.franceaoc.app.ar;

import android.graphics.Bitmap;
import com.jwetherell.augmented_reality.ui.IconMarker;

/**
 *
 * @author pierre
 */
public class CommuneMarker extends IconMarker
{
    private String ci;
    
    public CommuneMarker( String name , double lat, double lon, double alt, int color , Bitmap bitmap , String ci  )
    {
        super( name , lat , lon , alt , color , bitmap );
        this.ci = ci; 
    }
    
    public String getCI()
    {
        return ci;
    }
    
}
