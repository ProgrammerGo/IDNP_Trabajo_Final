package com.example.proyecto_final.entities;

public class Modo {
   private Integer id;
    private String nombre;
    private String descripcion;

    public Modo(){

    }

    public Modo( String nombre, String descripcion) {
       // this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

   public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
