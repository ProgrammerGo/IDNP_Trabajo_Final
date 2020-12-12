package com.idnp_trabajo_final.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.idnp_trabajo_final.dao.Utilidades;

public class ConexionSQLiteHelper  extends SQLiteOpenHelper {

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.create_tablaModo);
        db.execSQL(Utilidades.create_tablaCoordenada);
        db.execSQL(Utilidades.create_tablaTrayectoria);
        db.execSQL(Utilidades.create_tablaUsuario);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Utilidades.UPDATE_TABLE_MODO );
        db.execSQL(Utilidades.UPDATE_TABLE_COORDENADA );
        db.execSQL(Utilidades.UPDATE_TABLE_TRAYECTORIA );
        db.execSQL(Utilidades.UPDATE_TABLE_USUARIO);

        onCreate(db);

    }

}
