package mx.com.aulaxalapa.capturaelec;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.SharedPreferencesManager;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqlite;

public class GpsOcr extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private Button btnRegistrar;
    private EditText etLatitud, etLongitud;
    private String CLAVE, latitud, longitud;
    private Handler_sqlite base;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        base = new Handler_sqlite(this);

        CLAVE = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_CLAVE);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        btnRegistrar = findViewById(R.id.btnRegresar);
        etLatitud = findViewById(R.id.etClave);
        etLongitud = findViewById(R.id.etLongitud);
        etLatitud.setKeyListener(null);
        etLongitud.setKeyListener(null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, this);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location == null){
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        Location currentLocation = location;

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                latitud = etLatitud.getText().toString();
                longitud = etLongitud.getText().toString();

                if(latitud.equals("") || longitud.equals("") ){
                    Toast.makeText(getApplicationContext(), "Espere se obtenga los activity_datos GPS", Toast.LENGTH_SHORT).show();
                }else{
                    base.abrir();
                    base.actualizaGps(CLAVE, latitud,longitud);
                    base.cerrar();
                    Intent intent = new Intent(getApplicationContext(), InicioCaptura.class);
                    startActivity(intent);
                    finish();
                }

            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        etLatitud.setText(""+location.getLatitude());
        etLongitud.setText(""+location.getLongitude());

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, GpsOcr.class);
        startActivity(intent);
        finish();
    }
}
