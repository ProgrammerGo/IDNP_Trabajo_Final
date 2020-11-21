package com.example.proyecto_final.usecases;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.proyecto_final.entities.Coordenada;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HallarRecorrido {
    private  static float total=0;
    private static Location anterior = null;

     public static  String  RecorridoTotal(double latitud, double longitud, Date now){

         SimpleDateFormat sdf = new SimpleDateFormat( "hh: a dd-MMM-aaaa");
         String fechaComoCadena = sdf.format(now);
         Coordenada new_coordenada= new Coordenada(latitud,longitud,fechaComoCadena);
         if(anterior==null){
             Log.d("NO", "RecorridoTotal: AQUI");
             anterior = new Location("point A");
             anterior.setLatitude(latitud);
             anterior.setLongitude(longitud);
         }
         else{
             Log.d("SI", "RecorridoTotal: AQUI");
             Location locationB = new Location("point B");

             locationB.setLatitude(latitud);
             locationB.setLongitude(longitud);
             Log.d("hallando",""+anterior.getLatitude()+anterior.getLongitude() );
             Log.d("hallando",""+locationB.getLatitude()+locationB.getLongitude() );
             float distance = anterior.distanceTo(locationB);
             Log.d("hallando","distance: "+distance);
             anterior= locationB;
             total+=distance;
         }


         Log.d("hallando",String.valueOf(total) );
        return String.valueOf(total);
    }
}
