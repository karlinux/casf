package mx.com.aulaxalapa.capturaelec;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

import mx.com.aulaxalapa.capturaelec.data.Handler_sqlite;
import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.SharedPreferencesManager;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqliteU;
import mx.com.aulaxalapa.capturaelec.retrofit.CapturaClient;
import mx.com.aulaxalapa.capturaelec.retrofit.CapturaService;
import mx.com.aulaxalapa.capturaelec.retrofit.request.RequestConfiguracion;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseConfiguracion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioCaptura extends AppCompatActivity {

    private Handler_sqlite base;
    private Handler_sqliteU baseU;
    private ListView lista;
    private Button btnGuardar;
    private Adaptador adaptador;
    private TextView tvNombre;
    private int lastitem = 0;
    private static final int WRITE_PERMISSION = 11;
    private static final int CAMERA_PERMISSION = 8;
    private String TAG = "Capturar";
    CapturaClient capturaClient;
    CapturaService capturaService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        base = new Handler_sqlite(this);
        baseU = new Handler_sqliteU(this);
        capturaClient = new CapturaClient();
        capturaService = new CapturaClient().getService();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
                }
            }else{
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                    } else {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
                    }
                }

            }
        }
        Intent msgIntent = new Intent(getApplicationContext(), Serviciocaptura.class);
        startService(msgIntent);
        lista = findViewById(R.id.lista);
        tvNombre = findViewById(R.id.tvNombre);
        tvNombre.setText(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NOMBRE));
        lista.setSelection(lastitem);
        String guardado;
        base.abrir();
        guardado = base.getGuardado();
        base.cerrar();
        final Intent intent = new Intent(this, BuscarOcr.class);

        if(Integer.parseInt( guardado ) > 0 ){
            startActivity(intent);
            finish();
        }

        baseU.abrir();
        int registros = baseU.getRegistros("SELECT * FROM "+ Constantes.UNIVERSOS);
        baseU.cerrar();

        if(registros > 200){
            Log.e(TAG, "onCreate: " + registros );
        } else {
            Intent intents = new Intent(this, UniversosOcr.class);
            startActivity(intents);
            finish();
        }

        btnGuardar = (Button) findViewById(R.id.btnGuardar);
        String idUser=SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_USUARIO);
        adaptador = new Adaptador(this, GetArrayItems());
        lista.setAdapter(adaptador);
        lista.setSelection(lastitem);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String ide = String.valueOf(GetArrayItems().get(position).getId());
                //SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID, ide);
                //Intent intent = new Intent(getApplicationContext(), Editarcaptura.class);
                //startActivity(intent);
                //finish();
            }
        });

        btnGuardar.setText("Capturar");

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        goToConfig();
    }

    private ArrayList<Entidad> GetArrayItems(){
        ArrayList<Entidad> listItems = new ArrayList<>();
        base.abrir();
        Cursor c= base.getDatos();
        listItems.clear();
        lastitem = c.getCount();
        if(c.getCount() > 0) {
            listItems.add(new Entidad("", "",""));//id
            while (c.moveToNext()) {
                listItems.add(new Entidad(c.getString(0), "FECHA: " + c.getString(7),
                        "\nCLAVE: " + c.getString(4) +
                                "\nNOMBRE: " + c.getString(1) +
                                "\nPATERNO: " + c.getString(2) +
                                "\nMATERNO: " + c.getString(3)
                ));
            }
        }
        c.close();
        base.close();
        return listItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CORREO, "");
            Intent intents = new Intent(this, Logincaptura.class);
            startActivity(intents);
            finish();

            return true;
        }else if (id == R.id.uni) {
            Intent intents = new Intent(this, UniversosOcr.class);
            startActivity(intents);
            finish();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if( requestCode == WRITE_PERMISSION ||
                requestCode == CAMERA_PERMISSION){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent inteF = new Intent(this, InicioCaptura.class);
                startActivity(inteF);
                finish();
            }else{
                finish();
            }

        }
    }

    public void goToConfig(){
        String idUs = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_USUARIO);
        RequestConfiguracion requestConfiguracion = new RequestConfiguracion( idUs );
        Call<ResponseConfiguracion> call = capturaService.configuracion( requestConfiguracion );

        call.enqueue(new Callback<ResponseConfiguracion>() {
            @Override
            public void onResponse(Call<ResponseConfiguracion> call, Response<ResponseConfiguracion> response) {
                if(response.isSuccessful()){
                    if(!response.body().getDeshabilitado().equals("ACTIVO") || response.body().getFinalizado().equals("1") ){
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CORREO, "");
                        Intent intents = new Intent(getApplicationContext(), Logincaptura.class);
                        startActivity(intents);
                        finish();
                    }
                    if(Constantes.VERSION != response.body().getVersione()){
                        Intent intents = new Intent(getApplicationContext(), Versioncaptura.class);
                        startActivity(intents);
                        finish();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseConfiguracion> call, Throwable t) {
            }
        });
    }

}
