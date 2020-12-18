package com.idnp_trabajo_final.views;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.idnp_trabajo_final.viewmodels.EntrenamientoViewModel;
import com.idnp_trabajo_final.viewmodels.EstadisticasViewModel;

import java.text.DecimalFormat;

public class EstadisticasFragment extends Fragment {

    private EstadisticasViewModel mViewModel;
    private TextView distancia_ultimaSemana;
    private TextView distancia_ultimoMes;
    private TextView distancia_dia;
    private TextView totalmin;
    private TextView prommindia;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(EstadisticasViewModel.class);
        /* ******************************************************************************************* */

        /* ******************************************************************************************* */
        root = inflater.inflate(R.layout.fragment_estadisticas, container, false);

      config();
      actualiza();
        final Observer<double[]> observer = new Observer<double[]>() {
            @Override
            public void onChanged(double[] resultado) {
                DecimalFormat format = new DecimalFormat("#########.###");

                Log.d("estadisticas2", "AQUI "+resultado[0]);
                Log.d("estadisticas2", "AQUI "+resultado[1]);
                Log.d("estadisticas2", "AQUI "+resultado[2]);
                Log.d("estadisticas2", "AQUI "+resultado[3]);
                distancia_ultimaSemana.setText(String.valueOf(format.format(resultado[0])));
                distancia_ultimoMes.setText(String.valueOf(format.format(resultado[1])));
                distancia_dia.setText(String.valueOf(format.format(resultado[2])));
                totalmin.setText(format.format(resultado[3]));
            }
        };

      mViewModel.getText().observe(getViewLifecycleOwner(), observer);
        /* *************************************************************************** PAUSE*/















        /* *************************************************************************** */
        return root;
    }
    public void config(){
        distancia_ultimaSemana=  root.findViewById(R.id.ultsemanakm);
        distancia_ultimoMes=  root.findViewById(R.id.ultmeskm);
        distancia_dia=  root.findViewById(R.id.kmdiaprom);
        totalmin=root.findViewById(R.id.totalmin);
        prommindia=root.findViewById(R.id.prommindia);


    }
    public void actualiza(){
        mViewModel.HallarValores(root);
    }
}