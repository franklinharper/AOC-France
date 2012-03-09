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

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import com.franceaoc.app.Constants;
import com.franceaoc.app.R;
import com.franceaoc.app.service.LoadingDataService;

public class SplashActivity extends Activity
{
    private static final int SPLASH_DELAY = 2000;
    private Thread mThread;
    private boolean mDelayExpired;
    private boolean mDataLoaded;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        new LoadingTask().execute();
        mThread = new Thread()
        {

            @Override
            public void run()
            {
                try
                {
                    synchronized (this)
                    {
                        wait(SPLASH_DELAY);
                    }
                }
                catch (InterruptedException ex)
                {
                }
                mDelayExpired = true;
                startDashboard();    
            }
        };

        mThread.start();
    }

    private void loadData()
    {
        LoadingDataService.loadData(this);
    }

    private void startDashboard()
    {
        if( mDelayExpired && mDataLoaded )
        {    
            Intent intent = new Intent(Constants.ACTION_DASHBOARD);
            startActivity(intent);
            finish();
        }
    }

    private class LoadingTask extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... arg0)
        {
            loadData();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            mDataLoaded = true;
            startDashboard();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent evt)
    {
        if (evt.getAction() == MotionEvent.ACTION_DOWN)
        {
            synchronized (mThread)
            {
                mThread.notifyAll();
            }
        }
        return true;
    }
}
