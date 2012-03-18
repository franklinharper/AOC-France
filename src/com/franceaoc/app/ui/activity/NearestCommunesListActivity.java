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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.franceaoc.app.Constants;
import com.franceaoc.app.R;
import com.franceaoc.app.model.Commune;
import com.franceaoc.app.service.CommuneService;
import com.franceaoc.app.ui.fragment.NearestCommunesListFragment;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import java.util.List;

/**
 *
 * @author pierre
 */

@EActivity(R.layout.nearest_communes_activity)
public class NearestCommunesListActivity extends FragmentActivity implements NearestCommunesListFragment.CommuneListEventsCallback
{
    @AfterViews
    void updateUI()
    {
        final FragmentManager fm = getSupportFragmentManager();
        NearestCommunesListFragment fragment = (NearestCommunesListFragment) fm.findFragmentById(R.id.fragment_nearest_communes_list);

        List<Commune> listCommunes = CommuneService.instance().getNearestCommunes( this , Constants.MAX_POI_LIST );
        fragment.update( listCommunes );
    }

    
    @Override
    protected void onResume()
    {
        super.onResume();
        updateUI();
    }
    
    

    public void onCommuneSelected(String ci)
    {
        showOnMap(ci);
    }
    
    private void showOnMap( String ci )
    {
        Intent intent = new Intent(Constants.ACTION_MAP);
        Commune commune = CommuneService.get(ci);
        intent.putExtra(Constants.EXTRA_POINT_LAT, commune.getLatitude() );
        intent.putExtra(Constants.EXTRA_POINT_LON, commune.getLongitude() );

        startActivity(intent);
    }

}
