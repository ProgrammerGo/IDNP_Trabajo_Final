package com.idnp_trabajo_final.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Coordenada {
    private Integer id;
    private double coordenada_latitud;
    private  double coordenada_longitud;
    private Integer trayectoria;

    public Coordenada(double coordenada_latitud, double coordenada_longitud, Integer trayectoria) {
        this.coordenada_latitud = coordenada_latitud;
        this.coordenada_longitud = coordenada_longitud;
        this.trayectoria=trayectoria;
    }
    public Coordenada(){

    }
    public Integer getTrayectoria() {
        return trayectoria;
    }

    public void setTrayectoria(Integer trayectoria) {
        this.trayectoria = trayectoria;
    }

    public double getCoordenada_latitud() {
        return coordenada_latitud;
    }

    public void setCoordenada_latitud(double coordenada_latitud) {
        this.coordenada_latitud = coordenada_latitud;
    }

    public double getCoordenada_longitud() {
        return coordenada_longitud;
    }

    public void setCoordenada_longitud(double coordenada_longitud) {
        this.coordenada_longitud = coordenada_longitud;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
