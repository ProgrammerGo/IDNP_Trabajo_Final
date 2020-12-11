package com.idnp_trabajo_final.views;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;

import com.idnp_trabajo_final.viewmodels.EntrenamientoViewModel;

import java.util.Date;

public class MyLocationUpdateReceiver extends BroadcastReceiver {
    private EntrenamientoViewModel  homeviewModel;
    private View root;

    public MyLocationUpdateReceiver(EntrenamientoViewModel homeViewModel, View root) {
        this.homeviewModel= homeViewModel;
        this.root=root;
    }


    public void onReceive(Context context, Intent intent) {
        Log.d("Prueba3",   "onReceive: ");

        if(intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)){
            String locationChanged= LocationManager.KEY_LOCATION_CHANGED;
            Location location= (Location) intent.getExtras().get(locationChanged);
            double latitud= location.getLatitude();
            double longitud= location.getLongitude();
            Log.d("Prueba",   "onReceive: "+latitud+longitud);
            Date now = new Date();
            homeviewModel.RecorridoTotal(latitud, longitud, now,root);
        }



    }
}
