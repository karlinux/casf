package mx.com.aulaxalapa.capturaelec.retrofit;

import java.util.List;

import mx.com.aulaxalapa.capturaelec.retrofit.request.RequestConfiguracion;
import mx.com.aulaxalapa.capturaelec.retrofit.request.RequestInsert;
import mx.com.aulaxalapa.capturaelec.retrofit.request.RequestLogin;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseAuth;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseConfiguracion;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseFoto;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseInsert;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseUniverso;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CapturaService {

    // Petición de login
    @POST("api/log.php")
    Call<ResponseAuth> doLogin(@Body RequestLogin requestLogin);

    // Petición de login
    @POST("api/insertar.php")
    Call<ResponseInsert> subirDatos(@Body RequestInsert requestInsert);

    //
    @Multipart
    @POST("api/uploadFile.php")
    Call<ResponseFoto> subirFoto(@Part MultipartBody.Part file);

    //@POST("api/ines.php")
    @GET("api/ines.php")
    Call<List<ResponseUniverso>> obtenerUniversos( );

    @POST("api/configuracion.php")
    Call<ResponseConfiguracion> configuracion(@Body RequestConfiguracion requestConfiguracion);



}
