package com.idnp_trabajo_final.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.idnp_trabajo_final.entities.Recorrido;
import com.idnp_trabajo_final.entities.Usuario;
import com.idnp_trabajo_final.utils.Utilidades;

import java.util.ArrayList;

public class daoRecorrido {
    ArrayList<Recorrido> lista;
    ConexionSQLiteHelper conn;
    SQLiteDatabase db;
    Context root;
    public daoRecorrido(Context root){
        this.root= root;
        conn= new ConexionSQLiteHelper(this.root, "recorrido", null,1 );
        db= conn.getWritableDatabase();
        lista= new ArrayList<Recorrido>();
    }
    public void connect(){
        conn= new ConexionSQLiteHelper(root, "recorrido", null,1 );
        db= conn.getWritableDatabase();
    }
    public boolean insertRecorrido(  Recorrido recorrido){
        if (buscar(recorrido.getFecha())==0){
            ContentValues cv  = new ContentValues();
            cv.put("modo",recorrido.getModo());
             cv.put("usuario",recorrido.getUsuario());
             cv.put("fecha",recorrido.getFecha());
            cv.put("tiempo",recorrido.getTiempo());
            cv.put("distancia",recorrido.getDistancia());
            boolean rpt= db.insert("recorrido",null,cv)>0;
            db.close();
            return (rpt);
        }else{
            db.close();
            return false;
        }
    }

    public int buscar(String u){
        int x=0;
        db.close();
        lista = selectRecorrido();
        connect();
        for (Recorrido us:lista) {
            if (us.getFecha().equals(u)){
                x++;
            }
        }
        return x;
    }
    public ArrayList<Recorrido>selectRecorrido(){
        ArrayList<Recorrido> lista =new ArrayList<Recorrido>();
        lista.clear();
        db.close();
        connect();
        Cursor cr= db.rawQuery("select * from recorrido",null);
        if(cr!=null && cr.moveToFirst()){

            do {
                Recorrido u = new Recorrido();
                u.setId(cr.getInt(0));
                u.setModo(cr.getInt(1));
                u.setUsuario(cr.getInt(2));
                u.setFecha(cr.getString(3));
                u.setTiempo(cr.getLong(4));
                u.setDistancia(cr.getFloat(5));
                lista.add(u);
            }while(cr.moveToNext());
        }
        db.close();


        return lista;
    }


    public Recorrido getRecorrido(String u ){
        lista=selectRecorrido();
        for (Recorrido us:lista) {
            if(us.getFecha().equals(u)){
                return us;
            }
        }
        return null;
    }

    public Recorrido getRecorridoById(int id){
        lista=selectRecorrido();
        for (Recorrido us:lista) {
            if(us.getId()==id){
                return us;
            }
        }
        return null;
    }
    public boolean updateRecorrido(Recorrido recorrido){
        db.close();
        connect();
        Log.d("prueba", "updateRecorrido_distancia: ");
        ContentValues cv= new ContentValues();
        cv.put("modo",recorrido.getModo());
        cv.put("usuario",recorrido.getUsuario());
        cv.put("fecha",recorrido.getFecha());
        cv.put("tiempo",recorrido.getTiempo());
        cv.put("distancia",recorrido.getDistancia());
        return (db.update("recorrido",cv,"id="+recorrido.getId(),null)>0);
    }

    /*public void updateRecorrido_distancia(int id, float distance) {
        db.close();
        connect();
        Log.d("prueba", "updateRecorrido_distancia: ");
      db.execSQL("update recorrido set distancia="+distance+
              "where id="+id+";");

        Log.d("prueba", "updateRecorrido_distancia: finalizado ");
               db.close();
    }
    */
    public void updateRecorrido_tiempo(int id, double time ){
        db.execSQL("UPDATE recorrido" +
                "SET tiempo = " +time+
                "WHERE id = "+id);
    }
    public void EliminaTodo(){
        db.execSQL(Utilidades.create_tablaRecorrido);
    }

}
