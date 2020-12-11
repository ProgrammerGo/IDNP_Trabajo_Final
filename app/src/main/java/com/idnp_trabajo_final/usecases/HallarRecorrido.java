package com.idnp_trabajo_final.usecases;
import android.location.Location;

import android.view.View;

import com.idnp_trabajo_final.dao.daoCoordenada;
import com.idnp_trabajo_final.entities.Coordenada;
import com.idnp_trabajo_final.entities.Trayectoria;
import java.text.SimpleDateFormat;
import com.idnp_trabajo_final.dao.daoTrayectoria;

import java.util.ArrayList;
import java.util.Date;

public class HallarRecorrido {
    private  static float total=0;
    private static Location anterior = null;
    private static Trayectoria trayectoria;
    private static daoCoordenada daoCoordenada;
    private static daoTrayectoria daoTrayectoria;


    public static  String  RecorridoTotal(double latitud, double longitud, Date now, View root){
        if(trayectoria== null ){
            crearTrayectoria(now,root);
        }

        if(daoCoordenada== null ){
            daoCoordenada= new daoCoordenada(root.getContext());
        }

        Coordenada new_coordenada= new Coordenada(latitud,longitud, trayectoria.getId());
        daoCoordenada.connect();
        boolean a= daoCoordenada.insertCoordenada(new_coordenada);
        if(anterior==null){
            anterior = new Location("point A");
            anterior.setLatitude(latitud);
            anterior.setLongitude(longitud);
        }
        else{
            Location locationB = new Location("point B");
            locationB.setLatitude(latitud);
            locationB.setLongitude(longitud);
            float distance = anterior.distanceTo(locationB);
            anterior= locationB;
            total+=distance;
            daoCoordenada.connect();




        }


        return String.valueOf(total);
    }

    private static void crearTrayectoria(Date now,View root) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh: mm: ss a dd-MMM-aaaa");
        String fechaComoCadena = sdf.format(now);
        trayectoria = new Trayectoria(fechaComoCadena);
        daoTrayectoria = new daoTrayectoria(root.getContext());
        daoTrayectoria.insertTrayectoria(trayectoria);
        daoTrayectoria.connect();
        ArrayList<Trayectoria> imprimir = daoTrayectoria.selectTrayectorias();
        trayectoria.setId(imprimir.get(imprimir.size() - 1).getId());

    }


}
