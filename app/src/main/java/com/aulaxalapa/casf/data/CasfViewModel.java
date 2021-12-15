package com.aulaxalapa.casf.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class CasfViewModel extends AndroidViewModel {

    DataRepository dataRepository;
    //public LiveData<String> photoProfile;
    private boolean verdadero;

    public CasfViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository();
    }

    public void subirFoto( String photo, int tipo ){

        dataRepository.subirFoto( photo, tipo );
    }

    public void subirGps( String id, String latitud, String longitud ){
        dataRepository.subirGps( id, latitud, longitud);
    }

    public void obtenerUniversos( String ruta, String sector, int delFolio, int alFolio ){
        dataRepository.obtenerUniversos( ruta, sector, delFolio, alFolio );
    }

    public boolean carga(){
        return dataRepository.carga();
    }

    public void subirDatos(String id, String folioCont, String lectura, String latitud, String longitud, String fotoMedidor,
                           String fotoInconsistencia1, String fotoInconsistencia2, String fotoInconsistencia3,  String idInconsistencia1,
                           String idInconsistencia2,  String idInconsistencia3, String fecha, String usuario, String lectAnt,
                           String periodo, String anio ){
        dataRepository.subirDatos( id, folioCont, lectura, latitud, longitud, fotoMedidor, fotoInconsistencia1,
                fotoInconsistencia2, fotoInconsistencia3, idInconsistencia1, idInconsistencia2, idInconsistencia3, fecha, usuario, lectAnt,
                periodo, anio  );
    }
}
