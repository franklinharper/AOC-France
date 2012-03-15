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
package com.franceaoc.app.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.util.Log;

import com.franceaoc.app.Constants;
import com.franceaoc.app.R;
import com.franceaoc.app.model.Commune;
import com.franceaoc.app.ui.activity.SplashActivity;

public class ProximityService extends BroadcastReceiver
{

    private int requestCode = 0;

    @Override
    public void onReceive(Context context, Intent intent)
    {

        String action = intent.getAction();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Log.d(Constants.TAG, "BROADCAST Revceived with action = " + action);
        //On affiche un trucmuche
        if (Constants.ACTION_GEOLOC_NOTIFICATION.equalsIgnoreCase(action))
        {
            //If the value is true, the device is entering the proximity region; if false, it is exiting.
            Boolean entering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
            if (entering)
            {
                /*
                 * long lat = intent.getLongExtra("location-lat", -1); long lon
                 * = intent.getLongExtra("location-lon", -1);
                 * Log.i(Constants.TAG, "entering: " + lat + lon);
                 */
                String cid = intent.getStringExtra("communeId");
                Log.d(Constants.TAG, "on entre dans le rayon de la commune : " + cid);
                showNotification(context, cid);
            }
        } //ACTION_PREFERENCES_MODIFIED -> Préférences changées 
        //REBOOT -> Le device reboot, on vérifie si on doit remettre les capteurs
        else if (Constants.ACTION_PREFERENCES_MODIFIED.equalsIgnoreCase(action) || Intent.ACTION_REBOOT.equalsIgnoreCase(action))
        {
            Log.d(Constants.TAG, "Mise à jour des préférences");

            boolean geolocalisationIsActive = prefs.getBoolean("geolocalisationIsActive", false);
            int geolocalisationDistance = 0;

            try
            {
                geolocalisationDistance = Integer.parseInt(prefs.getString("geolocalisationDistance", "5")) * 1000;
            }
            catch (NumberFormatException e)
            {
                geolocalisationDistance = 0;
            }

            Log.d(Constants.TAG, "Désactivation des points");
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            for (Commune poi : CommuneService.getCommunesList())
            {
                Intent newIntent = new Intent(Constants.ACTION_GEOLOC_NOTIFICATION);
                intent.putExtra("communeId", poi.getId());
                PendingIntent proximityIntent = PendingIntent.getBroadcast(context, 0, newIntent, 0);
                locationManager.removeProximityAlert(proximityIntent);
                //removeEvent(context, poi);
            }

            if (geolocalisationIsActive)
            {
                Log.d(Constants.TAG, "Activation des points !");

                for (Commune poi : CommuneService.getCommunesList())
                {
                    Intent newIntent = new Intent(Constants.ACTION_GEOLOC_NOTIFICATION);
                    intent.putExtra("communeId", poi.getId());
                    PendingIntent proximityIntent = PendingIntent.getBroadcast(context, 0, newIntent, 0);
                    locationManager.addProximityAlert(poi.getLatitude(), poi.getLongitude(), geolocalisationDistance, Long.MAX_VALUE, proximityIntent);


                    Log.d(Constants.TAG, "Coordonnées ajoutées : " + poi.getLatitude() + ", " + poi.getLongitude());
                    return;
                    //addEvent(context, poi, geolocalisationDistance);
                }
            }

        }

    }

    public void addEvent(Context context, Commune poi, int geolocalisationDistance)
    {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Intent tmpIntent = new Intent(context, ProximityService.class);
        tmpIntent.putExtra("communeId", poi.getId());
        tmpIntent.setAction(Constants.ACTION_GEOLOC_NOTIFICATION);
        //tmpIntent.putExtra(GeoDbAdapter.KEY_ROWID, rowId);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, requestCode, tmpIntent, 0);
        requestCode++;


        lm.addProximityAlert(poi.getLatitude(), poi.getLongitude(), geolocalisationDistance, Long.MAX_VALUE, contentIntent);
        //lm.addProximityAlert(latitude, longitude, radius, (long)-1, contentIntent);

        //Log.d(Constants.TAG, "latitude:"+poi.getLatitude()+", longitude:"+poi.getLongitude()+", radius: "+geolocalisationDistance);

    }

    public void removeEvent(Context context, Commune poi)
    {

        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        Intent tmpIntent = new Intent(context, ProximityService.class);
        tmpIntent.putExtra("communeId", poi.getId());
        tmpIntent.setAction(Constants.ACTION_GEOLOC_NOTIFICATION);
        //tmpIntent.putExtra(GeoDbAdapter.KEY_ROWID, rowId);
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, requestCode, tmpIntent, 0);
        requestCode++;


        lm.removeProximityAlert(contentIntent);
        //lm.addProximityAlert(latitude, longitude, radius, (long)-1, contentIntent);
    }

    private void showNotification(Context context, String communeId)
    {

        Commune com = CommuneService.get(communeId);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent notificationIntent = new Intent(context, SplashActivity.class);//TODO lancer une autre activity
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification notification = createNotification();
        notification.setLatestEventInfo(context,
                "Proximity Alert!", "You are near your point of interest.", pendingIntent);

        notificationManager.notify(1, notification);//TODO mettre une vraie gestion des ID pour pouvoir avoir plusieurs notifications
    }

    private Notification createNotification()
    {
        Notification notification = new Notification();

        notification.icon = R.drawable.notification;
        notification.when = System.currentTimeMillis();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;

        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;


        return notification;
    }
}
