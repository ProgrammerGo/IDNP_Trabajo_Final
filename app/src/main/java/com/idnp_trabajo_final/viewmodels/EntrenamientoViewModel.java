package com.idnp_trabajo_final.viewmodels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.idnp_trabajo_final.entities.Coordenada;
import com.idnp_trabajo_final.usecases.HallarRecorrido;

import java.util.ArrayList;
import java.util.Date;

public class EntrenamientoViewModel extends ViewModel {

  //  private MutableLiveData<String> mText;
    private MutableLiveData<double[]> mMedida2;


    public EntrenamientoViewModel() {

      //  mText = new MutableLiveData<String>();
        mMedida2= new MutableLiveData<double[]>();
    }

    /*public LiveData<String> getText() {

        return mText;
    }

     */
    public LiveData<double[]> getText() {

        return mMedida2;
    }

    public void RecorridoTotal(double latitud, double longitud, Date now, View root) {
      //  mText.setValue(HallarRecorrido.RecorridoTotal(latitud, longitud, now, root));
        mMedida2.setValue(HallarRecorrido.RecorridoTotal(latitud,longitud,now,root));
    }
}