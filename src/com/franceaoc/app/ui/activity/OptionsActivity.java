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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.text.InputType;

import com.franceaoc.app.Constants;
import com.franceaoc.app.R;

public class OptionsActivity extends PreferenceActivity {

	boolean isGps;
	int dist;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.isGps = prefs.getBoolean("geolocalisationIsActive", false);
		try {
			this.dist = Integer.parseInt(prefs.getString("geolocalisationDistance", "5"));
		} catch (NumberFormatException	e) {
			this.dist = 0;
		}


		EditTextPreference eTPDist = (EditTextPreference)findPreference("geolocalisationDistance");
		eTPDist.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);

	}

	@Override
	protected void onStop() {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean _isGps = prefs.getBoolean("geolocalisationIsActive", false);
		int _dist = 0;
		
		try {
			_dist = Integer.parseInt(prefs.getString("geolocalisationDistance", "5"));
		} catch (NumberFormatException	e) {
			_dist = 0;
		}

		if(_isGps!=this.isGps || _dist!=this.dist) {//si changement, on balance l'intent
			Intent i = new Intent();
			i.setAction(Constants.ACTION_PREFERENCES_MODIFIED);
			this.sendBroadcast(i);
		}
		super.onStop();
	}
}
