package mx.com.aulaxalapa.capturaelec.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CapturaClient {

    private static CapturaClient instance = null;
    private CapturaService capturaService;
    private Retrofit retrofit;

    public CapturaClient() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_RUTA_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        capturaService = retrofit.create( CapturaService.class );
    }

    // Patrón Singleton
    public static CapturaClient getInstance(){

        if( instance == null){
            instance = new CapturaClient();
        }

        return instance;
    }

    public CapturaService getService(){
        return capturaService;
    }

}
