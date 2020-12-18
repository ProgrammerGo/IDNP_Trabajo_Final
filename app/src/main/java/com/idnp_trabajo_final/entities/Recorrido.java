package com.idnp_trabajo_final.entities;

public class Recorrido   {
    private Integer id;
    private Integer modo;
    private Integer usuario;
    private String fecha;
    private double tiempo;
    private float distancia;

    public Recorrido(Integer modo, Integer usuario, String fecha, double tiempo, float distancia) {

        this.modo = modo;
        this.usuario = usuario;
        this.fecha = fecha;
        this.tiempo = tiempo;
        this.distancia = distancia;
    }
    public Recorrido (){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModo() {
        return modo;
    }

    public void setModo(Integer modo) {
        this.modo = modo;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }
}
