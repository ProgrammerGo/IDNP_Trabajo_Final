package com.idnp_trabajo_final.dao;

public class Utilidades {

    public static final String UPDATE_TABLE_COORDENADA= "DROP TABLE IF EXISTS coordenada";
    public static final String UPDATE_TABLE_MODO= "DROP TABLE IF EXISTS modo";
    public static final String UPDATE_TABLE_USUARIO= "DROP TABLE IF EXISTS usuario";
    public static final String UPDATE_TABLE_TRAYECTORIA= "DROP TABLE IF EXISTS trayectoria";
    public static final String create_tablaModo="create table IF NOT EXISTS modo(id integer primary key autoincrement, nombre text, descripcion text)";
    public static final String create_tablaCoordenada="create table IF NOT EXISTS coordenada(id integer primary key autoincrement, latitud  real, longitud real, trayectoria integer)";
    public static final String create_tablaTrayectoria="create table IF NOT EXISTS trayectoria(id integer primary key autoincrement, fecha text)";
    public static final String create_tablaUsuario="create table IF NOT EXISTS usuario(id integer primary key autoincrement, mail text, pass text, nombre text)";

}

