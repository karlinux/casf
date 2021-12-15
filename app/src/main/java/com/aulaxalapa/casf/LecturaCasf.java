package com.aulaxalapa.casf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.SharedPreferencesManager;
import com.aulaxalapa.casf.data.Handler_sqlite;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class LecturaCasf extends AppCompatActivity {

    private Button btnRegistrar;
    private EditText etLectura1, etLectura2;
    private TextView tvUsuario, tvDatos, tvlectAnt;
    private String folioCont, guardado, _ID, periodo, anio, lecAnt;
    String TAG = "LecturaCasf";
    Handler_sqlite base;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura);
        base = new Handler_sqlite(this);

        tvUsuario = findViewById(R.id.tvUsuario);
        tvlectAnt = findViewById(R.id.tvlectAnt);
        etLectura1 = findViewById(R.id.etLectura1);
        etLectura2 = findViewById(R.id.etLectura2);
        btnRegistrar = findViewById(R.id.btnCargar);
        tvDatos = findViewById(R.id.tvDatos);

        String usuario = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NOMBRE);
        String id = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID);
        base.abrir();
        Cursor c = base.getDatos(id);
        c.moveToLast();
        periodo = c.getString(16);
        anio = c.getString(17);
        folioCont = c.getString(1);
        lecAnt =  c.getString(15);
        tvlectAnt.setText(lecAnt);
        tvDatos.setText(folioCont + " Folio: " + c.getString(9) +
                        "\n"+ c.getString(2) +
                        " " + c.getString(3) +
                        " " + c.getString(4) +
                        "\n" + c.getString(5) + " " + c.getString(6) +
                        "\nMedidor: " + c.getString(12));
        base.cerrar();

        String ide = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_IDE);
        if(ide.equals("null")) {
            Log.e(TAG, "onCreate: " + ide);
            guardado = "0";
        }else{
            _ID = ide;
            guardado = "1";
        }

        tvUsuario.setText(usuario);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lec1 = etLectura1.getText().toString();
                String lec2 = etLectura2.getText().toString();
                String error = "";
                String menor = "";

                if (lec1.equals("")){
                    lec1 = "0";
                }
                if (lec2.equals("")){
                    lec2 = "0";
                }
                if(Integer.parseInt(lecAnt)>Integer.parseInt(lec1)){
                    menor = " La lectura actual es menor que la anterior\n";
                }else{
                    menor = "";
                }

                if(!lec1.equals(lec2)){
                    error = "Los datos de lectura tienen que ser iguales";
                }

                if(error.equals("")){
                    int total = Integer.parseInt(lec1) - Integer.parseInt(lecAnt);
                    String finalLec = lec1;
                    String finalLec1 = lec2;
                    AlertDialog builder = new MaterialAlertDialogBuilder( LecturaCasf.this)
                        .setTitle("Lectura")
                        .setMessage(String.format("%s Lectura anterior %s Lectura actual %s \nConsumo %s", menor, lecAnt, lec2, total))
                        .setNegativeButton("Cancelar", null)
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            guardar(finalLec, finalLec1);
                        }).show();

                builder.show();
                }else {
                    Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void  guardar( String lec1, String lec2 ){

            base.abrir();

            if(!guardado.equals("0")){
                base.actualizaLectura( _ID, folioCont, lec1, lec2 );
            }else{
                base.insertarCasf( folioCont, lec1, lec2, periodo, anio);
            }
            base.cerrar();

            Toast.makeText(LecturaCasf.this, "Datos guardados", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), GpsCasf.class);
            startActivity(intent);
            finish();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), InicioCasf.class);
        startActivity(intent);
        finish();
    }
}
