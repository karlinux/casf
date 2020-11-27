package mx.com.aulaxalapa.capturaelec;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.MyApp;
import mx.com.aulaxalapa.capturaelec.common.SharedPreferencesManager;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqlite;
import mx.com.aulaxalapa.capturaelec.retrofit.CapturaClient;
import mx.com.aulaxalapa.capturaelec.retrofit.CapturaService;
import mx.com.aulaxalapa.capturaelec.retrofit.request.RequestInsert;
import mx.com.aulaxalapa.capturaelec.retrofit.request.RequestRegistro;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseAuth;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseFoto;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseInsert;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Serviciocaptura extends Service {
    private final Handler_sqlite base = new Handler_sqlite(this);

    private String id, imagen, domicilio, colonia, casilla,
            seccion, ocupacion, curp,facebook, twitter, emision, vigencia, sexo, latitud, longitud, escolaridad, tipo,
            promotor, idcoordinador, idlider, idjefe, fecha;
    private String envio = "1";
    private String modifica = "0";
    private String TAG = "Servicio";
    int tiempo = 15;
    CapturaService capturaService;
    CapturaClient capturaClient;
    private String nombre, apaterno, amaterno,
            telefono, correo, clave;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        capturaClient = capturaClient.getInstance();
        capturaService = capturaClient.getService();
        promotor = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_USUARIO);
        ejecutar();
    }

    private void enviarImagenes( ) {
        imagen = Environment.getExternalStorageDirectory() + Constantes.CARPETA;
        Cursor c = base.getImagenes(envio);
        if(c.getCount()>0){
            while(c.moveToNext()){
                subirFoto(  imagen + c.getString(0) );
            }
        }
    }
    private void enviar( ) {
        base.abrir();
        Cursor c = base.getEnvio(envio);
        if(c.getCount()>0){
            while(c.moveToNext()){
                id =  c.getString(0);
                nombre = c.getString(1);
                apaterno = c.getString(2);
                amaterno = c.getString(3);
                domicilio = c.getString(4);
                colonia = c.getString(5);
                seccion =  c.getString(6);
                clave =  c.getString(7);
                ocupacion =   c.getString(8);
                curp =  c.getString(9);
                telefono =  c.getString(10);
                correo =  c.getString(11);
                facebook =  c.getString(12);
                twitter =  c.getString(13);
                emision =  c.getString(14);
                vigencia =  c.getString(15);
                sexo =  c.getString(16);
                casilla =  c.getString(17);
                latitud =  c.getString(18);
                longitud =  c.getString(19);
                escolaridad =  c.getString(20);
                tipo =  c.getString(21);
                promotor =  c.getString(22);
                idcoordinador =  c.getString(23);
                idlider =  c.getString(24);
                idjefe =  c.getString(25);
                fecha =  c.getString(26);

                RequestInsert requestInsert = new RequestInsert (
                        id,
                        nombre,
                        apaterno,
                        amaterno,
                        domicilio,
                        colonia,
                        seccion,
                        clave,
                        ocupacion,
                        curp,
                        telefono,
                        correo,
                        facebook,
                        twitter,
                        emision,
                        vigencia,
                        sexo,
                        casilla,
                        latitud,
                        longitud,
                        escolaridad,
                        tipo,
                        promotor,
                        idcoordinador,
                        idlider,
                        idjefe,
                        fecha
                );
                Call<ResponseInsert> call = capturaService.subirDatos(requestInsert);
                call.enqueue(new Callback<ResponseInsert>() {
                    @Override
                    public void onResponse(Call<ResponseInsert> call, Response<ResponseInsert> response) {
                        if(response.isSuccessful()){

                            String uid = response.body().getUid();
                            String existe = response.body().getIdcapturista();

                            Log.e(TAG, "onResponse: " + uid +" "+ existe );
                            base.setEstado( uid, "_ID", modifica, Constantes.INE, response.body().getIdcapturista());

                        }else {
                            Log.e(TAG, "onResponse: " + response.body().getUid() );
                            Toast.makeText(MyApp.getContext(), "Hubo problemas para conectarse a internet", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseInsert> call, Throwable t) {
                        Toast.makeText(MyApp.getContext(), "Error en la conexión "+ t.toString(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onFailure: " + t.getMessage() );
                    }
                });
            }
        }

        base.cerrar();
    }

    public void subirFoto( String fileFoto ){
        File file = new File( fileFoto);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
        Call<ResponseFoto> call = capturaService.subirFoto(body);
        call.enqueue(new Callback<ResponseFoto>() {
            @Override
            public void onResponse(Call<ResponseFoto> call, Response<ResponseFoto> response) {
                if(response.isSuccessful()){

                    Log.e(TAG, "onResponse: " + response.body().getNombreOriginal() );
                    String uid = response.body().getNombreOriginal();

                    base.setEstado( uid, Constantes.IMAGEN, modifica, Constantes.IMAGENES );

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

    public void ejecutar(){
        times time = new times();
        time.execute();
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
            enviar();
            enviarImagenes();
        }
    }

}
