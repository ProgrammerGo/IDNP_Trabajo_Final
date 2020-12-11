package com.idnp_trabajo_final.entities;

import java.util.ArrayList;

public class Trayectoria {
    private Integer id;
    private String fecha;


    public Trayectoria(String fecha ) {
        this.fecha=fecha;
    }
    public Trayectoria(){

    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
