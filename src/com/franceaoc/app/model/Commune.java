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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pierre
 */
public class Commune extends AbstractPOI
{
    private String id;  // code INSEE
    private String name;
    private List<AOC> listAOC = new ArrayList<AOC>();

    public Commune(String insee, String commune)
    {
        id = insee;
        name = commune;
    }

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the aoc
     */
    public List<AOC> getAOCList()
    {
        return listAOC;
    }


    public void addAOC(AOC a)
    {
        listAOC.add(a);
    }

    public String getTitle()
    {
        return getName();
    }

    public String getDesciption()
    {
        StringBuilder sb = new StringBuilder("AOC : \n"); 
        for( AOC aoc : listAOC )
        {
            sb.append( aoc.getName()).append("\n");
        }
        return sb.toString();
    }
    
}
