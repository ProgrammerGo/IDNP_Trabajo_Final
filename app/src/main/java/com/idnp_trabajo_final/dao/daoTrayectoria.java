package com.idnp_trabajo_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.idnp_trabajo_final.entities.Modo;
import com.idnp_trabajo_final.entities.Trayectoria;

import java.util.ArrayList;

public class daoTrayectoria {
    ArrayList<Trayectoria> lista;
    ConexionSQLiteHelper conn;
    SQLiteDatabase db;
    Context root;
    public daoTrayectoria(Context root){
        this.root= root;
        conn= new ConexionSQLiteHelper(this.root, "trayectoria", null,1 );
        db= conn.getWritableDatabase();
        lista= new ArrayList<Trayectoria>();
    }
    public void connect(){
        conn= new ConexionSQLiteHelper(root, "trayectoria", null,1 );
        db= conn.getWritableDatabase();
    }
    public boolean insertTrayectoria(  Trayectoria u){
        ContentValues cv  = new ContentValues();
        cv.put("fecha",u.getFecha());
        boolean rpt= db.insert("trayectoria",null,cv)>0;
        db.close();
        return (rpt);

    }

    public int buscar(String u){
        int x=0;
        lista = selectTrayectorias();
        for (Trayectoria us:lista) {
            if (us.getFecha().equals(u)){
                x++;
            }
        }
        return x;
    }
    public ArrayList<Trayectoria>selectTrayectorias(){
        ArrayList<Trayectoria> lista =new ArrayList<Trayectoria>();
        lista.clear();
        Cursor cr= db.rawQuery("select * from trayectoria",null);
        if(cr!=null && cr.moveToFirst()){

            do {
                Trayectoria u = new Trayectoria();
                u.setId(cr.getInt(0));
                u.setFecha(cr.getString(1));
                lista.add(u);
            }while(cr.moveToNext());
        }
        db.close();
        return lista;
    }


    public Trayectoria getTrayectoria(String u ){
        lista=selectTrayectorias();
        for (Trayectoria us:lista) {
            if(us.getFecha().equals(u)){
                return us;
            }
        }
        return null;
    }

    public Trayectoria getTrayectoriaById(int id){
        lista=selectTrayectorias();
        for (Trayectoria us:lista) {
            if(us.getId()==id){
                return us;
            }
        }
        return null;
    }
    public boolean updateTrayectoria(Trayectoria u, SQLiteDatabase sql){
        ContentValues cv= new ContentValues();
        cv.put("fecha",u.getFecha());
        return (sql.update("trayectoria",cv,"id="+u.getId(),null)>0);
    }


}
