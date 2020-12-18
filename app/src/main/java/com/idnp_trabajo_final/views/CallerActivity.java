package com.idnp_trabajo_final.views;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.idnp_trabajo_final.entities.Recorrido;
import com.idnp_trabajo_final.utils.IComunicaFragments;

public class CallerActivity extends AppCompatActivity implements ListaRecorridosFragment.OnFragmentInteractionListener,
        DetalleRecorridoFragment.OnFragmentInteractionListener, IComunicaFragments {

    private TextView mTextView;
    ListaRecorridosFragment listaFragment;
    DetalleRecorridoFragment detalleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_entrenar, R.id.navigation_mapa, R.id.navigation_estadisticas,R.id.navigation_musica,R.id.navigation_historial)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        mTextView = (TextView) findViewById(R.id.text);
    }

    @Override
    public void enviarRecorrido(Recorrido recorrido) {

        detalleFragment= new DetalleRecorridoFragment();
        Bundle bundleEnvio= new Bundle();
        bundleEnvio.putSerializable("objeto", recorrido);
        detalleFragment.setArguments(bundleEnvio);

        //cargar el fragmetn en el activity
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment,detalleFragment).addToBackStack(null).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}