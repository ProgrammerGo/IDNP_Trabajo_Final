package com.idnp_trabajo_final.viewmodels;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.idnp_trabajo_final.usecases.EstadisticasUseCase;

import java.util.Date;


public class EstadisticasViewModel extends ViewModel {
    //  private MutableLiveData<String> mText;
    private MutableLiveData<double[]> mMedida2;


    public EstadisticasViewModel() {

        //  mText = new MutableLiveData<String>();
        mMedida2= new MutableLiveData<double[]>();
    }


    public LiveData<double[]> getText() {

        return mMedida2;
    }

    public void HallarValores(View root) {
                mMedida2.setValue(EstadisticasUseCase.HallarValores(root));
    }
}