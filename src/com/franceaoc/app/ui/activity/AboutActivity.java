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
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import com.franceaoc.app.R;
import org.androidsoft.utils.credits.CreditsParams;
import org.androidsoft.utils.credits.CreditsView;

/**
 *
 * @author pierre
 */
public class AboutActivity extends Activity
{

    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);

        View view = new CreditsView(this, getCreditsParams());
        setContentView(view);

    }

    private CreditsParams getCreditsParams()
    {
        CreditsParams p = new CreditsParams();
        p.setAppNameRes(R.string.credits_app_name);
        p.setAppVersionRes(R.string.credits_current_version);
        p.setBitmapBackgroundRes(R.drawable.bg_dashboard);
        p.setBitmapBackgroundLandscapeRes(R.drawable.bg_dashboard);
        p.setArrayCreditsRes(R.array.credits);

        p.setColorDefault(0xCC8D1C0E);
        p.setTextSizeDefault(32);
        p.setTypefaceDefault(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        p.setSpacingBeforeDefault(10);
        p.setSpacingAfterDefault(18);

        p.setColorCategory(0xCC333333);
        p.setTextSizeCategory(20);
        p.setTypefaceCategory(Typeface.create(Typeface.SANS_SERIF, Typeface.ITALIC));
        p.setSpacingBeforeCategory(12);
        p.setSpacingAfterCategory(12);

        return p;

    }

}

