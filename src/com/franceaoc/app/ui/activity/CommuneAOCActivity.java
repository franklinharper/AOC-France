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


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.franceaoc.app.Constants;
import com.franceaoc.app.R;
import com.franceaoc.app.model.AOC;
import com.franceaoc.app.model.Commune;
import com.franceaoc.app.service.CommuneService;

/**
 *
 * @author pierre
 */
public class CommuneAOCActivity extends FragmentActivity 
{

    private Commune mCurrentCommune;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commune_aoc);
        FragmentManager fm = getSupportFragmentManager();

        Intent intent = getIntent();
        String ci = intent.getStringExtra(Constants.EXTRA_COMMUNE_CI);
        mCurrentCommune = CommuneService.get(ci);


        TextView tvCommune = (TextView) findViewById(R.id.name_commune);
        tvCommune.setText(mCurrentCommune.getName());
        TextView tvAOC = (TextView) findViewById(R.id.name_aoc);
        
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

        tvAOC.setText( sb.toString() );

    }
}
