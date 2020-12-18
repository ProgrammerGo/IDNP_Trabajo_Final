package com.idnp_trabajo_final.views;
import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.idnp_trabajo_final.dao.ConexionSQLiteHelper;
import com.idnp_trabajo_final.dao.daoModo;
import com.idnp_trabajo_final.dao.daoRecorrido;
import com.idnp_trabajo_final.entities.Modo;
import com.idnp_trabajo_final.entities.Recorrido;
import com.idnp_trabajo_final.viewmodels.EntrenamientoViewModel;


import java.text.DecimalFormat;
import java.util.ArrayList;
public class EntrenamientoFragment extends Fragment {
    private Spinner spinner;
    private Button iniciar;
    private Button terminar;
    private Button pause;
    private TextView kilometros;
    private MyLocationUpdateReceiver broadcast;
    private boolean update= true;
    private EntrenamientoViewModel homeViewModel;
    private static final long MIN_TIEMPO_ENTRE_UPDATES = 1000 * 5 * 1; // 1 minuto
    private static final long MIN_CAMBIO_DISTANCIA_PARA_UPDATES = 1; // 1.5 metros
    private static daoModo dao;
    private static daoRecorrido daoRecorrido;
    private  PendingIntent  pendingIntent;
    private View root;
    private   long tStart;
    private int id_recorrido;
    private static  LocationManager locationManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                ViewModelProviders.of(this).get(EntrenamientoViewModel.class);
        /* ******************************************************************************************* */

        /* ******************************************************************************************* */
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

            @Override
            public void onClick(View v) {
                /* * *************************************************** Tiempo  */

                tStart = System.currentTimeMillis();

                /* * ***************************************************  */

           renaudar();
            }




        });



        /*-----------------------------------------------------------------------------------------------------------------*/

        /*Observador*/

        /*final Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(String resultado) {
                kilometros.setText(resultado);

            }
        };

        homeViewModel.getText().observe(getViewLifecycleOwner(), observer);*/

        final Observer<double[]> observer = new Observer<double[]>() {
            @Override
            public void onChanged(double[] resultado) {
                DecimalFormat format = new DecimalFormat("#########.###");// el numero de ceros despues del entero
                kilometros.setText(String.valueOf(format.format(resultado[2])));
                id_recorrido= (int) resultado[3];
            }
        };

        homeViewModel.getText().observe(getViewLifecycleOwner(), observer);
/* *************************************************************************** PAUSE*/
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (update) {
                    update = false;
                    pause.setBackgroundResource(R.drawable.ic_baseline_play_circle_outline_24);
                    locationManager.removeUpdates(pendingIntent);
                }
                else {
                    update=true;
                    pause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    renaudar();
                }
            }
        });

        /* *************************************************************************** */
        return root;
    }

    public void configView(View root){
        spinner= (Spinner) root.findViewById(R.id.modo);
        iniciar= (Button)root.findViewById(R.id.iniciar);
        terminar= (Button)root.findViewById(R.id.terminar);
        kilometros=(TextView)root.findViewById(R.id.kilometros) ;
        pause=(Button)root.findViewById(R.id.renaudar) ;
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

public void renaudar(){
    Log.d("prueba ", "Empezo el entrenamiento ");
    locationManager = (LocationManager)
            root.getContext().getSystemService(Context.LOCATION_SERVICE);

    final int locationUpdateRC = 0;
    int flags = PendingIntent.FLAG_UPDATE_CURRENT;
  broadcast = new MyLocationUpdateReceiver(homeViewModel,root);
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
            long tEnd = System.currentTimeMillis();
            long tDelta = tEnd - tStart;
            double elapsedSeconds = tDelta / 1000.0;
            Log.d("prueba", "onClick: " + elapsedSeconds);
            requireActivity().unregisterReceiver(broadcast);
            locationManager.removeUpdates(pendingIntent);
            daoRecorrido = new daoRecorrido(root.getContext());
            Log.d("prueba", "onClick: " + id_recorrido);
            dao.connect();
            Recorrido recorrido = daoRecorrido.getRecorridoById(id_recorrido);
            recorrido.setTiempo(elapsedSeconds);
            daoRecorrido.updateRecorrido(recorrido);
            Log.d("prueba", "onClick: "+recorrido.getDistancia()+" "+recorrido.getTiempo());
            getActivity().finish();
        }
    });
}


}