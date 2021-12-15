package com.aulaxalapa.casf;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.MyApp;
import com.aulaxalapa.casf.data.Handler_sqlite;
import com.aulaxalapa.casf.retrofit.CasfClient;
import com.aulaxalapa.casf.retrofit.CasfService;
import com.aulaxalapa.casf.retrofit.response.ResponseFoto;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicioCasf extends Service {
    private final Handler_sqlite base = new Handler_sqlite(this);

    private String textin, imeistring, imagen, imagenes, foto, foto2, foto3, foto4;
    private int sinc;
    private String[] fecha;
    private String envio = "1";
    private String modifica = "0";
    private String TAG;
    CasfService casfService;
    CasfClient casfClient;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        imagen =  getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        casfClient = casfClient.getInstance();
        casfService = casfClient.getService();
        ejecutar();
    }

    public void ejecutar(){
        times time = new times();
        time.execute();
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

    public void hilo(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //TODO Hacer un select donde para sincronizar tenga que sincronizar las que sean 60 segundos despues de guardadas
    public class times extends AsyncTask<Void, Integer, Boolean> {
        int tiempo = 5;
        @Override
        protected Boolean doInBackground(Void... voids) {
            for(int i = 1; i <= tiempo; i++)
            {
                hilo();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            ejecutar();
            if(conectadoWifi()){
                enviarImagenes();
            }
        }
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

}
