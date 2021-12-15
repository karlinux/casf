package com.aulaxalapa.casf.data;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.SharedPreferencesManager;
import com.aulaxalapa.casf.retrofit.CasfClient;
import com.aulaxalapa.casf.retrofit.CasfService;
import com.aulaxalapa.casf.retrofit.request.RequestGps;
import com.aulaxalapa.casf.retrofit.request.RequestInsert;
import com.aulaxalapa.casf.retrofit.request.RequestUniverso;
import com.aulaxalapa.casf.retrofit.response.ResponseFoto;
import com.aulaxalapa.casf.retrofit.response.ResponseGps;
import com.aulaxalapa.casf.retrofit.response.ResponseInsert;
import com.aulaxalapa.casf.retrofit.response.ResponseUniverso;
import com.aulaxalapa.casf.common.MyApp;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private String TAG = "DataRepository";
    MutableLiveData<String> fotoCredencial;
    CasfService casfService;
    CasfClient casfClient;
    List<ResponseUniverso> universoLista;
    private boolean verdadero = false;

    Handler_sqlite base;
    public DataRepository() {
        casfClient = casfClient.getInstance();
        casfService = casfClient.getService();
        base = new Handler_sqlite(MyApp.getContext());

        if(fotoCredencial==null){
           fotoCredencial = new MutableLiveData<>();
        }
    }

    public void subirFoto( String fileFoto, int tipo){
        File file = new File( fileFoto);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
        Call<ResponseFoto> call;
        if(tipo==1){
            call = casfService.subirLectura(body);
        }else {
            call = casfService.subirInconsistencia(body);
        }
        call.enqueue(new Callback<ResponseFoto>() {
            @Override
            public void onResponse(Call<ResponseFoto> call, Response<ResponseFoto> response) {
                if(response.isSuccessful()){
                    //String uid = response.body().getNombreOriginal();
                    //base.setEstado( uid, Constantes.IMAGEN, "0", Constantes.IMAGENES );
                }else {
                    Toast.makeText(MyApp.getContext(), "Hubo problemas para conectarse a internet", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseFoto> call, Throwable t) {
                    Toast.makeText(MyApp.getContext(), "Error en la conexión"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void subirGps( String id, String latitud, String longitud ){

        RequestGps requestGps = new RequestGps( id, latitud, longitud );
        Call<ResponseGps> call = casfService.subirGps(requestGps);
        call.enqueue(new Callback<ResponseGps>() {
            @Override
            public void onResponse(Call<ResponseGps> call, Response<ResponseGps> response) {
                if(response.isSuccessful()){
                    String uid = response.body().getId();
                }else {
                    Toast.makeText(MyApp.getContext(), "Hubo problemas para conectarse a internet", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseGps> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión"+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void obtenerUniversos( String ruta , String sector, int delFolio, int alFolio ){

        RequestUniverso requestUniverso = new RequestUniverso( ruta, sector, delFolio, alFolio );
        Log.e(TAG, "obtenerUniversos: " +requestUniverso.getSector() );
        Call<List<ResponseUniverso>> call = casfService.obtenerUniversos(requestUniverso);
        call.enqueue(new Callback<List<ResponseUniverso>>() {
            @Override
            public void onResponse(Call<List<ResponseUniverso>> call, Response<List<ResponseUniverso>> response) {
                if( response.isSuccessful()){

                    universoLista = response.body();
                    for (int i=0; i < universoLista.size(); i++) {
                        base.abrir();
                        base.insertarRegistro(
                                universoLista.get(i).getFolioCont(),universoLista.get(i).getPaterno(),universoLista.get(i).getMaterno(),
                                universoLista.get(i).getNombre(),universoLista.get(i).getCalle(),universoLista.get(i).getNumExt(),
                                universoLista.get(i).getSector(), universoLista.get(i).getRuta(), universoLista.get(i).getFolioReparto(),
                                universoLista.get(i).getColonia(), universoLista.get(i).getIdMedidor(), universoLista.get(i).getMedidor(),
                                universoLista.get(i).getDiametro(), universoLista.get(i).getMarca(), universoLista.get(i).getLectAnt(),
                                universoLista.get(i).getAnio(), universoLista.get(i).getPeriodo()
                        );
                        base.cerrar();
                    }
                        SharedPreferencesManager.setSomeBooleanValue(Constantes.PREF_CARGA, false);
                        Log.e(TAG, "onResponse: SALIDA" );

                }else{
                    Toast.makeText(MyApp.getContext(), "Error al cargar universos" + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseUniverso>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Problemas de conexión intentelo de nuevo", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.toString() );
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
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
                    base.setEstado( id, Constantes.ESTATUS, "0", Constantes.CASF);

                }else {
                    Toast.makeText(MyApp.getContext(), "Hubo problemas para conectarse a internet", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onResponse: " +response.toString() );
                }
            }

            @Override
            public void onFailure(Call<ResponseInsert> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexión"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }

    public boolean carga(){
        return verdadero;
    }


}
