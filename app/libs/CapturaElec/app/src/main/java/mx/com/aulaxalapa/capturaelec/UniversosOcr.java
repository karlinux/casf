package mx.com.aulaxalapa.capturaelec;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.util.ByteArrayBuffer;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.MyApp;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqlite;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqliteU;
import mx.com.aulaxalapa.capturaelec.retrofit.CapturaClient;
import mx.com.aulaxalapa.capturaelec.retrofit.CapturaService;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseUniverso;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UniversosOcr extends AppCompatActivity {

    private String dia;
    private TextView tvRegistros;
    CapturaService capturaService;
    CapturaClient capturaClient;
    List<ResponseUniverso> universoLista;
    Handler_sqlite base;
    Handler_sqliteU baseU;

    private int registros;
    private Button btnRegresar;
    private ProgressBar spinner;
    private String TAG = "UniversosCasf";
    private boolean verdadero = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universos);
        base = new Handler_sqlite(this);
        baseU = new Handler_sqliteU(this);

        capturaClient = capturaClient.getInstance();
        capturaService = capturaClient.getService();

        spinner = findViewById(R.id.progreso);
        tvRegistros = findViewById(R.id.tvRegistros);

        spinner.setVisibility(View.VISIBLE);

        btnRegresar = findViewById(R.id.btnFinalizar);

        Date fechaActual = new Date();
        SimpleDateFormat d = new SimpleDateFormat("dd");
        dia = d.format(fechaActual);

            /*
            base.abrir();
            registros = base.getRegistros("SELECT * FROM "+ Constantes.UNIVERSOS);
            base.cerrar();

            if(registros > 28300){
                tvRegistros.setText(String.valueOf(registros) + " Registros");
            }else{
                btnRegresar.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
                base.abrir();
                base.eliminarTabla();
                base.crearTabla();
                base.cerrar();
                goUniversos( );
            }
             */

            UploaderSu nuevaTarea = new UploaderSu();
            nuevaTarea.execute();

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InicioCaptura.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void goUniversos( ){
        Call<List<ResponseUniverso>> call = capturaService.obtenerUniversos();
        call.enqueue(new Callback<List<ResponseUniverso>>() {
            @Override
            public void onResponse(Call<List<ResponseUniverso>> call, Response<List<ResponseUniverso>> response) {

                if( response.isSuccessful()){
                    universoLista = response.body();
                    Log.e(TAG, "onResponse: " + universoLista.size() );
                    if(universoLista != null) {
                        for (int i = 0; i < universoLista.size(); i++) {
                            base.abrir();
                            // Log.e(TAG, "onResponse: " + i + " | " + universoLista.get(i).getClaveElectoral() );
                            base.insertarRegistro (
                                    universoLista.get(i).getNombre(),
                                    universoLista.get(i).getPaterno(),
                                    universoLista.get(i).getMaterno(),
                                    universoLista.get(i).getSeccion(),
                                    universoLista.get(i).getClaveElectoral()
                            );
                            base.cerrar();
                        }
                            btnRegresar.setVisibility(View.VISIBLE);
                            spinner.setVisibility(View.GONE);
                        base.abrir();

                        registros = base.getRegistros("SELECT _ID FROM "+ Constantes.UNIVERSOS);
                        base.cerrar();
                        tvRegistros.setText(String.valueOf(registros) + " Registros");
                        Log.e(TAG, "onResponse: " + registros );
                    }else{
                        Toast.makeText(getApplicationContext(), "No existen activity_datos con esa configuración", Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.GONE);
                    }
                }else{
                    Toast.makeText(MyApp.getContext(), "Error al cargar universos" + response.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ResponseUniverso>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No se pudieron descargar los activity_datos del servidor", Toast.LENGTH_SHORT).show();
                spinner.setVisibility(View.GONE);
                btnRegresar.setVisibility(View.VISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }

    class UploaderSu extends AsyncTask<String, Void, Void> {

        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        protected Void doInBackground(String... params) {
            File foto = new File(Environment.DIRECTORY_DOWNLOADS);

            if(!foto.exists()){
                foto.mkdirs();
            }
            try {
                String DownloadUrl= Constantes.API_RUTA_BASE + "encuesta/universo" ;
                String fileName = "universo";
                URL url = new URL(DownloadUrl);
                String path = getApplicationContext().getPackageName();
                File file= new File("/data/data/"+ path + "/databases/", fileName);
                long startTime = System.currentTimeMillis();
                /* abro una conexion a URL. */
                URLConnection ucon = url.openConnection();
                /*
                 * Defino InputStreams para leer desde la URLConnection.
                 */
                InputStream is = ucon.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                /*
                 * leo bytes para el Buffer hasta que no queden mas para leer(-1).
                 */
                ByteArrayBuffer baf = new ByteArrayBuffer(5000);
                int current = 0;
                while ((current = bis.read()) != -1) {
                    baf.append((byte) current);
                }

                FileOutputStream fos = new FileOutputStream(file);
                fos.write(baf.toByteArray());
                fos.flush();
                fos.close();
                Log.e("Descarga", "descarga lista en"  + ((System.currentTimeMillis() - startTime) / 1000) + " segundos");
                if(registros > 200){
                    Log.e(TAG, "onCreate: " + registros );
                } else {

                }
            } catch (IOException e) {
                Log.e("Descarga", "descarga fallida " + e + "");
            }
            return null;
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

        protected Boolean conectadoRedMovil() {
            ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                if (info != null) {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
            return false;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (conectadoRedMovil() || conectadoWifi()) {

            } else {

                Toast.makeText(getApplicationContext(), "NO ESTA CONECTADO A INTERNET", Toast.LENGTH_LONG).show();
            }
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            baseU.abrir();
            int registros = baseU.getRegistros("SELECT * FROM "+ Constantes.UNIVERSOS);
            tvRegistros.setText(String.valueOf(registros) + " Registros");
            baseU.cerrar();
            spinner.setVisibility(View.GONE);
        }
    }
}
