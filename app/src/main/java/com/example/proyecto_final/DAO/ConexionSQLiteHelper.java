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
        db.execSQL(Utilidades.create_tablaModo);
        db.execSQL(Utilidades.create_tablaCoordenada);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Utilidades.UPDATE_TABLE_MODO );
       db.execSQL(Utilidades.UPDATE_TABLE_COORDENADA );
        onCreate(db);

    }

}
