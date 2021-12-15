package com.aulaxalapa.casf.retrofit;

import com.aulaxalapa.casf.retrofit.request.RequestGps;
import com.aulaxalapa.casf.retrofit.request.RequestInsert;
import com.aulaxalapa.casf.retrofit.request.RequestRuta;
import com.aulaxalapa.casf.retrofit.response.ResponseRuta;
import com.aulaxalapa.casf.retrofit.response.ResponseSector;
import com.aulaxalapa.casf.retrofit.response.ResponseAuth;
import com.aulaxalapa.casf.retrofit.response.ResponseFoto;
import com.aulaxalapa.casf.retrofit.response.ResponseGps;
import com.aulaxalapa.casf.retrofit.response.ResponseInsert;
import com.aulaxalapa.casf.retrofit.response.ResponseUniverso;
import com.aulaxalapa.casf.retrofit.request.RequestLogin;
import com.aulaxalapa.casf.retrofit.request.RequestUniverso;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CasfService {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST("production/accesar/loguin.php")
    Call<ResponseAuth> doLogin(@Body RequestLogin requestLogin);

    //@POST("production/accesar/universos.php")
    @POST("production/accesar/universos.php")
    Call<List<ResponseUniverso>> obtenerUniversos(@Body RequestUniverso requestUniverso);

    //@POST("production/accesar/universos.php")
    @POST("production/accesar/rutas.php")
    Call<List<ResponseRuta>> obtenerRutas(@Body RequestRuta requestRuta);

    @GET("production/accesar/sectores.php")
    Call<List<ResponseSector>> obtenerSector( );

    // Gps
    @POST("casf/gps.php")
    Call<ResponseGps> subirGps(@Body RequestGps requestGps);

    // Petición de Insertar
    @POST("production/apk/in.php")
    Call<ResponseInsert> subirDatos(@Body RequestInsert requestInsert);

    //
    @Multipart
    @POST("production/images/lecturas.php")
    Call<ResponseFoto> subirLectura(@Part MultipartBody.Part file);

    @Multipart
    @POST("production/images/inconsistencia.php")
    Call<ResponseFoto> subirInconsistencia(@Part MultipartBody.Part file);


}
