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
package com.franceaoc.app.map;

import android.graphics.drawable.Drawable;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;
import com.readystatesoftware.mapviewballoons.BalloonOverlayResources;
import java.util.ArrayList;

/**
 *
 * @author pierre
 */
public class CommuneOverlay extends BalloonItemizedOverlay<OverlayItem>
{
    private OnTapListener mTapListener;
    
    public interface OnTapListener {
    
        void onTap( String ci );
    }

    private ArrayList<OverlayItem> mOverlays;
    private POIOverlayResources mRes;

    public CommuneOverlay(Drawable defaultMarker, MapView mapView, POIOverlayResources res , OnTapListener listener )
    {
        super(boundCenterBottom(defaultMarker), mapView);
        mOverlays = new ArrayList<OverlayItem>();
        mRes = res;
        mTapListener = listener;
        setBalloonBottomOffset( res.getBottomOffset() );
        populate();
    }

    public void addOverlay(OverlayItem overlay)
    {
        mOverlays.add(overlay);
        populate();
    }

    @Override
    protected OverlayItem createItem(int i)
    {
        return mOverlays.get(i);
    }

    // Removes overlay item i
    public void removeItem(int i)
    {
        mOverlays.remove(i);
        populate();
    }

    // Returns present number of items in list
    @Override
    public int size()
    {
        return mOverlays.size();
    }

    @Override
    public int getBalloonInnerLayoutId()
    {
        return mRes.getInnerLayoutId();
    }

    @Override
    public int getBalloonMainLayoutId()
    {
        return mRes.getMainLayoutId();
    }

    @Override
    public BalloonOverlayResources getBalloonOverlayResources()
    {
        BalloonOverlayResources res = new BalloonOverlayResources();
        res.setLayout(mRes.getLayout());
        res.setItemTitleId(mRes.getItemTitleId());
        res.setItemSnippetId(mRes.getItemSnippetId());
        res.setCloseId(mRes.getCloseId());
        return res;
    }

    @Override
    protected boolean onBalloonTap(int index, OverlayItem item)
    {
        CommuneOverlayItem i = (CommuneOverlayItem) item;
        
        mTapListener.onTap( i.getCI() );
        
        return super.onBalloonTap(index, item);
    }
   
   
    
}