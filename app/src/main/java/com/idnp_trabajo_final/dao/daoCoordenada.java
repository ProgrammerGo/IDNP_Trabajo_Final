package com.idnp_trabajo_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.idnp_trabajo_final.entities.Coordenada;
import com.idnp_trabajo_final.entities.Modo;
import com.idnp_trabajo_final.entities.Trayectoria;


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
        if (buscar(u.getTrayectoria())==0){
            ContentValues cv  = new ContentValues();
            cv.put("latitud",u.getCoordenada_latitud());
            cv.put("longitud",u.getCoordenada_longitud());
            cv.put("trayectoria", u.getTrayectoria());
            boolean rpt= db.insert("coordenada",null,cv)>0;
            db.close();
            return (rpt);
        }else{
            db.close();
            return false;
        }


    }

    public int buscar(Integer u){
        int x=0;
        db.close();
        lista = selectCoordenadas();
        connect();
        for (Coordenada us:lista) {
            if (us.getTrayectoria()==u){
                x++;
            }
        }
        return x;
    }
    public ArrayList<Coordenada>selectCoordenadas(){
        connect();
        ArrayList<Coordenada> lista =new ArrayList<Coordenada>();
        lista.clear();
        Cursor cr= db.rawQuery("select * from coordenada",null);
        if(cr!=null && cr.moveToFirst()){

            do {
                Coordenada u = new Coordenada();
                u.setId(cr.getInt(0));
                u.setCoordenada_latitud(cr.getDouble(1));
                u.setCoordenada_longitud(cr.getDouble(2));
                u.setTrayectoria(cr.getInt(3));
                lista.add(u);
            }while(cr.moveToNext());
        }
        db.close();
        return lista;
    }


    public Coordenada getCoordenada(Double u , Double l , Integer trayectoria ){
        lista=selectCoordenadas();
        for (Coordenada us:lista) {
            if(us.getCoordenada_latitud()== u && us.getCoordenada_longitud()==l && us.getTrayectoria()==trayectoria){
                return us;
            }
        }
        return null;
    }

    public Coordenada getCoordenadaById(int id){
        lista=selectCoordenadas();
        for (Coordenada us:lista) {
            if(us.getId()==id){
                return us;
            }
        }
        return null;
    }
    public boolean updateCoordenada(Coordenada u, SQLiteDatabase sql){
        ContentValues cv= new ContentValues();
        cv.put("latitud",u.getCoordenada_latitud());
        cv.put("longitud ",u.getCoordenada_longitud());
        cv.put("trayectoria",u.getTrayectoria());
        return (sql.update("coordenada",cv,"id="+u.getId(),null)>0);
    }


}
