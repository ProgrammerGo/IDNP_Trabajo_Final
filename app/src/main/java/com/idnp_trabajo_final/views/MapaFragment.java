package com.idnp_trabajo_final.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.idnp_trabajo_final.viewmodels.EntrenamientoViewModel;
import com.idnp_trabajo_final.viewmodels.MapaViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.core.content.PermissionChecker.PERMISSION_GRANTED;

public class MapaFragment extends Fragment implements OnMapReadyCallback {
    private MapaViewModel mViewModel;
    private GoogleMap mMap;
    private static final String ERROR_MSG= "Google Play services are unavailable.";
    private TextView mTextView;
    private LocationRequest mLocationRequest;
    private List<Marker> mMarkers = new ArrayList<>();
    private Polyline mPolyline;
    private static final int LOCATION_PERMISSION_REQUEST = 1;
    public static MapaFragment newInstance() {
        return new MapaFragment();
    }
    private MapaViewModel homeViewModel;
    private View root;
    private FragmentActivity myContext;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //setContentView(R.layout.fragment_mapa);
        homeViewModel =
                ViewModelProviders.of(this).get(MapaViewModel.class);
        root = inflater.inflate(R.layout.fragment_mapa, container, false);
        mTextView = root.findViewById(R.id.myLocationText);
        // Obtain the SupportMapFragment and request the Google Map object.
        /*MapFragment mapFragment =
                (MapFragment)getFragmentManager()
                        .findFragmentById(R.id.map);*/
        //SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);
        GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
        int result = availability.isGooglePlayServicesAvailable(getActivity());
        if (result != ConnectionResult.SUCCESS) {
            if (!availability.isUserResolvableError(result)) {
                Toast.makeText(getActivity(), ERROR_MSG, Toast.LENGTH_LONG).show();
            }
        }
        startTrackingLocation();
            /*mLocationRequest = new LocationRequest()
                    .setInterval(5000)
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);*/
        //return inflater.inflate(R.layout.fragment_mapa, container, false);
        return root;
    }
    private void startTrackingLocation() {
        if (
                ActivityCompat
                        .checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION)==PERMISSION_GRANTED ||
                        ActivityCompat
                                .checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION)==PERMISSION_GRANTED) {
            FusedLocationProviderClient locationClient =
                    LocationServices.getFusedLocationProviderClient(getActivity());
            LocationRequest request = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000); // Update every 5 seconds.
            locationClient.requestLocationUpdates(request, mLocationCallback, null);
        }
    }
    private void startTrackingLocation2(double lat, double lng) {
        if (
                ActivityCompat
                        .checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION)==PERMISSION_GRANTED ||
                        ActivityCompat
                                .checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION)==PERMISSION_GRANTED) {
            FusedLocationProviderClient locationClient =
                    LocationServices.getFusedLocationProviderClient(getActivity());
            LocationRequest request = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(5000); // Update every 5 seconds.
            locationClient.requestLocationUpdates(request, mLocationCallback, null);
        }
    }
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
                /*String latLongString = "No location found";
                for (Location location : locationResult.getLocations()) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    latLongString = "Lat:" + lat + "\nLong:" + lng;
                }
                mTextView.setText(latLongString);*/
            Location location = locationResult.getLastLocation();
            if (location != null) {
                updateTextView(location);
                if (mMap != null) {
                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    Calendar c = Calendar.getInstance();
                    String dateTime
                            = DateFormat.format("MM/dd/yyyy HH:mm:ss",
                            c.getTime()).toString();
                        /*int markerNumber = mMarkers.size()+1;
                        mMarkers.add(mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                                .title(dateTime)
                                .snippet("Marker #" + markerNumber +
                                        " @ " + dateTime)));*/
                    List<LatLng> points = mPolyline.getPoints();
                    points.add(latLng);
                    mPolyline.setPoints(points);
                }
            }
        }
    };

    private void updateTextView(Location location) {
        String latLongString = "No location found";
        if (location != null) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            latLongString = "Lat:" + lat + "\nLong:" + lng;
        }
        mTextView.setText(latLongString);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MapaViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17));
        Double lat = -16.378902237623677;
        Double lng = -71.51167433639307;
        LatLng position = new LatLng(lat, lng);
        //Marker newMarker = mMap.addMarker(new MarkerOptions().position(position));
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(lat,lng))
                .radius(10) // 1000 meters
                .fillColor(Color.argb(50, 255, 0, 0))
                .strokeColor(Color.RED);
        Circle circle = mMap.addCircle(circleOptions);
        PolygonOptions polygonOptions = new PolygonOptions()
                .add(new LatLng(66.992803, -26.369462),
                        new LatLng(51.540138, -2.990557),
                        new LatLng(50.321568, -6.066729),
                        new LatLng(49.757089, -5.231768),
                        new LatLng(50.934844, 1.425947),
                        new LatLng(52.873063, 2.107099),
                        new LatLng(56.124692, -1.738115),
                        new LatLng(67.569820, -13.625322))
                .fillColor(Color.argb(44,00,00,44)).geodesic(true);
            /*List<LatLng> holePoints = new ArrayList<>();
            holePoints.add(new LatLng(53.097936, -2.331377));
            holePoints.add(new LatLng(52.015946, -2.067705));
            holePoints.add(new LatLng(52.117943, 0.383657));
            holePoints.add(new LatLng(53.499125, -1.088511));
            mMap.addPolygon(new PolygonOptions()
                    .add(new LatLng(66.992803, -26.369462),
                            new LatLng(51.540138, -2.990557),
                            new LatLng(50.321568, -6.066729),
                            new LatLng(49.757089, -5.231768),
                            new LatLng(50.934844, 1.425947),
                            new LatLng(52.873063, 2.107099),
                            new LatLng(56.124692, -1.738115),
                            new LatLng(67.569820, -13.625322))
                    .fillColor(Color.argb(44, 00, 00, 44)).addHole(holePoints));
            Polygon polygon = mMap.addPolygon(polygonOptions);
            PolylineOptions polylineOptions = new PolylineOptions()
                    .add(new LatLng(lat,lng),
                            new LatLng(51.540138, -2.990557),
                            new LatLng(50.321568, -6.066729),
                            new LatLng(49.757089, -5.231768),
                            new LatLng(50.934844, 1.425947),
                            new LatLng(52.873063, 2.107099),
                            new LatLng(56.124692, -1.738115),
                            new LatLng(67.569820, -13.625322))
                    .geodesic(true);
            Polyline polyline = mMap.addPolyline(polylineOptions);
            polyline.setClickable(true);*/
        circle.setClickable(true);
        //polygon.setClickable(true);
        BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
        Marker newMarker = mMap.addMarker(new MarkerOptions()
                .position(position)
                .icon(icon));
        //target
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        //target
        PolylineOptions polylineOptions = new PolylineOptions()
                .color(Color.CYAN)
                .geodesic(true);
        mPolyline = mMap.addPolyline(polylineOptions);
    }
}
