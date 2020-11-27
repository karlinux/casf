package mx.com.aulaxalapa.capturaelec;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.SharedPreferencesManager;
import mx.com.aulaxalapa.capturaelec.retrofit.CapturaClient;
import mx.com.aulaxalapa.capturaelec.retrofit.CapturaService;
import mx.com.aulaxalapa.capturaelec.retrofit.request.RequestLogin;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseAuth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Versioncaptura extends AppCompatActivity {

    // http://www.jsonschema2pojo.org/  Registro POJO


    Button btnIniciar;
    TextView tvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        btnIniciar = findViewById(R.id.btnIniciar);
        tvVersion = findViewById(R.id.tvVersion);
        int v = (Constantes.VERSION) + 1;
        tvVersion.setText(getString(R.string.version) +  v );

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(Constantes.API_RUTA_BASE_DESCARGA);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
