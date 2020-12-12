package com.idnp_trabajo_final.views;
import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.idnp_trabajo_final.dao.ConexionSQLiteHelper;
import com.idnp_trabajo_final.dao.daoModo;
import com.idnp_trabajo_final.entities.Modo;
import com.idnp_trabajo_final.viewmodels.EntrenamientoViewModel;


import java.util.ArrayList;
public class EntrenamientoFragment extends Fragment {
    private Spinner spinner;
    private Button iniciar;
    private Button terminar;
    private TextView kilometros;
    private EntrenamientoViewModel homeViewModel;
    private static final long MIN_TIEMPO_ENTRE_UPDATES = 1000 * 5 * 1; // 1 minuto
    private static final long MIN_CAMBIO_DISTANCIA_PARA_UPDATES = 50; // 1.5 metros
    private static daoModo dao;
    private double longitud;
    private double latitud;
    private View root;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(EntrenamientoViewModel.class);
        root = inflater.inflate(R.layout.fragment_entrenamiento, container, false);

        /*-----------------------------------------------------------------------------------------------------------------*/
        /*
         * Prepara la interfaz
         * */
        configView(root);
        /*-----------------------------------------------------------------------------------------------------------------*/

        /*PERMISOS*/
        int permissionCheck= ContextCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck== PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) root.getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)){

            }
            else{
                ActivityCompat.requestPermissions((Activity) root.getContext(), new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        }
        /*-----------------------------------------------------------------------------------------------------------------*/

        /*Ubicacion*/

        iniciar.setOnClickListener(new View.OnClickListener() {

            PendingIntent pendingIntent= null;
            @Override
            public void onClick(View v) {
                Log.d("prueba2 ", "onClick: ");
                final LocationManager locationManager = (LocationManager)
                        root.getContext().getSystemService(Context.LOCATION_SERVICE);

                final int locationUpdateRC = 0;
                int flags = PendingIntent.FLAG_UPDATE_CURRENT;
                MyLocationUpdateReceiver broadcast = new MyLocationUpdateReceiver(homeViewModel,root);
                if(broadcast!=null){
                    IntentFilter intentFilter= new IntentFilter();
                    intentFilter.addAction(LocationManager.KEY_LOCATION_CHANGED);
                    requireActivity().registerReceiver(broadcast,intentFilter);
                }
                else{

                }
                Intent intent = new Intent(LocationManager.KEY_LOCATION_CHANGED);
                pendingIntent =PendingIntent.getBroadcast(root.getContext(), locationUpdateRC, intent, flags);

                if (ActivityCompat.checkSelfPermission(root.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(root.getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                }

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIEMPO_ENTRE_UPDATES,
                        MIN_CAMBIO_DISTANCIA_PARA_UPDATES, pendingIntent);

                terminar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        locationManager.removeUpdates(pendingIntent);

                    }
                });

            }




        });



        /*-----------------------------------------------------------------------------------------------------------------*/

        /*Observador*/

        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(String resultado) {
                kilometros.setText(resultado);

            }
        };

        homeViewModel.getText().observe(getViewLifecycleOwner(), observer);


        return root;
    }

    public void configView(View root){
        spinner= (Spinner) root.findViewById(R.id.modo);
        iniciar= (Button)root.findViewById(R.id.iniciar);
        terminar= (Button)root.findViewById(R.id.terminar);
        kilometros=(TextView)root.findViewById(R.id.kilometros) ;
        ComboBox(root);
        Sppiner();


    }
    public void ComboBox(View root){
        ArrayList<String> opciones = new ArrayList<String>();
        dao= new daoModo(getContext());
        dao.connect();
        for (Modo us:dao.selectModos()) {
            opciones.add(us.getNombre());
        }

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item,opciones);
        spinner.setAdapter(adapter);
    }
public void Sppiner(){
        daoModo dao= new daoModo(root.getContext());
    Log.d("Quiero", "Sppiner: "+dao.selectModos().size());
        if (dao.selectModos().size()==0) {
            Log.d("Quiero2", "Sppiner: "+dao.selectModos().size());

            dao.connect();
            dao.insertModo(new Modo("Correr", "Velocidad alta"));
            dao.connect();
            dao.insertModo(new Modo("Caminata", "Velocidad lenta "));
        }
        }
}