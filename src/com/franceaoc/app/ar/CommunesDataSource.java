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

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import com.franceaoc.app.model.Commune;
import com.franceaoc.app.service.CommuneService;
import com.jwetherell.augmented_reality.data.DataSource;
import com.jwetherell.augmented_reality.ui.Marker;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Source of POIs
 * @author pierre
 */
public class CommunesDataSource extends DataSource
{

    private List<Marker> mCachedMarkers = new ArrayList<Marker>();
    private static Bitmap icon = null;

    public CommunesDataSource( Context context , int iconRes )
    {
        Resources res = context.getResources();
        if (res == null)
        {
            throw new NullPointerException();
        }

        createIcon( res , iconRes );
    }

    protected final void createIcon( Resources res , int iconRes )
    {
        if (res == null)
        {
            throw new NullPointerException();
        }

        icon = BitmapFactory.decodeResource(res, iconRes );
    }

    public List<Marker> getMarkers()
    {
        List<Commune> listPOIs = CommuneService.getNearestCommunes();
        for( Commune commune : listPOIs )
        {
            Marker marker = new CommuneMarker( commune.getTitle(), commune.getLatitude(), commune.getLongitude(), 0.0 , Color.DKGRAY, icon , commune.getId() );
            
            mCachedMarkers.add(marker);
        }

        return mCachedMarkers;
    }
}
