package com.idnp_trabajo_final.usecases;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.entiities.Usuario;
import com.idnp_trabajo_final.views.EditarActivity;


public class EditarUseCase {

    public static int editarUsuario(Usuario u, daoUsuario dao, String mail, String name, String pass) {
        u.setMail(mail);
        u.setNombre(name);
        u.setPassword(pass);
        if (!u.isNull()) {
            return -1;
        } else if (dao.updateUsuario(u)) {
            return u.getId();
        } else {
            return -2;
        }
    }
}
