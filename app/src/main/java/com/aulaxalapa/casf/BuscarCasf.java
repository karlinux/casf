package com.aulaxalapa.casf;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.SharedPreferencesManager;
import com.aulaxalapa.casf.data.Handler_sqlite;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.aulaxalapa.casf.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BuscarCasf extends AppCompatActivity {

    private ListView lista;
    private ArrayList<String> nameList;
    private TextView tvUsuario;
    private Handler_sqlite base;
    private Adaptador adaptador;
    private String _ID, guardado, dia;
    private EditText etBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        base= new Handler_sqlite(this);

        Date fechaActual = new Date();
        SimpleDateFormat d = new SimpleDateFormat("dd");
        dia = d.format(fechaActual);
        final Intent intents = new Intent(this, MainActivity.class);

        if(!dia.equals(SharedPreferencesManager.getSomeStringValue(Constantes.PREF_DIA))){
            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_TOKEN, "");
            startActivity(intents);
            finish();
        }
        base.abrir();
        //base.getTablas("SELECT * FROM CASF", "Buscar");
        guardado = base.getGuardado();
        _ID = base.getId(guardado);
        base.cerrar();
        redireccionar();

        FloatingActionButton fab = findViewById(R.id.fab);
        int miColor = getResources().getColor(R.color.colorPrimary);
        ColorStateList csl = new ColorStateList(new int[][]{new int[0]}, new int[]{miColor});
        fab.setBackgroundTintList(csl);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar;
                snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
                snackbar.setAction("Cerrar sesión", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesManager.setSomeStringValue(Constantes.PREF_TOKEN, "");
                        startActivity(intents);
                        finish();
                    }
                }).show();
            }
        });

        lista = findViewById(R.id.list);
        etBuscar = findViewById(R.id.etBuscar);

        tvUsuario = findViewById(R.id.tvUsuario);
        String usuario = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NOMBRE);
        tvUsuario.setText(usuario);

        productos();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long ids) {

                String envio = String.valueOf(GetArrayItems().get(position).getColor());
                String ide = String.valueOf(GetArrayItems().get(position).getIde());
                if(!envio.equals("0")){
                String id = String.valueOf(GetArrayItems().get(position).getId());
                String folioCont = String.valueOf(GetArrayItems().get(position).getContenido());
                SharedPreferencesManager.setSomeStringValue(Constantes.PREF_ID, id);
                SharedPreferencesManager.setSomeStringValue(Constantes.PREF_FOLIOCONT, folioCont);
                SharedPreferencesManager.setSomeStringValue(Constantes.PREF_IDE, ide);
                Intent intent = new Intent(getApplicationContext(), LecturaCasf.class);
                startActivity(intent);
                finish();
                }else{
                    Toast.makeText(BuscarCasf.this, "LOS REGISTROS SINCRONIZADOS NO SE PUEDEN ACTUALIZAR", Toast.LENGTH_SHORT).show();
                }
            }
        });

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                productos( );
            }
        });

    }

    private void productos() {
        adaptador = new Adaptador(this, GetArrayItems());
        lista.setAdapter(adaptador);
    }

    private void redireccionar() {
        switch (guardado){
            case "1":
                Intent intent = new Intent(this, GpsCasf.class);
                startActivity(intent);
                finish();
                break;
            case "2":
                intent = new Intent(this, FotoMedidorCasf.class);
                startActivity(intent);
                finish();
                break;
            case "3":
            case "5":
            case "7":
                intent = new Intent(this, InconsistenciaCasf.class);
                startActivity(intent);
                finish();
                break;
            case "4":
            case "6":
            case "8":
                intent = new Intent(this, FotoInconsistenciaCasf.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    private ArrayList<Entidad> GetArrayItems(){
        ArrayList<Entidad> listItems = new ArrayList<>();
        base.abrir();
        String datos;
        datos = etBuscar.getText().toString();
        Cursor c;
        if (datos.equals(""))
            c = base.getUniversos( );
        else
            c = base.getUniversos( datos );

        listItems.clear();
        if(c.getCount() > 0) {
            while (c.moveToNext()) {
                listItems.add(
                        new Entidad(
                                c.getString(0), c.getString(1),
                        c.getString(2) +
                                " " + c.getString(3) +
                                " " + c.getString(4) +
                                "\nFolio: " + c.getString(9) +
                                "\n" + c.getString(5) + " " + c.getString(6) +
                                "\nMedidor: " + c.getString(12), c.getString(16),
                                c.getString(17)
                ));
            }
        }
        c.close();
        base.close();
        return listItems;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.universos:
                Intent intent = new Intent(this, UniversosCasf.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sincronizar:
                intent = new Intent(this, SincronizarCasf.class);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
