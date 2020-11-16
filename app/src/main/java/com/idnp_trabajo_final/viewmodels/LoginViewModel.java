package com.idnp_trabajo_final.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.usecases.LoginUseCase;

public class LoginViewModel extends ViewModel {
    /*
    private MutableLiveData<String> screentext;
    private int id;

    public LoginViewModel(){
        screentext=new MutableLiveData<>();
    }
    public LiveData<String> getMessage(){
        return screentext;
    }
    public void loginMessage(daoUsuario dao, String u, String p){
        screentext.setValue(LoginUseCase.loginMessage( dao,  u,  p));
    }
    public int login(daoUsuario dao, String u, String p){
        Usuario ux =LoginUseCase.login(dao,u,p);
        return ux.getId();
    }
     */
    private MutableLiveData<String> screentext;
    public LoginViewModel(){
        screentext=new MutableLiveData<>();
    }
    public LiveData<String> getMessage(){
        return screentext;
    }
    public int login(daoUsuario dao,String mail, String pass){
        int numLogin= LoginUseCase.loginUsuario(dao, mail, pass);
        if(numLogin==-1){
            screentext.setValue("INGRESE SU CORREO");
            return -1;
        }
        else if(numLogin==-2){
            screentext.setValue("INGRESE SU CONTRASEÑA");
            return -1;
        }
        else if(numLogin==-3){
            screentext.setValue("Usuario no registrado");
            return -1;
        }
        else{
            screentext.setValue("Bienvenido!!!");
            return numLogin;
        }

    }
    public int loginAnonimo(daoUsuario dao) {
        int numLogin = LoginUseCase.loginAnonimo(dao);
        screentext.setValue("Bienvenido!!!");
        return numLogin;
    }
}
