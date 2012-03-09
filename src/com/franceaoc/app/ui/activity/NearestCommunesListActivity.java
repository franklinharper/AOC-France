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
import com.franceaoc.app.Constants;
import com.franceaoc.app.R;
import com.franceaoc.app.model.Commune;
import com.franceaoc.app.service.CommuneService;
import com.franceaoc.app.ui.fragment.NearestCommunesListFragment;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author pierre
 */
public class NearestCommunesListActivity extends FragmentActivity implements NearestCommunesListFragment.CommuneListEventsCallback
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nearest_communes_activity);

        FragmentManager fm = getSupportFragmentManager();
        NearestCommunesListFragment fragment = (NearestCommunesListFragment) fm.findFragmentById(R.id.fragment_nearest_communes_list);

        Collection<Commune> all = CommuneService.getCommunesList();
        double lat = 47.07;
        double lon = 4.88;
        List<Commune> listCommunes = CommuneService.getNearestPOI( all , lat, lon, 20, 1000000);
        fragment.update( listCommunes );
    }

    public void onCommuneSelected(String ci)
    {
        startCommune(ci);
    }
    
    private void startCommune( String ci )
    {
        Intent intent = new Intent(Constants.ACTION_COMMUNE_AOC);
        intent.putExtra(Constants.EXTRA_COMMUNE_CI, ci );

        startActivity(intent);
    }

}
