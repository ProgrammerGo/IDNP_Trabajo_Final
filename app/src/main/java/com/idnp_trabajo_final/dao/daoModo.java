package com.idnp_trabajo_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.idnp_trabajo_final.entities.Modo;

import java.util.ArrayList;

public class daoModo {
    ArrayList<Modo> lista;
    ConexionSQLiteHelper conn;
    SQLiteDatabase db;
    Context root;
    public daoModo(Context root){
        this.root= root;
        conn= new ConexionSQLiteHelper(this.root, "modo", null,1 );
        db= conn.getWritableDatabase();
        lista= new ArrayList<Modo>();
    }
    public void connect(){
        conn= new ConexionSQLiteHelper(root, "modo", null,1 );
        db= conn.getWritableDatabase();
    }
    public boolean insertModo(  Modo u){
        if (buscar(u.getNombre())==0){
            ContentValues cv  = new ContentValues();
            cv.put("nombre",u.getNombre());
            cv.put("descripcion",u.getDescripcion());
            boolean rpt= db.insert("modo",null,cv)>0;
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
        lista = selectModos();
        connect();
        for (Modo us:lista) {
            if (us.getNombre().equals(u)){
                x++;
            }
        }
        return x;
    }
    public ArrayList<Modo>selectModos(){
        ArrayList<Modo> lista =new ArrayList<Modo>();
        lista.clear();
        db.close();
        connect();
        Cursor cr= db.rawQuery("select * from modo",null);
        if(cr!=null && cr.moveToFirst()){

            do {
                Modo u = new Modo();
                u.setId(cr.getInt(0));
                u.setNombre(cr.getString(1));
                u.setDescripcion(cr.getString(2));
                lista.add(u);
            }while(cr.moveToNext());
        }
        db.close();


        return lista;
    }


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
}
