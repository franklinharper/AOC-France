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

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.franceaoc.app.Constants;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;

/**
 * POI Map View
 * @author pierre
 */
public class POIMapView extends MapView
{

    public interface OnPanChangeListener
    {

        public void onPanChange( GeoPoint newCenter, GeoPoint oldCenter);
    }

    private static final long TIMEOUT = 1000L;
    private boolean mTouched = false;
    private GeoPoint mLastMapCenter;
    private Timer mPanDelay = new Timer();
    private OnPanChangeListener mPanListener;

    public POIMapView(Context context, String apiKey)
    {
        super(context, apiKey);
        mLastMapCenter = this.getMapCenter();
    }

    public POIMapView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public POIMapView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public void setOnPanChangeListener( OnPanChangeListener listener)
    {
        mPanListener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        if (ev.getAction() == 1)
        {
            mTouched = false;
        }
        else
        {
            mTouched = true;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public void computeScroll()
    {
        super.computeScroll();
        if( mLastMapCenter == null )
        {
            mLastMapCenter = getMapCenter();
        }    
        
        if (!mLastMapCenter.equals(getMapCenter()) || !mTouched)
        {
            mPanDelay.cancel();
            mPanDelay = new Timer();
            mPanDelay.schedule(new TimerTask()
            {

                @Override
                public void run()
                {
                    Log.i(Constants.TAG, "Pan changed event");
                    mPanListener.onPanChange( getMapCenter(), mLastMapCenter);
                    mLastMapCenter = getMapCenter();
                }
            }, TIMEOUT);
        }
    }
    
    public void cancelUpdateTask()
    {
        mPanDelay.cancel();
        mPanDelay.purge();
    }
}