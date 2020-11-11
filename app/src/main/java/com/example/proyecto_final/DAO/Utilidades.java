package com.example.proyecto_final.DAO;

public class Utilidades {

    public static final String TABLA_MODO= "MODO";
    public static final String CAMPO_ID= "id";
    public static final String CAMPO_NOMBRE= "nombre";
    public static final String CAMPO_DESCRIPCION= "descripcion";

    public static final String  CREAR_TABLA_MODO="CREATE TABLE " +
            ""+TABLA_MODO+" ("+CAMPO_ID+" " +
            "INTEGER, "+CAMPO_NOMBRE+" TEXT,"+CAMPO_DESCRIPCION+" TEXT)";
    public static final String UPDATE_TABLE_MODO= "DROP TABLE IF EXISTS MODO";

}

