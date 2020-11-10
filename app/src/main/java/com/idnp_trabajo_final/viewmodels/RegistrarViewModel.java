package com.idnp_trabajo_final.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.entiities.Usuario;
import com.idnp_trabajo_final.usecases.LoginUseCase;
import com.idnp_trabajo_final.usecases.RegistrarUseCase;

public class RegistrarViewModel extends ViewModel {
    private MutableLiveData<String> screentext;

    public RegistrarViewModel(){
        screentext=new MutableLiveData<>();
    }
    public LiveData<String> getMessage(){
        return screentext;
    }
    public void registrarMessage(daoUsuario dao, String mail, String name, String pass){
        screentext.setValue(RegistrarUseCase.registrarMessage(dao, mail,name,pass));
    }
}
