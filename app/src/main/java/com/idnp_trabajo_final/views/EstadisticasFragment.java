package com.idnp_trabajo_final.views;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idnp_trabajo_final.viewmodels.EstadisticasViewModel;

public class EstadisticasFragment extends Fragment {

    private EstadisticasViewModel mViewModel;

    public static EstadisticasFragment newInstance() {
        return new EstadisticasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estadisticas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EstadisticasViewModel.class);
        // TODO: Use the ViewModel
    }

}