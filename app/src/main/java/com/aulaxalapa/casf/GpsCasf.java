package com.aulaxalapa.casf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.SharedPreferencesManager;
import com.aulaxalapa.casf.data.Handler_sqlite;

public class GpsCasf extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private Button btnRegistrar;
    private TextView tvUsuario, tvDatos;
    private EditText etLatitud, etLongitud;
    private String folioCont, guardado, _ID, latitud, longitud, fecha, imeistring;
    private Handler_sqlite base;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        base = new Handler_sqlite(this);

        tvDatos = findViewById(R.id.tvDatos);
        String id = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID);
        base.abrir();
        _ID = base.getId("1");
        Cursor c = base.getDatos(id);
        c.moveToLast();
        folioCont = c.getString(1);
        tvDatos.setText(folioCont + " Folio: " + c.getString(9) +
                "\n"+ c.getString(2) +
                " " + c.getString(3) +
                " " + c.getString(4) +
                "\n" + c.getString(5) + " " + c.getString(6) +
                "\nMedidor: " + c.getString(12));
        base.cerrar();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        btnRegistrar = findViewById(R.id.btnCargar);
        etLatitud = findViewById(R.id.etClave);
        etLongitud = findViewById(R.id.etLongitud);
        tvUsuario = findViewById(R.id.tvUsuario);
        etLatitud.setKeyListener(null);
        etLongitud.setKeyListener(null);
        String usuario = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NOMBRE);
        tvUsuario.setText(usuario);

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
                    Toast.makeText(GpsCasf.this, "Espere se obtenga los datos GPS", Toast.LENGTH_SHORT).show();
                }else{
                    base.abrir();
                    base.actualizaGps(_ID, latitud,longitud);
                    base.cerrar();
                    Intent intent = new Intent(getApplicationContext(), FotoMedidorCasf.class);
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
        Intent intent = new Intent(this, LecturaCasf.class);
        startActivity(intent);
        finish();
    }
}
