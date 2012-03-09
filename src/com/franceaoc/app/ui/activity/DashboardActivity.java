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
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.franceaoc.app.Constants;
import com.franceaoc.app.R;

/**
 *
 * @author pierre
 */
public class DashboardActivity extends Activity implements View.OnClickListener
{

	private Button mButtonNearest;
	private Button mButtonMap;
	private Button mButtonAbout;
	private Button mButtonAR;
	private Button mButtonOptions;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);

		mButtonNearest = (Button) findViewById(R.id.button_list_around);
		mButtonNearest.setOnClickListener(this);
		mButtonMap = (Button) findViewById(R.id.button_map);
		mButtonMap.setOnClickListener(this);
		mButtonAbout = (Button) findViewById(R.id.button_about);
		mButtonAbout.setOnClickListener(this);
		mButtonAR = (Button) findViewById(R.id.button_ar);
		mButtonAR.setOnClickListener(this);
	}

	public void onClick(View v)
	{
		if (v == mButtonNearest)
		{
			startNearestCommunes();
		}
		else if (v == mButtonMap)
		{
			startMap();
		}
		else if (v == mButtonAbout)
		{
			startAbout();
		} 
		else if (v == mButtonAR)
		{
			startAR();
		} 
		else if ( v == mButtonOptions )
		{
			startOptions();
		}
	}

	private void startNearestCommunes()
	{
		Intent intent = new Intent(Constants.ACTION_NEAREST);
		startActivity(intent);
	}

	private void startMap()
	{
		Intent intent = new Intent(Constants.ACTION_MAP);
		startActivity(intent);
	}

	private void startAbout()
	{
		Intent intent = new Intent(Constants.ACTION_ABOUT);
		startActivity(intent);
	}

	private void startOptions() {
		Intent intent = new Intent( Constants.ACTION_OPTIONS );
		startActivity(intent);
	}

	private void startAR()
	{
		Intent intent = new Intent( Constants.ACTION_AR );
		startActivity(intent);
	}

	public static final int Menu1 = Menu.FIRST + 1;

	/** create the menu items */
	public void populateMenu(Menu menu) {
		MenuItem item1 = menu.add(0, Menu1, Menu1, "MenuOption1");
		item1.setIcon(R.drawable.settings_button);
	}
	/** hook into menu button for activity */
	@Override public boolean onCreateOptionsMenu(Menu menu) {
		populateMenu(menu);
		return super.onCreateOptionsMenu(menu);
	}

	/** when menu button option selected */
	@Override public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case Menu1:
			startOptions();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
