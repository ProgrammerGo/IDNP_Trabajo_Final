package com.idnp_trabajo_final.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.idnp_trabajo_final.dao.daoUsuario;
import com.idnp_trabajo_final.entities.Usuario;
import com.idnp_trabajo_final.usecases.EditarUseCase;

public class EditarViewModel extends ViewModel {
    private MutableLiveData<String> screentext;
    public EditarViewModel(){
        screentext=new MutableLiveData<>();
    }
    public LiveData<String> getMessage(){
        return screentext;
    }
    public int editarUsuario(Usuario u, daoUsuario dao,String mail, String name, String pass){
        int numeditar= EditarUseCase.editarUsuario(u, dao, mail, name, pass);
        if(numeditar==-1){
            screentext.setValue("ERROR: Campos Vacíos !!");
            return -1;
        }
        else if(numeditar==-2){
            screentext.setValue("No se pudo realizar la actualización!");
            return -1;
        }
        else{
            screentext.setValue("Actualización Exitosa!!!");
            return numeditar;
        }

    }
}
