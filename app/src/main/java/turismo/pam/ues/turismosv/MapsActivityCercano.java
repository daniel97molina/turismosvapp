package turismo.pam.ues.turismosv;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivityCercano extends AppCompatActivity implements OnMapReadyCallback,GoogleApiClient.OnConnectionFailedListener {
    final static String[] INIT_PERMS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    };
    private GoogleApiClient mGoogleApi;
    final static int INITIAL_REQUEST = 1337;
    Location location;
    LocationManager locationManager;
    LocationListener locationListener;
    private GoogleMap mMap;
    AlertDialog alert = null;
    SQLiteDatabase db;

    //Instancia de SitioService
    SitioService sitioService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_cercano);

        //Manejo de la BD
        ConexionSQLiteHelper conexion = new ConexionSQLiteHelper(this, "DBTurismo", null, 1);
        db = conexion.getWritableDatabase();
        sitioService = new SitioService(db, getApplicationContext());
//   mGoogleApi = new GoogleApiClient
//                .Builder(this)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
//                .enableAutoManage(this, this)
//                .build();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location locationIn) {
                location = locationIn;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        if (apiGreaterThan23()) {
            if (!canAccesLocation() || !canAccessCoarseLocation() || !canAccessInternet()) {
                requestPermissions(INIT_PERMS, INITIAL_REQUEST);
            }
        }
        getLatlng();

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            alert();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (canAccessInternet() && canAccessCoarseLocation() && canAccesLocation()) {
        getLatlng();

        } else {
            Toast.makeText(getApplicationContext(), "Permisos ausentes, debe autorizarlos", Toast.LENGTH_LONG).show();
            System.exit(0);
        }
    }

    @SuppressLint("MissingPermission")
    private void getLatlng() {
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, locationListener);
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //getLatlng();
        // Add a marker in Sydney and move the camera
        LatLng lugarActual = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        List<Sitio> listaCercanos = sitioService.findAllSitios();
        for (int i=0; i<listaCercanos.size();i++){
            mMap.addMarker(new MarkerOptions().position(new LatLng(listaCercanos.get(i).getLatitud(), listaCercanos.get(i).getLongitud())).title(listaCercanos.get(i).getNombre().toString()));
        }
        mMap.addMarker(new MarkerOptions().position(lugarActual).title("Aqui estoy"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lugarActual, 17f));
    }

    public boolean hasPermission(String perm) {
        return apiGreaterThan23() ? PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this, perm) : true;
    }

    public boolean canAccesLocation() {
        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    public boolean canAccessCoarseLocation() {
        return (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    public boolean canAccessInternet() {
        return (hasPermission(Manifest.permission.INTERNET));
    }

    public boolean apiGreaterThan23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public void alert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El sistema GPS esta desactivado, ¿Desea Activarlo?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(), "The app cannot run without gps service...bye", Toast.LENGTH_LONG);
                        System.exit(0);
                    }
                });
        alert = builder.create();
        alert.show();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
