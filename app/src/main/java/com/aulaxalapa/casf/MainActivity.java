package com.aulaxalapa.casf;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.SharedPreferencesManager;
import com.aulaxalapa.casf.retrofit.CasfClient;
import com.aulaxalapa.casf.retrofit.CasfService;
import com.aulaxalapa.casf.retrofit.request.RequestLogin;
import com.aulaxalapa.casf.retrofit.response.ResponseAuth;
import com.aulaxalapa.casf.R;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int WRITE_PERMISSION = 11;
    private static final int READ_PHONE_STATE = 10;
    private static final int ACCESS_FINE_LOCATION = 9;
    private static final int CAMERA_PERMISSION = 8;

    private Button btnIniciar;
    private String TAG = "MainActivity";
    private EditText etUsuario, etPassword;
    CasfClient casfClient;
    CasfService casfService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent msgIntent = new Intent(MainActivity.this, ServicioCasf.class);
        //msgIntent.putExtra("iteraciones", 10);
        //startService(msgIntent);

        File directory = new File(Environment.getExternalStorageDirectory() + "/CASF" );
        boolean bool = directory.exists();
        if (bool == false) {
            directory.mkdirs();
        }

        permisos();

        String token = "";
        token = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_TOKEN);
        if( token != null) {
            if (!token.equals("")) {
                Intent intent = new Intent(getApplicationContext(), BuscarCasf.class);
                startActivity(intent);
                finish();
            }
        }

        casfClient = new CasfClient();
        casfService = new CasfClient().getService();

        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);
        btnIniciar = findViewById(R.id.btnRegresar);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });

    }

    private void permisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                }
            } else {

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);

                    } else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
                    }
                } else {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                        } else {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                        }
                    }
                }
            }
        }
    }

    public void goToLogin(){
        String email = etUsuario.getText().toString();
        String password = etPassword.getText().toString();
        RequestLogin requestLogin = new RequestLogin( email, password );
        Call<ResponseAuth> call = casfService.doLogin(requestLogin);
        call.enqueue(new Callback<ResponseAuth>() {
            @Override
            public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                if(response.isSuccessful()){

                    Toast.makeText(getApplicationContext(), "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();

                    Log.e(TAG, "onResponse: " + response.body().getToken() );
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_TOKEN, response.body().getToken());
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_NOMBRE, response.body().getNombre());
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CORREO, response.body().getEmail());
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_IDUSU, response.body().getIdUsuario());

                    Intent intent = new Intent(getApplicationContext(), UniversosCasf.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Error de usuario o contraseña" + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseAuth> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage() );
                Toast.makeText(getApplicationContext(), "Problemas de conexión intentelo de nuevo", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if( requestCode == WRITE_PERMISSION ||
                requestCode == READ_PHONE_STATE ||
                requestCode == CAMERA_PERMISSION||
                requestCode == ACCESS_FINE_LOCATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent inteF = new Intent(this, MainActivity.class);
                startActivity(inteF);
                finish();
            }else{
                finish();
            }
        }
    }
}
