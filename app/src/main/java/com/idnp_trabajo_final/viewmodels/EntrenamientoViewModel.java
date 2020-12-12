package com.idnp_trabajo_final.viewmodels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.idnp_trabajo_final.entities.Coordenada;
import com.idnp_trabajo_final.usecases.HallarRecorrido;

import java.util.Date;

public class EntrenamientoViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Coordenada> mMedida;

    public EntrenamientoViewModel() {

        mText = new MutableLiveData<String>();
        mMedida=new MutableLiveData<Coordenada>();
    }

    public LiveData<String> getText() {

        return mText;
    }
    public LiveData<Coordenada> getMedida() {

        return mMedida;
    }
    public void RecorridoTotal(double latitud, double longitud, Date now, View root) {
        mText.setValue(HallarRecorrido.RecorridoTotal(latitud, longitud, now, root));
    }
}