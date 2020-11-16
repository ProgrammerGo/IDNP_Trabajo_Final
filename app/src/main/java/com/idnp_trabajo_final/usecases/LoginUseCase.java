package com.idnp_trabajo_final.usecases;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.entities.Usuario;


public class LoginUseCase {
    public static int loginUsuario(daoUsuario dao, String mail, String pass) {
        if (mail.equals("")) {
            return -1;
        } else if (pass.equals("")) {
            return -2;
        } else if (dao.login(mail, pass) == 1) {
            Usuario u = dao.getUsuario(mail, pass);
            return u.getId();
        } else {
            return -3;
        }
    }

    public static int loginAnonimo(daoUsuario dao) {
        Usuario ua = new Usuario();
        boolean ins = dao.insertUsuario(ua);
        Usuario u = dao.getUsuario("", "");
        return u.getId();
    }
}
