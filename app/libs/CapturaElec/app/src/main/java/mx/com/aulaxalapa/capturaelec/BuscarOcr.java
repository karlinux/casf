/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mx.com.aulaxalapa.capturaelec;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.common.api.CommonStatusCodes;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.SharedPreferencesManager;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqlite;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqliteU;
import mx.com.aulaxalapa.capturaelec.retrofit.CapturaClient;
import mx.com.aulaxalapa.capturaelec.retrofit.CapturaService;
import mx.com.aulaxalapa.capturaelec.retrofit.request.RequestConfiguracion;
import mx.com.aulaxalapa.capturaelec.retrofit.request.RequestLogin;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseAuth;
import mx.com.aulaxalapa.capturaelec.retrofit.response.ResponseConfiguracion;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * recognizes text.
 */
public class BuscarOcr extends AppCompatActivity implements View.OnClickListener {

    // Use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private EditText etClave;
    private Button btnBuscar;
    private TextView textValue;

    Handler_sqliteU baseU;
    Handler_sqlite base;

    private static final int RC_OCR_CAPTURE = 9003;
    private static String TAG = "Buscar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TAG = getClass().getName();
        base = new Handler_sqlite(this);
        baseU = new Handler_sqliteU(this);
        etClave =  findViewById(R.id.etClave);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        String guardado;
        base.abrir();
        base.getTablas("Select * from INE limit 10", "BuscarOcr");
        // base.borrar("2", Constantes.INE);
        base.cerrar();
        base.abrir();
        guardado = base.getGuardado();
        base.cerrar();


        if(Integer.parseInt( guardado ) > 0 ){
            Intent intent = new Intent(this, Fotocapturaanterior.class);
            startActivity(intent);
            finish();
        }

        if(Integer.parseInt( guardado ) > 1 ){
            Intent intent = new Intent(this, GpsOcr.class);
            startActivity(intent);
            finish();
        }

        findViewById(R.id.read_text).setOnClickListener(this);


        Log.e(TAG, "onCreate: ");
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + etClave.getText().toString().trim());
            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CLAVE, etClave.getText().toString().trim());
            Intent intent = new Intent(getApplicationContext(), Datos.class);
            startActivity(intent);
            finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_text) {
            // launch Ocr capture activity.
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_OCR_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
/////////////////////////    CLAVE

                    String[] claveCompleto = data.getStringExtra(OcrCaptureActivity.TextBlockObject).split("CLAVE");
                    String textoClave = "";
                    if(claveCompleto.length>1) {
                        textoClave= claveCompleto[1];
                    }
                    claveCompleto = textoClave.split("ELECTOR");


                    if(claveCompleto.length>1){
                        textoClave = claveCompleto[1];
                        String clave = claveCompleto[0].trim();
                        textoClave = textoClave.replaceAll("\n","---");
                        claveCompleto = textoClave.split("---");
                        if(claveCompleto.length>0){
                            if(!claveCompleto[0].equals("")) {
                                etClave.setText(claveCompleto[0].trim());
                            }else{
                                etClave.setText(clave);
                            }
                        }
                    }

                } else {
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                textValue.setText(String.format(getString(R.string.ocr_error),  CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, InicioCaptura.class);
        startActivity(intent);
        finish();
    }
}
