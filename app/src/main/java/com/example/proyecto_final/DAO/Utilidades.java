package com.example.proyecto_final.DAO;

public class Utilidades {

    public static final String UPDATE_TABLE_COORDENADA= "DROP TABLE IF EXISTS coordenada";
    public static final String UPDATE_TABLE_MODO= "DROP TABLE IF EXISTS modo";
    public static final String create_tablaModo="create table IF NOT EXISTS modo(id integer primary key autoincrement, nombre text, descripcion text)";
    public static final String create_tablaCoordenada="create table IF NOT EXISTS coordenada(id integer primary key autoincrement, latitud  real, longitud real, fecha text)";

}

