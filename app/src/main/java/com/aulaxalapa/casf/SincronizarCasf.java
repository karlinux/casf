package com.aulaxalapa.casf;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.MyApp;
import com.aulaxalapa.casf.common.SharedPreferencesManager;
import com.aulaxalapa.casf.data.CasfViewModel;
import com.aulaxalapa.casf.data.Handler_sqlite;
import com.aulaxalapa.casf.retrofit.CasfClient;
import com.aulaxalapa.casf.retrofit.CasfService;
import com.aulaxalapa.casf.retrofit.request.RequestInsert;
import com.aulaxalapa.casf.retrofit.response.ResponseFoto;
import com.aulaxalapa.casf.retrofit.response.ResponseInsert;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SincronizarCasf extends AppCompatActivity {
    CasfViewModel casfViewModel;
    private Handler_sqlite base = new Handler_sqlite(this);
    private String imagen, baseFoto;
    private Button btnRegresar;
    private String TAG = "SincronizarCasf";
    private String usuario;
    private TextView tvUsuario;
    private String carpeta = "CASF";
    private final String enviar = "1";
    private final String modificar = "0";
    int count;
    private ProgressBar spinner;

    CasfService casfService;
    CasfClient casfClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sincro);
        casfViewModel = ViewModelProviders.of(this).get(CasfViewModel.class);
        casfClient = casfClient.getInstance();
        casfService = casfClient.getService();

        usuario = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_IDUSU);
        imagen =  getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        imagen = Environment.getExternalStorageDirectory() + "/"+ carpeta +"/";

        tvUsuario = findViewById(R.id.tvUsuario);
        String usr = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NOMBRE);
        tvUsuario.setText(usr);
        btnRegresar = findViewById(R.id.btnCargar);
        spinner = findViewById(R.id.progreso);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InicioCasf.class);
                startActivity(intent);
                finish();
            }
        });

        if (conectadoWifi()) {
            Cursor c = base.getEnvio(enviar);
            count = c.getCount();
            enviarImagenes( );
            if(count > 0){
                while (c.moveToNext()){
                String id = c.getString(0);
                String folioCont = c.getString(1);
                String lectura = c.getString(2);
                String latitud = c.getString(3);
                String longitud = c.getString(4);
                String fotoMedidor = c.getString(5);
                String fotoInconsistencia1 = c.getString(6);
                String fotoInconsistencia2 = c.getString(7);
                String fotoInconsistencia3 = c.getString(8);
                String idInconsistencia1 = c.getString(9);
                String idInconsistencia2 = c.getString(10);
                String idInconsistencia3 = c.getString(11);
                String fecha = c.getString(12);
                String lectAnt = c.getString(13);
                String periodo = c.getString(14);
                String anio = c.getString(15);

                    StringBuilder stringFecha = new StringBuilder();
                    stringFecha.append(imagen);
                    stringFecha.append("/");
                    baseFoto = stringFecha.toString();

                     File file = new File(imagen+fotoMedidor);
                    if(file.exists()){
                        casfViewModel.subirFoto(imagen+fotoMedidor, 1);
                    }

                    File file2 = new File(imagen+fotoInconsistencia1);
                    if(file2.exists()){
                        casfViewModel.subirFoto(imagen+fotoInconsistencia1, 2);
                        //Log.e(TAG, "onCreate: " + imagen+fotoInconsistencia1 );
                    }
                    File file3 = new File(imagen+fotoInconsistencia2);
                    if(file3.exists()){
                        casfViewModel.subirFoto(imagen+fotoInconsistencia2, 2);
                        //Log.e(TAG, "onCreate: " + imagen+fotoInconsistencia2 );
                    }
                    File file4 = new File(imagen+fotoInconsistencia3);
                    if(file4.exists()){
                        casfViewModel.subirFoto(imagen+fotoInconsistencia3, 2);
                        //Log.e(TAG, "onCreate: " + imagen+fotoInconsistencia3 );
                    }

                subirDatos(id, folioCont, lectura, latitud, longitud, fotoMedidor,
                        fotoInconsistencia1==null?"":fotoInconsistencia1,
                        fotoInconsistencia2==null?"":fotoInconsistencia2,
                        fotoInconsistencia3==null?"":fotoInconsistencia3,
                        idInconsistencia1, idInconsistencia2, idInconsistencia3, fecha, usuario,
                        lectAnt, periodo, anio  );
                }
            }else{
                Toast.makeText(this, "No hay registros para enviar", Toast.LENGTH_SHORT).show();
                spinner.setVisibility(View.GONE);
            }

        }else{
            Toast.makeText(this, "No está conectado a un WiFi", Toast.LENGTH_SHORT).show();
        }

    }

    protected Boolean conectadoWifi() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                if (info.isConnected()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void subirDatos( String id, String folioCont, String lectura, String latitud, String longitud, String fotoMedidor,
                            String fotoInconsistencia1, String fotoInconsistencia2, String fotoInconsistencia3,  String idInconsistencia1,
                            String idInconsistencia2,  String idInconsistencia3, String fecha, String usuario, String lectAnt,
                            String periodo, String anio ){

        RequestInsert requestInsert = new RequestInsert( id, folioCont, lectura, latitud, longitud, fotoMedidor, fotoInconsistencia1,
                fotoInconsistencia2, fotoInconsistencia3, idInconsistencia1, idInconsistencia2, idInconsistencia3, fecha, usuario, lectAnt , periodo , anio );

        Call<ResponseInsert> call = casfService.subirDatos(requestInsert);
        call.enqueue(new Callback<ResponseInsert>() {
            @Override
            public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                if(response.isSuccessful()){
                    String id = response.body().getId();
                    base.setEstado( id, "_ID", modificar, Constantes.CASF);
                    Log.e(TAG, "onResponseac: " + id );
                    count--;
                    if(count==0){
                        spinner.setVisibility(View.GONE);
                        Toast.makeText(SincronizarCasf.this, "Datos enviados al servidor", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MyApp.getContext(), "Hubo problemas para conectarse a internet", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: " +response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailureaqui: " + t.getCause() );
            }
        });
    }

    private void enviarImagenes( ) {
        imagen = Environment.getExternalStorageDirectory() + Constantes.CARPETA;
        Cursor c = base.getImagenes("0");
        if(c.getCount()>0){
            while(c.moveToNext()){
                Log.e(TAG, "enviarImagenes: " + imagen + c.getString(0) + ".jpg");
                subirFoto(  imagen + c.getString(0), c.getString(1));
            }
        }
    }

    public void subirFoto( String fileFoto, String tipo ){
        File file = new File( fileFoto);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
        Call<ResponseFoto> call;
        if(tipo.equals("1")){
            call = casfService.subirLectura(body);
        }else {
            call = casfService.subirInconsistencia(body);
        }
        call.enqueue(new Callback<ResponseFoto>() {
            @Override
            public void onResponse(Call<ResponseFoto> call, Response<ResponseFoto> response) {
                if(response.isSuccessful()){
                    String uid = response.body().getNombreOriginal();
                    base.setEstado( uid, Constantes.IMAGEN, "1", Constantes.IMAGENES );
                    Log.e(TAG, "onResponse: " + response.body().getNombreOriginal() );
                }else {
                    Toast.makeText(MyApp.getContext(), "Hubo problemas para conectarse a internet", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseFoto> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, InicioCasf.class);
        startActivity(intent);
        finish();
    }
}
