package com.aulaxalapa.casf.retrofit;

import android.util.Log;

import com.aulaxalapa.casf.common.Constantes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CasfClient {

    private static CasfClient instance = null;
    private CasfService casfService;
    private Retrofit retrofit;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    public CasfClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_RUTA_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create( gson ))
                .build();
        casfService = retrofit.create( CasfService.class );
    }

    // Patrón Singleton
    public static CasfClient getInstance(){

        if( instance == null){
            instance = new CasfClient();
        }

        return instance;
    }

    public CasfService getService(){
        return casfService;
    }

}
