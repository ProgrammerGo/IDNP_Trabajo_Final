package com.idnp_trabajo_final.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.idnp_trabajo_final.entities.Usuario;

import java.util.ArrayList;

public class daoUsuario {
    Context c;
    Usuario u;
    ArrayList<Usuario> lista;
    SQLiteDatabase sql;
    String bd="BDUusarios";
    String tabla="create table IF NOT EXISTS usuario(id integer primary key autoincrement, mail text, pass text, nombre text)";

    public daoUsuario(Context c){
        this.c=c;
        sql = c.openOrCreateDatabase(bd, c.MODE_PRIVATE, null);
        sql.execSQL(tabla);
        u=new Usuario();
        lista=new ArrayList<Usuario>();
    }

    public boolean insertUsuario(Usuario u ){
        if (buscar(u.getMail())==0){
            ContentValues cv  = new ContentValues();
            cv.put("mail",u.getMail());
            cv.put("pass",u.getPassword());
            cv.put("nombre",u.getNombre());
            return (sql.insert("usuario",null,cv)>0);
        }else{

            return false;
        }
    }
    public boolean insertUsuarioAn(Usuario u ){
        if (buscar(u.getMail())==0){
            ContentValues cv  = new ContentValues();
            cv.put("mail","");
            cv.put("pass","");
            cv.put("nombre","Anónimo");
            return (sql.insert("usuario",null,cv)>0);
        }else{

            return false;
        }
    }

    public int buscar(String u){
        int x=0;
        lista = selectUsuarios();
        for (Usuario us:lista) {
            if (us.getMail().equals(u)){
                x++;
            }
        }
        return x;
    }
    public ArrayList<Usuario>selectUsuarios(){
        ArrayList<Usuario> lista =new ArrayList<Usuario>();
        lista.clear();
        Cursor cr= sql.rawQuery("select * from usuario",null);
        if(cr!=null && cr.moveToFirst()){
            do {
                Usuario u=new Usuario();
                u.setId(cr.getInt(0));
                u.setMail(cr.getString(1));
                u.setPassword(cr.getString(2));
                u.setNombre(cr.getString(3));
                lista.add(u);
            }while(cr.moveToNext());
        }
        return lista;
    }

    public int login(String us, String pa) {
        int a=0;
        Cursor cr = sql.rawQuery("select * from usuario", null);
        if (cr != null && cr.moveToFirst()) {
            do {
                if (cr.getString(1).equals(us)&&cr.getString(2).equals(pa)){
                    a++;
                }
            } while (cr.moveToNext());
        }
        return  a;
    }


    public Usuario getUsuario(String u, String p){
        lista=selectUsuarios();
        for (Usuario us:lista) {
            if(us.getMail().equals(u)&&us.getPassword().equals(p)){
                return us;
            }
        }
        return null;
    }

    public Usuario getUsuarioById(int id){
        lista=selectUsuarios();
        for (Usuario us:lista) {
            if(us.getId()==id){
                return us;
            }
        }
        return null;
    }
    public boolean updateUsuario(Usuario u){
        ContentValues cv= new ContentValues();
        cv.put("mail",u.getMail());
        cv.put("pass",u.getPassword());
        cv.put("nombre",u.getNombre());
        return (sql.update("usuario",cv,"id="+u.getId(),null)>0);
    }
}
