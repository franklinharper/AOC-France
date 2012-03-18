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
import android.util.Log;
import com.franceaoc.app.Constants;
import com.franceaoc.app.model.AOC;
import com.franceaoc.app.model.Commune;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author pierre
 */
public class LoadingDataService
{

    public static void loadData(Context context)
    {
        try
        {
            InputStream is = context.getAssets().open("aoc.csv");
            DataInputStream in = new DataInputStream(is);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            int i = 0;
            while ((strLine = br.readLine()) != null)
            {
                i++;
                if (i > 1)
                {
                    strLine.replace("\"", "" );
                    String splitarray[] = strLine.split(",");
                    String insee = splitarray[0];
                    String commune = splitarray[1];
                    String aoc = splitarray[2];
                    String idAoc = splitarray[3];
                    Double lat = Double.parseDouble( splitarray[4]);
                    Double lon = Double.parseDouble(splitarray[5]);

                    Commune c = CommuneService.instance().get(insee);
                    if (c == null)
                    {
                        c = new Commune(insee, commune);
                        c.setLatitude(lat);
                        c.setLongitude(lon);
                        CommuneService.instance().register(c);
                    }

                    AOC a = AOCService.get(idAoc);
                    if (a == null)
                    {
                        a = new AOC(idAoc, aoc);
                        AOCService.register(a);
                    }
                    c.addAOC(a);
                    a.addCommune(c);
                }
            }
            //Close the input stream
            in.close();
            Log.i( Constants.TAG, "Number of records loaded : " + i );
            Log.i( Constants.TAG, "Number of communes loaded : " + CommuneService.instance().getCount() );
            Log.i( Constants.TAG, "Number of AOC loaded : " + AOCService.getAOCList().size() );
        }
        catch (Exception e)
        {//Catch exception if any
            Log.e( Constants.TAG, "Error loading data : " + e.getMessage() );
        }

    }
}