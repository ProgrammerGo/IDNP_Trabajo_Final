package com.idnp_trabajo_final.usecases;
import android.location.Location;

import android.util.Log;
import android.view.View;

import com.idnp_trabajo_final.dao.daoCoordenada;
import com.idnp_trabajo_final.dao.daoRecorrido;
import com.idnp_trabajo_final.entities.Coordenada;
import com.idnp_trabajo_final.entities.Recorrido;
import com.idnp_trabajo_final.entities.Trayectoria;
import java.text.SimpleDateFormat;
import com.idnp_trabajo_final.dao.daoTrayectoria;
import com.idnp_trabajo_final.utils.PreferenceUtilsLog;

import java.util.ArrayList;
import java.util.Date;

public class HallarRecorrido {
    private  static float total=0;
    private static Location anterior = null;
    private static Trayectoria trayectoria;
    private static daoCoordenada daoCoordenada;
    private static daoTrayectoria daoTrayectoria;
    private static daoRecorrido daoRecorrido;
    private static Recorrido recorrido;
    private static  double [] enviar;
    private  static Recorrido  id_recorrido;

   /* public static  String  RecorridoTotal(double latitud, double longitud, Date now, View root){
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

    */
   public static  double[] RecorridoTotal(double latitud, double longitud, Date now, View root){
       Log.d("prueba ", "Hallando recorrido total ");
      /* if(trayectoria== null ){
           Log.d("prueba ", "Trayectoria nula ");
           crearTrayectoria(now,root);
       }*/
       if (recorrido==null){
           Log.d("prueba ", "Recorrido nulo ");

           crearRecorrido(now, root);
        }
       if(daoCoordenada== null ){
           daoCoordenada= new daoCoordenada(root.getContext());
       }
       Coordenada new_coordenada= new Coordenada(latitud,longitud, recorrido.getId());
      // Coordenada new_coordenada= new Coordenada(latitud,longitud, trayectoria.getId());
       Log.d("veamos", "RecorridoTotal: "+new_coordenada.getTrayectoria());
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
           Log.d("prueba ", "Actualizando recorrido distancia");
           id_recorrido.setDistancia(total);
           daoRecorrido.updateRecorrido(id_recorrido);
           Log.d("prueba", "updateRecorrido_distancia: finalizado ");


       }

       enviar= new double[4];
       enviar[0]= latitud;
       enviar[1]= longitud;
       enviar[2]=total;
       enviar[3]= id_recorrido.getId();
       return enviar;

       //return String.valueOf(total);

   }
 /*   private static void crearTrayectoria(Date now,View root) {
        Log.d("prueba", "crearTrayectoria: ");
        SimpleDateFormat sdf = new SimpleDateFormat("hh: mm: ss a dd-MMM-aaaa");
        String fechaComoCadena = sdf.format(now);
        trayectoria = new Trayectoria(fechaComoCadena);
        daoTrayectoria = new daoTrayectoria(root.getContext());
        daoTrayectoria.insertTrayectoria(trayectoria);
        daoTrayectoria.connect();
        ArrayList<Trayectoria> imprimir = daoTrayectoria.selectTrayectorias();
        trayectoria.setId(imprimir.get(imprimir.size() - 1).getId());
    }

  */
    private static void crearRecorrido(Date now,View root) {
        Log.d("prueba", "crearRecorrido: ");
        SimpleDateFormat sdf = new SimpleDateFormat("hh: mm: ss a dd-MM-yyyy");
        String fechaComoCadena = sdf.format(now);
        Log.d("prueba", "crearRecorrido: "+fechaComoCadena);
        int id= PreferenceUtilsLog.getId(root.getContext());
        recorrido = new Recorrido(1,id,fechaComoCadena,0L,0);
        daoRecorrido = new daoRecorrido(root.getContext());
        daoRecorrido.insertRecorrido(recorrido);
        daoRecorrido.connect();
        ArrayList<Recorrido> imprimir = daoRecorrido.selectRecorrido();
        id_recorrido= imprimir.get(imprimir.size() - 1);
        recorrido.setId(id_recorrido.getId());
    }


}
