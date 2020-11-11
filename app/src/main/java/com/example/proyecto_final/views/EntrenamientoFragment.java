package com.example.proyecto_final.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_final.R;
import com.example.proyecto_final.viewmodels.EntrenamientoViewModel;

public class EntrenamientoFragment extends Fragment {

    private EntrenamientoViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(EntrenamientoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_entrenamiento, container, false);
        final TextView textView = root.findViewById(R.id.text_entrenamiento);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}