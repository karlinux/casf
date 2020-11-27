package mx.com.aulaxalapa.capturaelec;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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

public class Logincaptura extends AppCompatActivity {

    // http://www.jsonschema2pojo.org/  Registro POJO

    private static final int WRITE_PERMISSION = 11;
    private static final int ACCESS_FINE_LOCATION = 9;
    private static final int CAMERA_PERMISSION = 8;
    private static final String TAG = "INICIO";
    Button btnIniciar;
    EditText etUsuario, etPassword;
    private Window window;
    String error;
    CapturaClient capturaClient;
    CapturaService capturaService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        permisos();

        File directory = new File(Environment.getExternalStorageDirectory() + Constantes.CARPETA );
        boolean bool = directory.exists();
        if (bool == false) {
            directory.mkdirs();
        }

        String token = "";
        token = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_CORREO);
        if( token != null) {
            if (!token.equals("")) {
                Intent intent = new Intent(getApplicationContext(), InicioCaptura.class);
                startActivity(intent);
                finish();
            }
        }

        capturaClient = new CapturaClient();
        capturaService = new CapturaClient().getService();
        btnIniciar = findViewById(R.id.btnIniciar);
        etUsuario = findViewById(R.id.etUsuario);
        etPassword = findViewById(R.id.etPassword);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            error = "";
            if(etUsuario.getText().toString().equals("")){
                error = "INGRESE EL CORREO DE USUARIO";
                etUsuario.setError(error);
            }else if(etPassword.getText().toString().equals("")){
                error = "INGRESE LA CONTRASEÑA";
                etPassword.setError(error);
            }

            if(error.equals("")){
                goToLogin();
            }else{

            }
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
        final String email = etUsuario.getText().toString();
        String password = etPassword.getText().toString();

        RequestLogin requestLogin = new RequestLogin( email, password );
        Call<ResponseAuth> call = capturaService.doLogin(requestLogin);

        call.enqueue(new Callback<ResponseAuth>() {
            @Override
            public void onResponse(Call<ResponseAuth> call, Response<ResponseAuth> response) {
                if(response.isSuccessful()){
                    Log.e(TAG, "onResponse: " +response.body().getCorreo() );
                    if(email.equals(response.body().getCorreo())){
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CORREO, response.body().getCorreo());
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID_USUARIO, response.body().getIdUsuario());
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_NOMBRE, response.body().getNombre());
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_PERFIL, response.body().getPerfil());
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_IDCOORDINADOR, response.body().getIdCoordinador());
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_IDLIDER, response.body().getIdLider());
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_IDJEFE, response.body().getIdJefe());

                    Intent intent = new Intent(getApplicationContext(), InicioCaptura.class);
                    startActivity(intent);
                    finish();
                    }else{
                        Toast.makeText(getApplicationContext(), "Error de usuario o contraseña" , Toast.LENGTH_SHORT).show();
                    }
                } else {
                        Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseAuth> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if( requestCode == WRITE_PERMISSION ||
                requestCode == CAMERA_PERMISSION||
                requestCode == ACCESS_FINE_LOCATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent inteF = new Intent(getApplicationContext(), Logincaptura.class);
                startActivity(inteF);
                finish();
            }
        }
    }
}
