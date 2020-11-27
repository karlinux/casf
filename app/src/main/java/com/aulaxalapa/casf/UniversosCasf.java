package com.aulaxalapa.casf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.MyApp;
import com.aulaxalapa.casf.common.SharedPreferencesManager;
import com.aulaxalapa.casf.data.Handler_sqlite;
import com.aulaxalapa.casf.retrofit.CasfClient;
import com.aulaxalapa.casf.retrofit.CasfService;
import com.aulaxalapa.casf.retrofit.request.RequestRuta;
import com.aulaxalapa.casf.retrofit.response.ResponseRuta;
import com.aulaxalapa.casf.retrofit.response.ResponseSector;
import com.aulaxalapa.casf.retrofit.request.RequestUniverso;
import com.aulaxalapa.casf.retrofit.response.ResponseUniverso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UniversosCasf extends AppCompatActivity {

    private Spinner spSector, spRuta;
    private String selecSector, selecRuta, dia;
    private TextView tvUsuario;
    CasfService casfService;
    CasfClient casfClient;
    List<ResponseUniverso> universoLista;
    Handler_sqlite base;
    private Button btnIniciar, btnRegresar;
    private ProgressBar spinner;
    private String TAG = "UniversosCasf";
    private boolean verdadero = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universos);
        base = new Handler_sqlite(this);
        casfClient = casfClient.getInstance();
        casfService = casfClient.getService();

        spinner = findViewById(R.id.progreso);

        spSector = findViewById(R.id.spSector);
        spRuta = findViewById(R.id.spInconsistencia);
        tvUsuario = findViewById(R.id.tvUsuario);

        spinner.setVisibility(View.GONE);
        String usuario = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NOMBRE);
        tvUsuario.setText(usuario);

        obtenerSectores( );

        btnIniciar = findViewById(R.id.btnRegresar);
        btnRegresar = findViewById(R.id.btnFinalizar);

        Date fechaActual = new Date();
        SimpleDateFormat d = new SimpleDateFormat("dd");
        dia = d.format(fechaActual);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            spinner.setVisibility(View.VISIBLE);

            btnIniciar.setVisibility(View.GONE);
            btnRegresar.setVisibility(View.GONE);

            base.abrir();
            base.eliminarTabla();
            base.crearTabla();
            base.cerrar();
            goUniversos( selecRuta, selecSector );

            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BuscarCasf.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void goUniversos( String ruta, String sector ){
        RequestUniverso requestUniverso = new RequestUniverso( ruta, sector );
        Call<List<ResponseUniverso>> call = casfService.obtenerUniversos(requestUniverso);
        call.enqueue(new Callback<List<ResponseUniverso>>() {
            @Override
            public void onResponse(Call<List<ResponseUniverso>> call, Response<List<ResponseUniverso>> response) {

                if( response.isSuccessful()){
                    universoLista = response.body();
                    if(universoLista != null) {
                        for (int i = 0; i < universoLista.size(); i++) {
                            base.abrir();
                            base.insertarRegistro(
                                    universoLista.get(i).getFolioCont(), universoLista.get(i).getPaterno(), universoLista.get(i).getMaterno(),
                                    universoLista.get(i).getNombre(), universoLista.get(i).getCalle(), universoLista.get(i).getNumExt(),
                                    universoLista.get(i).getSector(), universoLista.get(i).getRuta(), universoLista.get(i).getFolioReparto(),
                                    universoLista.get(i).getColonia(), universoLista.get(i).getIdMedidor(), universoLista.get(i).getMedidor(),
                                    universoLista.get(i).getDiametro(), universoLista.get(i).getMarca(), universoLista.get(i).getLectAnt(),
                                    universoLista.get(i).getAnio(), universoLista.get(i).getPeriodo()
                            );
                            spinner.setVisibility(View.GONE);
                            btnIniciar.setVisibility(View.VISIBLE);
                            btnRegresar.setVisibility(View.VISIBLE);
                            base.cerrar();
                        }
                        SharedPreferencesManager.setSomeBooleanValue(Constantes.PREF_CARGA, false);
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_DIA, dia);
                    }else{
                        Toast.makeText(UniversosCasf.this, "No existen datos con esa configuración", Toast.LENGTH_SHORT).show();
                        spinner.setVisibility(View.GONE);
                    }
                }else{
                    Toast.makeText(MyApp.getContext(), "Error al cargar universos" + response.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ResponseUniverso>> call, Throwable t) {
                Toast.makeText(UniversosCasf.this, "No se pudieron descargar los datos del servidor", Toast.LENGTH_SHORT).show();
                spinner.setVisibility(View.GONE);
                btnIniciar.setVisibility(View.VISIBLE);
                btnRegresar.setVisibility(View.VISIBLE);
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }
    public void obtenerSectores(  ){

        Call<List<ResponseSector>> call = casfService.obtenerSector(  );
        call.enqueue(new Callback<List<ResponseSector>>() {
            @Override
            public void onResponse(Call<List<ResponseSector>> call, Response<List<ResponseSector>> response) {
                if( response.isSuccessful()){

                    adaptadorSector( response.body() );
                    //Log.e(TAG, "onResponse: " + response.body() );


                }else{
                    Toast.makeText(MyApp.getContext(), "Error al cargar universos" + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseSector>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Problemas de conexión intentelo de nuevo", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.toString() );
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }
    public void adaptadorSector( List<ResponseSector> sectorList ){

        List<String> sectorLista;

        sectorLista = new ArrayList<String>();
            sectorLista.add( "SECTOR" );
        for (int i=0; i < sectorList.size(); i++) {
            sectorLista.add(sectorList.get(i).getSector());
        }

        //MySpinnerAdapter myadapter = new MySpinnerAdapter(MyApp.getContext(),  R.layout.spinner_item , sectorLista);
        ArrayAdapter<String> myadapter = new ArrayAdapter<>(MyApp.getContext(), R.layout.spinner_item, sectorLista);
        spSector.setAdapter(myadapter);

        spSector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selecSector = String.valueOf(spSector.getSelectedItem());
                obtenerRutas( selecSector );
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void obtenerRutas( String sector ){

        RequestRuta requestRuta = new RequestRuta( sector );
        Call<List<ResponseRuta>> call = casfService.obtenerRutas(requestRuta);
        call.enqueue(new Callback<List<ResponseRuta>>() {
            @Override
            public void onResponse(Call<List<ResponseRuta>> call, Response<List<ResponseRuta>> response) {
                if( response.isSuccessful()){

                    adaptadorRuta( response.body() );

                }else{
                    Toast.makeText(MyApp.getContext(), "Error al cargar universos" + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseRuta>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Problemas de conexión intentelo de nuevo", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onFailure: " + t.toString() );
                Log.e(TAG, "onFailure: " + t.getMessage() );
            }
        });
    }

    public void adaptadorRuta( List<ResponseRuta> sectorList ){

        List<String> sectorLista;
        sectorLista = new ArrayList<String>();
        sectorLista.add( "RUTA" );
        for (int i=0; i < sectorList.size(); i++) {
            sectorLista.add(sectorList.get(i).getRuta());
        }

        //MySpinnerAdapter myadapter = new MySpinnerAdapter(MyApp.getContext(),  R.layout.spinner_item , sectorLista);
        ArrayAdapter<String> myadapter = new ArrayAdapter<>(MyApp.getContext(), R.layout.spinner_item, sectorLista);
        spRuta.setAdapter(myadapter);

        spRuta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selecRuta = String.valueOf(spRuta.getSelectedItem());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
