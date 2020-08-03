package com.aulaxalapa.casf;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

import com.aulaxalapa.casf.data.Handler_sqlite;

public class ServicioCasf extends Service {
    private final Handler_sqlite base = new Handler_sqlite(this);

    private String textin, imeistring, imagen, imagenes, foto, foto2, foto3, foto4;
    private int sinc;
    private String[] fecha;
    private String envio = "1";
    private String modifica = "0";
    private String imei;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        imagen =  getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        imei = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
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
        }

    }
}
