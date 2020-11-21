package com.example.proyecto_final.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.proyecto_final.entities.Coordenada;


import java.util.ArrayList;

public class daoCoordenada {
    ArrayList<Coordenada> lista;
    ConexionSQLiteHelper conn;
    SQLiteDatabase db;
    Context root;
    public daoCoordenada(Context root){
        this.root= root;
        conn= new ConexionSQLiteHelper(this.root, "coordenada", null,1 );
        db= conn.getWritableDatabase();
        lista= new ArrayList<Coordenada>();
    }
    public void connect(){
        conn= new ConexionSQLiteHelper(root, "coordenada", null,1 );
        db= conn.getWritableDatabase();
    }
    public boolean insertCoordenada( Coordenada u){
      if (buscar(u.getCoordenada_fecha())==0){
            ContentValues cv  = new ContentValues();
            cv.put("latitud",u.getCoordenada_latitud());
            cv.put("longitud",u.getCoordenada_longitud());
            cv.put("fecha", u.getCoordenada_fecha());
            boolean rpt= db.insert("coordenada",null,cv)>0;
            db.close();
            return (rpt);
        }else{
            db.close();
            return false;
        }


    }

    public int buscar(String u){
        int x=0;
        lista = selectCoordenadas();
        for (Coordenada us:lista) {
            if (us.getCoordenada_fecha().equals(u)){
                x++;
            }
        }
        return x;
    }
    public ArrayList<Coordenada>selectCoordenadas(){
        ArrayList<Coordenada> lista =new ArrayList<Coordenada>();
        lista.clear();
        Cursor cr= db.rawQuery("select * from coordenada",null);
        if(cr!=null && cr.moveToFirst()){

            do {
               Coordenada u = new Coordenada();
                u.setId(cr.getInt(0));
                u.setCoordenada_latitud(cr.getLong(1));
                u.setCoordenada_longitud(cr.getLong(2));
                u.setCoordenada_fecha(cr.getString(3));
                lista.add(u);
            }while(cr.moveToNext());
        }
        db.close();
        return lista;
    }

/*
    public Modo getModo(String u ){
        lista=selectModos();
        for (Modo us:lista) {
            if(us.getNombre().equals(u)){
                return us;
            }
        }
        return null;
    }

    public Modo getModoById(int id){
        lista=selectModos();
        for (Modo us:lista) {
            if(us.getId()==id){
                return us;
            }
        }
        return null;
    }
    public boolean updateModo(Modo u, SQLiteDatabase sql){
        ContentValues cv= new ContentValues();
        cv.put("mail",u.getNombre());
        cv.put("pass",u.getDescripcion());
        return (sql.update("modo",cv,"id="+u.getId(),null)>0);
    }

 */
}
