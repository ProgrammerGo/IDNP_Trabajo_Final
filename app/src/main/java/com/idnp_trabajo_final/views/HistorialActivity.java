package com.idnp_trabajo_final.views;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.idnp_trabajo_final.entities.Recorrido;
import com.idnp_trabajo_final.utils.IComunicaFragments;
import com.idnp_trabajo_final.utils.Constants;


public class HistorialActivity extends AppCompatActivity
        implements ListaRecorridosFragment.OnFragmentInteractionListener,
        DetalleRecorridoFragment.OnFragmentInteractionListener, IComunicaFragments {

    ListaRecorridosFragment listaFragment;
    DetalleRecorridoFragment detalleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        //Validamos que se trabaja en modo portrait desde un smarthPhone
        if(findViewById(R.id.contenedorFragment)!=null){
            Constants.PORTRAIT=true;
            if (savedInstanceState!=null){
                return;
            }
            listaFragment=new ListaRecorridosFragment();
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.contenedorFragment,listaFragment).commit();
        }else{
            Constants.PORTRAIT=false;
        }

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
