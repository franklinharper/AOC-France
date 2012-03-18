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
package com.franceaoc.app.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import com.franceaoc.app.Constants;
import com.franceaoc.app.model.Commune;
import com.franceaoc.app.ui.adpater.CommuneAdapter;
import java.util.List;

/**
 * Application List Fragment
 * @author Pierre Levy
 */
public class NearestCommunesListFragment extends ListFragment
{

    private CommuneListEventsCallback mContainerCallback;
    private Activity mActivity;

    public void update(List<Commune> listCommunes )
    {
        fillData( listCommunes );
    }

    /**
     * Interface 
     */
    public interface CommuneListEventsCallback
    {

        /**
         * Callback
         * @param ci The ci of the commune
         */
        public void onCommuneSelected( String ci );
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        registerForContextMenu(getListView());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            // check that the containing activity implements our callback
            mContainerCallback = (CommuneListEventsCallback) activity;
            mActivity= activity;
        }
        catch (ClassCastException e)
        {
            activity.finish();
            throw new ClassCastException(activity.toString()
                    + " must implement AppListEventsCallback");
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        super.onListItemClick(l, v, position, id);
        Adapter adapter = l.getAdapter();
        Commune commune = (Commune) adapter.getItem(position);
        mContainerCallback.onCommuneSelected( commune.getId() );
    }
    
    private void fillData( List<Commune> listCommunes )
    {
        Activity activity = getActivity();
        if( activity != null )
        {
            CommuneAdapter adapter = new CommuneAdapter( activity, listCommunes );
            setListAdapter(adapter);
        }
        else
        {
            Log.i( Constants.TAG, "Activity not found" );
        }

    }
    
}
