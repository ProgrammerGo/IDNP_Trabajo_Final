package com.example.proyecto_final.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EntrenamientoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EntrenamientoViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}