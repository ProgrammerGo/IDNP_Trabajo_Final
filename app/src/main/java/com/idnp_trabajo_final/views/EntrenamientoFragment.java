package com.idnp_trabajo_final.views;
import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.idnp_trabajo_final.dao.ConexionSQLiteHelper;
import com.idnp_trabajo_final.dao.daoModo;
import com.idnp_trabajo_final.dao.daoRecorrido;
import com.idnp_trabajo_final.entities.Modo;
import com.idnp_trabajo_final.entities.Recorrido;
import com.idnp_trabajo_final.viewmodels.EntrenamientoViewModel;
import com.idnp_trabajo_final.viewmodels.MapaViewModel;
import com.idnp_trabajo_final.viewmodels.MapaViewModel;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.List;

public class EntrenamientoFragment extends Fragment implements OnMapReadyCallback {
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
    private PendingIntent pendingIntent2;
    private final static String CHANNEL_ID= "Notification";
    private final static int  NOTIFICATION_ID= 0;
    private MapaViewModel mViewModel;
    private GoogleMap mMap;
    private static final String ERROR_MSG= "Google Play services are unavailable.";
    private TextView mTextView;
    private LocationRequest mLocationRequest;
    private List<Marker> mMarkers = new ArrayList<>();
    private Polyline mPolyline;
    private static final int LOCATION_PERMISSION_REQUEST = 1;
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
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        int result = availability.isGooglePlayServicesAvailable(getActivity());
        if (result != ConnectionResult.SUCCESS) {
            if (!availability.isUserResolvableError(result)) {
                Toast.makeText(getActivity(), ERROR_MSG, Toast.LENGTH_LONG).show();
            }
        }
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
                LatLng latLng = new LatLng(resultado[0],resultado[1]);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                /*Calendar c = Calendar.getInstance();
                String dateTime = DateFormat.format("MM/dd/yyyy HH:mm:ss", c.getTime()).toString();
                int markerNumber = mMarkers.size()+1;
                mMarkers.add(mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title(dateTime)
                        .snippet("Marker #" + markerNumber +
                                " @ " + dateTime)));*/
                List<LatLng> points = mPolyline.getPoints();
                points.add(latLng);
                mPolyline.setPoints(points);
            }
        };

        homeViewModel.getText().observe(getViewLifecycleOwner(), observer);
/* *************************************************************************** PAUSE*/
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (update) {
                    update = false;
                    createNotification();
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
    Toast toast1 = Toast.makeText(getActivity(),"Se inicio el recorrido", Toast.LENGTH_SHORT);
    toast1.show();
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
            /*Toast toast1 = Toast.makeText(getActivity(),"Termino el recorrido", Toast.LENGTH_SHORT);
            toast1.show();*/
            long tEnd = System.currentTimeMillis();
            long tDelta = tEnd - tStart;
            double elapsedSeconds = tDelta / 1000.0;
            Log.d("prueba", "onClick: " + elapsedSeconds);
            requireActivity().unregisterReceiver(broadcast);
            if(update==false)
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
    public void createNotification(){
        NotificationCompat.Builder builder= new NotificationCompat.Builder(root.getContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_directions_run_24);
        builder.setContentTitle("Goo");
        builder.setContentText("Tu entrenamiento esta pausado");
        builder.setColor(Color.BLACK);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.MAGENTA, 1000, 1000);
        builder.setVibrate(new long[]{1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        NotificationManagerCompat notificationManagerCompat= NotificationManagerCompat.from(root.getContext());
        notificationManagerCompat.notify(NOTIFICATION_ID,builder.build());

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        Double lat = -16.378902237623677;
        Double lng = -71.51167433639307;
        LatLng position = new LatLng(lat, lng);
        Marker newMarker = mMap.addMarker(new MarkerOptions().position(position));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position,15));
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        PolylineOptions polylineOptions = new PolylineOptions()
                .color(Color.GREEN)
                .geodesic(true);
        mPolyline = mMap.addPolyline(polylineOptions);
    }
}