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

import com.franceaoc.app.model.AOC;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author pierre
 */
public class AOCService
{
    private static Map< String, AOC> registry = new HashMap< String, AOC>() ;
    
    
    public static void register( AOC aoc )
    {
        registry.put(aoc.getId(), aoc );
    }
    
    public static boolean isRegistered( String id )
    {
        return registry.containsKey(id);
    }

    public static AOC get(String idAoc)
    {
        return registry.get(idAoc);
    }
    
    public static Collection<AOC> getAOCList()
    {
        return registry.values();
    }
}
