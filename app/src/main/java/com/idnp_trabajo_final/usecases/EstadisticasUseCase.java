package com.idnp_trabajo_final.usecases;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.idnp_trabajo_final.dao.daoRecorrido;
import com.idnp_trabajo_final.entities.Recorrido;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class EstadisticasUseCase {
    private static double[] respuestas;
    private static daoRecorrido daoRecorrido;
    private static View root;
    private static   Date date1;
    private static   Date now;
    private static   Date fecha_dos;
    private static double distancia_total_semana;
    private static double distancia_total_mes;
    private static double distancia_promedio_dia;
    private static double tiempo_promedio_dia;
    private static double total_min;
    private static    SimpleDateFormat sdf;
    public static double[] HallarValores(View root2){
        root=root2;
        Log.d("estadisticas", "Metodo HallarValores ");
        respuestas= new double[5];
        respuestas[0]= nueva();
        respuestas[1]= nueva2();
        respuestas[2]= nueva3();
        respuestas[3]= nueva4();
        respuestas[4]= nueva5();
        return respuestas;
    }
    public static double nueva() {
        daoRecorrido = new daoRecorrido(root.getContext());
        ArrayList<Recorrido> recorridos = daoRecorrido.selectRecorrido();
         now = new Date();
        date1=new Date();
        for(int i=0; i< recorridos.size();i++){
            Log.d("estadisticas2", "recorrido "+recorridos.get(i).getDistancia()+"  "+recorridos.get(i).getFecha());
            try {

                 sdf = new SimpleDateFormat("hh: mm: ss a dd-MM-yyyy");
                 construye(7,0);
                fecha_dos= new Date();
                String cadena=recorridos.get(i).getFecha();
                convierte(cadena);
                Log.d("estadisticas2", "fechaultima "+sdf.format(fecha_dos));
                Log.d("estadisticas2", "Date1 "+sdf.format(date1));
               if (fecha_dos.after(date1)) {
                    distancia_total_semana += recorridos.get(i).getDistancia();
                    Log.d("estadisticas2", "distancia total"+distancia_total_semana);
                }


            }
            catch (Exception ex) {
                Log.d("estadistica2", "ERROR");
            }
        }

        return distancia_total_semana;
    }
    public static void  construye(int i, int j ){
        int f = now.getYear();
        date1.setHours(11);
        date1.setMinutes(59);
        date1.setSeconds(59);
        int  argumento1= now.getDate() - i;
        Log.d("estadisticas2", "argumento1 "+argumento1);
        int argumento2= now.getMonth() - j;
        date1.setDate(argumento1);
        date1.setMonth(argumento2);
        date1.setYear(f);
        Log.d("estadisticas", "fecha "+sdf.format(date1));
    }
    public static void convierte(String cadena){
        fecha_dos.setHours(Integer.parseInt(cadena.substring(0,2)));
        fecha_dos.setMinutes(Integer.parseInt(cadena.substring(4,6)));
        fecha_dos.setSeconds(Integer.parseInt(cadena.substring(8,10)));
        fecha_dos.setDate(Integer.parseInt(cadena.substring(16,18)));
        fecha_dos.setMonth(Integer.parseInt(cadena.substring(19,21))-1);
        fecha_dos.setYear(Integer.parseInt(cadena.substring(22,26))-1900);
    }
    public static double nueva2() {
        daoRecorrido = new daoRecorrido(root.getContext());
        ArrayList<Recorrido> recorridos = daoRecorrido.selectRecorrido();
        now = new Date();
        date1=new Date();
        for(int i=0; i< recorridos.size();i++){
            Log.d("estadisticas1", "recorrido "+recorridos.get(i).getDistancia()+"  "+recorridos.get(i).getFecha());
            try {

                sdf = new SimpleDateFormat("hh: mm: ss a dd-MM-yyyy");
                construye(0,1);
                fecha_dos= new Date();
                String cadena=recorridos.get(i).getFecha();
                convierte(cadena);
                Log.d("estadisticas1", "fechaultima "+sdf.format(fecha_dos));
                Log.d("estadisticas1", "DATE1 "+sdf.format(date1));
                if (fecha_dos.after(date1)) {
                    distancia_total_mes += recorridos.get(i).getDistancia();

                }
                Log.d("estadisticas1", "distancia total "+distancia_total_mes);

            }
            catch (Exception ex) {
                Log.d("estadistica1", "ERROR");
            }
        }

        return distancia_total_mes;
    }
    public static double nueva3() {
        daoRecorrido = new daoRecorrido(root.getContext());
        ArrayList<Recorrido> recorridos = daoRecorrido.selectRecorrido();
        now = new Date();
        date1=new Date();
        for(int i=0; i< recorridos.size();i++){
            Log.d("estadisticas3", "recorrido "+recorridos.get(i).getDistancia()+"  "+recorridos.get(i).getFecha());
            try {

                sdf = new SimpleDateFormat("hh: mm: ss a dd-MM-yyyy");
                construye(0,0);
                fecha_dos= new Date();
                String cadena=recorridos.get(i).getFecha();
                convierte(cadena);
                Log.d("estadisticas3", "fechaultima "+sdf.format(fecha_dos));
                if ((fecha_dos.getDay())==(date1.getDay())) {
                    distancia_promedio_dia+= recorridos.get(i).getDistancia();
                    Log.d("estadisticas3", "distancia total"+distancia_promedio_dia);
                }


            }
            catch (Exception ex) {
                Log.d("estadistica3", "ERROR");
            }
        }
        Log.d("size", ""+recorridos.size());
        return distancia_promedio_dia/recorridos.size();
    }
    public static double nueva4() {
        daoRecorrido = new daoRecorrido(root.getContext());
        ArrayList<Recorrido> recorridos = daoRecorrido.selectRecorrido();
        now = new Date();
        date1=new Date();
        for(int i=0; i< recorridos.size();i++){
            Log.d("estadisticas4", "recorrido "+recorridos.get(i).getDistancia()+"  "+recorridos.get(i).getFecha());
            try {

                sdf = new SimpleDateFormat("hh: mm: ss a dd-MM-yyyy");
                construye(0,0);
                fecha_dos= new Date();
                String cadena=recorridos.get(i).getFecha();
                convierte(cadena);
                Log.d("estadisticas4", "fechaultima "+sdf.format(fecha_dos));
                if ((fecha_dos.getDay())==(date1.getDay())) {
                    total_min+= recorridos.get(i).getTiempo();
                    Log.d("estadisticas4", "distancia total"+total_min);
                }


            }
            catch (Exception ex) {
                Log.d("estadistica4", "ERROR");
            }
        }
        return total_min;
    }

    public static double nueva5() {
        daoRecorrido = new daoRecorrido(root.getContext());
        ArrayList<Recorrido> recorridos = daoRecorrido.selectRecorrido();
        now = new Date();
        date1=new Date();
        for(int i=0; i< recorridos.size();i++){
            Log.d("estadisticas3", "recorrido "+recorridos.get(i).getDistancia()+"  "+recorridos.get(i).getFecha());
            try {

                sdf = new SimpleDateFormat("hh: mm: ss a dd-MM-yyyy");
                construye(0,0);
                fecha_dos= new Date();
                String cadena=recorridos.get(i).getFecha();
                convierte(cadena);
                Log.d("estadisticas3", "fechaultima "+sdf.format(fecha_dos));
                if ((fecha_dos.getDay())==(date1.getDay())) {
                    tiempo_promedio_dia+= recorridos.get(i).getTiempo();
                    Log.d("estadisticas3", "distancia total"+tiempo_promedio_dia);
                }


            }
            catch (Exception ex) {
                Log.d("estadistica3", "ERROR");
            }
        }
        Log.d("size", ""+recorridos.size());
        return tiempo_promedio_dia/recorridos.size();
    }
}
