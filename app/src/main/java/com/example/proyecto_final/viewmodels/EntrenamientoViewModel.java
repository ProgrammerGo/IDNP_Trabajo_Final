package com.example.proyecto_final.viewmodels;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyecto_final.usecases.HallarRecorrido;

import java.util.Date;

public class EntrenamientoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EntrenamientoViewModel() {

        mText = new MutableLiveData<String>();
    }

    public LiveData<String> getText() {

        return mText;
    }

   public void RecorridoTotal(double latitud, double longitud, Date now){
       mText.setValue(HallarRecorrido.RecorridoTotal(latitud,longitud, now));
   }
}