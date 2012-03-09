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
package com.franceaoc.app.model;

import android.graphics.drawable.Drawable;

/**
 * POI Point of interest Interface
 * @author pierre
 */
public interface POI
{
    String getId();
    String getTitle();
    String getDesciption();
    double getLatitude();
    double getLongitude();
    long getDistance();
    void setDistance( long distance );

    public Drawable getIcon();
    
}
