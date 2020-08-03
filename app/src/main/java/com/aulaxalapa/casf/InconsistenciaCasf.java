package com.aulaxalapa.casf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.SharedPreferencesManager;
import com.aulaxalapa.casf.data.Handler_sqlite;
import com.aulaxalapa.casf.R;

public class InconsistenciaCasf extends AppCompatActivity {

    private Button btnSiguiente, btnFinalizar;
    private TextView tvUsuario, tvDatos;
    private Handler_sqlite base;
    private String folioCont, guardado, _ID, selecInconsistencia;
    private Spinner spInconsistencia;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inconsistencia);
        base = new Handler_sqlite(this);

        tvDatos = findViewById(R.id.tvDatos);
        String id = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID);
        base.abrir();
        guardado = base.getGuardado();
        Cursor c = base.getDatos(id);
        c.moveToLast();
        folioCont = c.getString(1);
        tvDatos.setText(folioCont + " Folio: " + c.getString(9) +
                "\n"+ c.getString(2) +
                " " + c.getString(3) +
                " " + c.getString(4) +
                "\n" + c.getString(5) + " " + c.getString(6) +
                "\nMedidor: " + c.getString(12));
        base.getTablas("SELECT * FROM " + Constantes.CASF, "GPS");
        _ID = base.getId( guardado );
        base.cerrar();

        if(guardado.equals("0")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        btnSiguiente = findViewById(R.id.btnRegresar);
        btnFinalizar = findViewById(R.id.btnFinalizar);

        tvUsuario = findViewById(R.id.tvUsuario);
        String usuario = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NOMBRE);
        tvUsuario.setText(usuario);

        spInconsistencia = findViewById(R.id.spInconsistencia);
        spInconsistencia.setAdapter(ArrayAdapter.createFromResource(this, R.array.inconsistencias, R.layout.spinner_item));

        spInconsistencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selecInconsistencia = String.valueOf(spInconsistencia.getSelectedItem());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String [] arrayInconsistenia = selecInconsistencia.split("-");
                String inconsistencia = arrayInconsistenia[0];

                if(inconsistencia.equals("0")){
                    Toast.makeText(InconsistenciaCasf.this, "No puede seleccionar una inconsistencia en 0 si no existe alguna presione finalizar", Toast.LENGTH_SHORT).show();
                    return;
                }
                base.abrir();
                switch (guardado){
                    case "3":
                        base.setCampo( _ID, Constantes.IDINCONSISTENCIA1, inconsistencia, "4");
                        break;
                    case "5":
                        base.setCampo( _ID, Constantes.IDINCONSISTENCIA2, inconsistencia, "6");
                        break;
                    case "7":
                        base.setCampo( _ID, Constantes.IDINCONSISTENCIA3, inconsistencia, "8");
                        break;
                }
                base.cerrar();

                Intent intent = new Intent(getApplicationContext(), FotoInconsistenciaCasf.class);
                startActivity(intent);
                finish();

                Toast.makeText(InconsistenciaCasf.this, "Datos guardados", Toast.LENGTH_SHORT).show();
            }
        });

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                base.abrir();
                base.setGuardado(_ID, "0");
                base.setEstado(_ID);
                base.cerrar();
                Intent intent = new Intent(getApplicationContext(), BuscarCasf.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, FotoInconsistenciaCasf.class);
        startActivity(intent);
        finish();
    }
}
