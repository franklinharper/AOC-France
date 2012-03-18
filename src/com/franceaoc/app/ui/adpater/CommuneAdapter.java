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
package com.franceaoc.app.ui.adpater;

import android.app.Activity;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.franceaoc.app.Constants;
import com.franceaoc.app.R;
import com.franceaoc.app.model.Commune;
import java.util.List;

/**
 *
 * @author pierre
 */
public class CommuneAdapter implements ListAdapter
{
    private List<Commune> mList;
    private Activity mActivity;

    /**
     * Constructor
     * @param activity The activity
     * @param list The apps list
     */
    public CommuneAdapter(Activity activity, List<Commune> list)
    {
        mActivity = activity;
        mList = list;
        Log.i( Constants.TAG, "Commune adapter created");

    }

    /**
     * {@inheritDoc }
     */
    public void registerDataSetObserver(DataSetObserver arg0)
    {
    }

    /**
     * {@inheritDoc }
     */
    public void unregisterDataSetObserver(DataSetObserver arg0)
    {
    }

    /**
     * {@inheritDoc }
     */
    public int getCount()
    {
        return mList.size();
    }

    /**
     * {@inheritDoc }
     */
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    /**
     * {@inheritDoc }
     */
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * {@inheritDoc }
     */
    public boolean hasStableIds()
    {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final Commune commune = mList.get(position);

        if (convertView == null)
        {
            final LayoutInflater inflater = mActivity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.commune_list_item, parent, false);
        }

        final ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
        imageView.setImageDrawable(commune.getIcon());

        final TextView tvDistance = (TextView) convertView.findViewById(R.id.distance);
        String distance = "" + commune.getDistance() / 1000 + " km";
        if( commune.getDistance() < 1000 )
        {
            distance = "< 1 km";
        }
        tvDistance.setText( distance );

        final TextView tvName = (TextView) convertView.findViewById(R.id.name_commune);
        tvName.setText(commune.getName());

        final TextView tvAOC = (TextView) convertView.findViewById(R.id.name_aoc);
        tvAOC.setText(commune.getAOCList().get(0).getName());

        return convertView;
    }

    /**
     * {@inheritDoc }
     */
    public int getItemViewType(int arg0)
    {
        return 0;
    }

    /**
     * {@inheritDoc }
     */
    public int getViewTypeCount()
    {
        return 1;
    }

    /**
     * {@inheritDoc }
     */
    public boolean isEmpty()
    {
        return mList.isEmpty();
    }

    /**
     * {@inheritDoc }
     */
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    /**
     * {@inheritDoc }
     */
    public boolean isEnabled(int arg0)
    {
        return true;
    }
}