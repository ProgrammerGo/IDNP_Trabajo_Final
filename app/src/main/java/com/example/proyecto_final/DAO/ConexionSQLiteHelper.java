package com.example.proyecto_final.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.proyecto_final.DAO.Utilidades;

public class ConexionSQLiteHelper  extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_MODO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Utilidades.UPDATE_TABLE_MODO );
        onCreate(db);

    }
    public void config(){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values= new ContentValues();
       /* values.put(Utilidades.CAMPO_ID,"1");
        values.put(Utilidades.CAMPO_NOMBRE,"Caminata");
        values.put(Utilidades.CAMPO_DESCRIPCION,"Accion de caminar");
        Long idResultante= db.insert(Utilidades.TABLA_MODO, Utilidades.CAMPO_ID, values);
        values.put(Utilidades.CAMPO_ID,"2");
        values.put(Utilidades.CAMPO_NOMBRE,"Ciclismo");
        values.put(Utilidades.CAMPO_DESCRIPCION,"Manejar una bicicleta");
        idResultante= db.insert(Utilidades.TABLA_MODO, Utilidades.CAMPO_ID, values);*/
    }
}
