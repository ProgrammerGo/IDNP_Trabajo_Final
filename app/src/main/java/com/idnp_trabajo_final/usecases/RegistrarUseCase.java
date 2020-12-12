package com.idnp_trabajo_final.usecases;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.entities.Usuario;


public class RegistrarUseCase {

    public static String registrarMessage(daoUsuario dao, String mail, String name, String pass){
        dao.connect();
        Usuario u=new Usuario();
        u.setMail(mail);
        u.setNombre(name);
        u.setPassword(pass);
        if(!u.isNull()){
            return "ERROR: Campos Vacíos !!";
        }
        else if(dao.insertUsuario(u)) {
            return "Registro Exitoso!!!";
        }
        else {
            return "Usuario ya registrado!";
        }
    }

}
