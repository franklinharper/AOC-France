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
package com.franceaoc.app.ui.activity;


import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.franceaoc.app.Constants;
import com.franceaoc.app.R;
import com.franceaoc.app.model.AOC;
import com.franceaoc.app.model.Commune;
import com.franceaoc.app.service.CommuneService;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 *
 * @author pierre
 */

@EActivity(R.layout.commune_aoc)
public class CommuneActivity extends FragmentActivity 
{
    private Commune mCurrentCommune;
    
    @ViewById( R.id.name_commune )
    TextView mTvCommune;

    @ViewById(R.id.name_aoc)
    TextView mTvAOC;
    
    @Extra(Constants.EXTRA_COMMUNE_CI)
    String mCI;
    
    @AfterViews
    void updateUI()
    {
        mCurrentCommune = CommuneService.get(mCI);

        mTvCommune.setText(mCurrentCommune.getName());
        
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for( AOC aoc : mCurrentCommune.getAOCList() )
        {
            if( first )
            {
                sb.append( aoc.getName() );
                first = false;
            }
            else
            {
                sb.append(", \n");
                sb.append( aoc.getName() );
            }
        }
        mTvAOC.setText( sb.toString() );
    }
}
