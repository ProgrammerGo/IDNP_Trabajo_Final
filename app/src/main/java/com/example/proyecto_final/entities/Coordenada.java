package com.example.proyecto_final.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Coordenada {
    private Integer id;
    private double coordenada_latitud;
    private  double coordenada_longitud;
    private String coordenada_fecha;

    public Coordenada(double coordenada_latitud, double coordenada_longitud, String coordenada_fecha) {
        this.coordenada_latitud = coordenada_latitud;
        this.coordenada_longitud = coordenada_longitud;
        this.coordenada_fecha = coordenada_fecha;
    }
    public Coordenada(){

    }
    public double getCoordenada_latitud() {
        return coordenada_latitud;
    }

    public void setCoordenada_latitud(long coordenada_latitud) {
        this.coordenada_latitud = coordenada_latitud;
    }

    public double getCoordenada_longitud() {
        return coordenada_longitud;
    }

    public void setCoordenada_longitud(long coordenada_longitud) {
        this.coordenada_longitud = coordenada_longitud;
    }

    public String getCoordenada_fecha() {

        return coordenada_fecha;
    }

    public void setCoordenada_fecha(String coordenada_fecha) {
        this.coordenada_fecha = coordenada_fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
