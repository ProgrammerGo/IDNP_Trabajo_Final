package com.example.proyecto_final.views;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.proyecto_final.DAO.ConexionSQLiteHelper;
import com.example.proyecto_final.DAO.Utilidades;
import com.example.proyecto_final.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;

public class Entrenamiento extends AppCompatActivity {
    private Spinner spinner;
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
        configView();
    }
    public void configView(){
        spinner= (Spinner) findViewById(R.id.modo);
         ArrayList<String> opciones = new ArrayList<String>();
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this, "MODO", null,1 );
        SQLiteDatabase db= conn.getWritableDatabase();
        String[] campos = new String[] {"nombre", "descripcion"};
        Cursor c = db.query(Utilidades.TABLA_MODO, campos, null, null, null, null, null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                String nombre= c.getString(0);
                String descripcion= c.getString(1);
                opciones.add(nombre);

            } while(c.moveToNext());
        }

        db.close();
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,opciones);
        spinner.setAdapter(adapter);

    }
}