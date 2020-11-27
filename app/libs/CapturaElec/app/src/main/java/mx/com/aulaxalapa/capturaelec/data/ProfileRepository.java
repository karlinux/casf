package mx.com.aulaxalapa.capturaelec.data;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.io.File;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.MyApp;
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

public class ProfileRepository {

    private String TAG = "ProfileRepository";
   MutableLiveData<String> fotoCredencial;
   CapturaService capturaService;
   CapturaClient capturaClient;
   Handler_sqlite base;
    public ProfileRepository() {
        capturaClient = capturaClient.getInstance();
        capturaService = capturaClient.getService();
        base = new Handler_sqlite(MyApp.getContext());

        if(fotoCredencial==null){
           fotoCredencial = new MutableLiveData<>();
        }
    }

    public void subirFoto( String fileFoto){
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

                    base.setEstado( uid, Constantes.IMAGEN, "0", Constantes.IMAGENES, "" );

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
