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
import android.database.Cursor;
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
import androidx.lifecycle.ViewModelProviders;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.SharedPreferencesManager;
import mx.com.aulaxalapa.capturaelec.data.FotoViewModel;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqlite;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * recognizes text.
 */
public class RegistroCaptura extends AppCompatActivity {

    // Use a compound button so either checkbox or switch widgets work.
    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView textValue;
    private String id, paterno, materno, nombre, municipio, idUsuario;
    private EditText etPassword, etConfirmar, etUsuario, etClave;
    Handler_sqlite base;
    FotoViewModel fotoViewModel;
    private static final String TAG = "Registro Captura";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        base = new Handler_sqlite(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fotoViewModel = ViewModelProviders.of(this).get(FotoViewModel.class);

        etUsuario = (EditText) findViewById(R.id.etUsuario);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etConfirmar = (EditText) findViewById(R.id.etConfirmar);
        etClave = (EditText) findViewById(R.id.etClave);
        Button btnGuardar = (Button) findViewById(R.id.btnGuardar);

        base.abrir();
        Cursor c;
        id = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID);
        Log.e(TAG, "onCreate: " + id );
        c = base.getDatos( id );
        c.moveToFirst();
        etUsuario.setText(c.getString(12));
        etClave.setText(c.getString(8));
        paterno = c.getString(2);
        materno = c.getString(3);
        nombre = c.getString(1);
        municipio = c.getString(6);

        idUsuario = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_USUARIO);
        etUsuario.setFocusable(false);
        etClave.setFocusable(false);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String error = "";

                if (etUsuario.getText().toString().equals("")) {
                    error = "INGRESE EL CORREO";
                    etUsuario.setError(error);
                } else if (!etPassword.getText().toString().equals(etConfirmar.getText().toString())) {
                    error = "EL PASSWORD NO COINCIDE EN LAS 2 CASILLAS";
                    etConfirmar.setError(error);
                } else if (etPassword.getText().toString().equals("")) {
                    error = "INGRESE LA CLAVE DE INE";
                    etPassword.setError(error);
                }

                if(error.equals("")) {
                    base.abrir();
                    base.insertarUsuario (
                            etUsuario.getText().toString(),
                            etPassword.getText().toString(),
                            etClave.getText().toString()
                    );

                    Intent intent = new Intent(getApplicationContext(), InicioCaptura.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, InicioCaptura.class);
        startActivity(intent);
        finish();
    }
}
