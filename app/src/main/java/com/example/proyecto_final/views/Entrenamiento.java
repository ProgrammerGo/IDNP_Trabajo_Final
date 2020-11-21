package com.example.proyecto_final.views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.proyecto_final.DAO.ConexionSQLiteHelper;
import com.example.proyecto_final.DAO.Utilidades;
import com.example.proyecto_final.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class Entrenamiento extends AppCompatActivity {
    /*
    private Spinner spinner;
    private Button iniciar;
    private TextView kilometros;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entrenamiento);
        BottomNavigationView navView = findViewById(R.id.nav_view);
     AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_entrenamiento, R.id.navigation_mapa, R.id.navigation_entrenamiento)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

}