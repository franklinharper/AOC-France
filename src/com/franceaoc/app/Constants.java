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
package com.franceaoc.app;

/**
 *
 * @author pierre
 */
public class Constants
{
    public static final String TAG = "franceaoc";
    public static final String ACTION_DASHBOARD = "com.franceaoc.app.action.DASHBOARD";
    public static final String ACTION_NEAREST = "com.franceaoc.app.action.NEAREST";
    public static final String ACTION_COMMUNE_AOC = "com.franceaoc.app.action.COMMUNE_AOC";
    public static final String ACTION_ABOUT = "com.franceaoc.app.action.ABOUT";
    public static final String ACTION_MAP = "com.franceaoc.app.action.MAP";
    public static final String ACTION_OPTIONS = "com.franceaoc.app.action.OPTIONS";
    public static final String ACTION_AR = "com.franceaoc.app.action.AR";
    public static final String EXTRA_AOC_ID = "aoc_id";
    public static final String EXTRA_COMMUNE_CI = "commune_ci";

    public static final String ACTION_GEOLOC_NOTIFICATION = "com.franceaoc.app.action.ACTION_GEOLOC_NOTIFYCATION";
    public static final String ACTION_PREFERENCES_MODIFIED = "com.franceaoc.app.action.ACTION_PREFERENCES_MODIFIED";
    
    public static final double DEMO_LAT = 47.07;
    public static final double DEMO_LON = 4.88;
    public static final double DEMO_ALT = 0.0;
    public static final boolean DEMO = false;
    
    public static final int MAX_POI_AR = 20;
    public static final int MAX_POI_MAP = 500;
    public static final int MAX_POI_LIST = 200;
    public static final String EXTRA_POINT_LAT = "lat";
    public static final String EXTRA_POINT_LON = "lon";

   
}
