package com.idnp_trabajo_final.entiities;

public class Usuario {
    int id;
    String nombre,password,mail;
    public Usuario(){

        this.nombre = "Anónimo";
        this.password = "";
        this.mail = "";

    }

    public Usuario(String nombre, String password, String mail) {
        this.nombre = nombre;
        this.password = password;
        this.mail = mail;
    }

    public boolean isNull(){
        if(nombre.equals("") || mail.equals("") || password.equals("")) {
            return false;
        }
        else{
            return true;
        }
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
