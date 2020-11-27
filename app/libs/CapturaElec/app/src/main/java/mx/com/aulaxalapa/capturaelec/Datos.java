package mx.com.aulaxalapa.capturaelec;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.MyApp;
import mx.com.aulaxalapa.capturaelec.common.SharedPreferencesManager;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqlite;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqliteU;

/**
 * Main activity demonstrating how to pass extra parameters to an activity that
 * recognizes text.
 */
public class Datos extends AppCompatActivity {

    // Use a compound button so either checkbox or switch widgets work.
    private String selecSexo, selecEscolaridad, selecCasilla, selecOcupacion, promotor, clave, idCoordinador, idLider, idJefe;
    private EditText etNombre, etPaterno, etMaterno, etDomicilio, etSeccion, etColonia;
    private Button btnGuardar, btnBuscar;

    Spinner spSexo, spEscolaridad, spCasilla, spOcupacion;
    Handler_sqlite base;
    Handler_sqliteU baseU;
    private EditText etCurp;
    private EditText etTelefono;
    private EditText etCorreo;
    private EditText etFacebook;
    private EditText etTwitter;
    private EditText etEmision;
    private EditText etVigencia;
    ArrayList<String> nameList;

    private static final int RC_OCR_CAPTURE = 9003;
    private static String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        base = new Handler_sqlite(this);
        baseU = new Handler_sqliteU(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TAG = getClass().getName();
        promotor = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID_USUARIO);
        idCoordinador = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_IDCOORDINADOR);
        idLider = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_IDLIDER);
        idJefe = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_IDJEFE);
        clave = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_CLAVE);
        String json = "[{\"id\":\"1\",\"ocupacion\":\"EMPLEADO(A)\"},{\"id\":\"2\",\"ocupacion\":\"AMA DE CASA\"},{\"id\":\"3\",\"ocupacion\":\"CARPINTERO\"},{\"id\":\"4\",\"ocupacion\":\"ABOGADO(A)\"},{\"id\":\"5\",\"ocupacion\":\"ALBA\\u00d1IL\"},{\"id\":\"6\",\"ocupacion\":\"ASISTENTE\"},{\"id\":\"7\",\"ocupacion\":\"ESTUDIANTE\"},{\"id\":\"8\",\"ocupacion\":\"PROFESOR\"},{\"id\":\"9\",\"ocupacion\":\"DOCTOR(A)\"},{\"id\":\"10\",\"ocupacion\":\"GANADERO\"},{\"id\":\"11\",\"ocupacion\":\"COMERCIANTE\"},{\"id\":\"12\",\"ocupacion\":\"AGRICULTOR\"},{\"id\":\"13\",\"ocupacion\":\"TAXISTA\"},{\"id\":\"14\",\"ocupacion\":\"PASTOR(A)\"},{\"id\":\"15\",\"ocupacion\":\"LIDER RELIGIOSO\"},{\"id\":\"16\",\"ocupacion\":\"FUNCIONARIO MUNICIPAL\"},{\"id\":\"17\",\"ocupacion\":\"ENFERMERA(O)\"},{\"id\":\"18\",\"ocupacion\":\"MECANICO\"},{\"id\":\"19\",\"ocupacion\":\"PANADERO\"},{\"id\":\"20\",\"ocupacion\":\"SACERDOTE\"},{\"id\":\"21\",\"ocupacion\":\"AGENTE MUNICIPAL\"},{\"id\":\"22\",\"ocupacion\":\"SUB AGENTE MUNICIPAL\"},{\"id\":\"23\",\"ocupacion\":\"COMISARIADO EJIDAL\"},{\"id\":\"24\",\"ocupacion\":\"PRESIDENTE DE COLONIA\"},{\"id\":\"25\",\"ocupacion\":\"DIRECTOR DE ESCUELA\"},{\"id\":\"26\",\"ocupacion\":\"SUPERVISOR ESCOLAR\"},{\"id\":\"27\",\"ocupacion\":\"QUIMICO(A)\"},{\"id\":\"28\",\"ocupacion\":\"OFICINISTA\"},{\"id\":\"29\",\"ocupacion\":\"ARQUITECTO\"},{\"id\":\"30\",\"ocupacion\":\"CHOFER\"},{\"id\":\"31\",\"ocupacion\":\"CONTADOR(A)\"},{\"id\":\"32\",\"ocupacion\":\"DEPORTISTA\"},{\"id\":\"33\",\"ocupacion\":\"ELECTRICISTA\"},{\"id\":\"34\",\"ocupacion\":\"FARMACEUTICO(A)\"},{\"id\":\"35\",\"ocupacion\":\"FONTANERO\"},{\"id\":\"36\",\"ocupacion\":\"INGENIERO\"},{\"id\":\"37\",\"ocupacion\":\"MECANICO\"},{\"id\":\"38\",\"ocupacion\":\"MUSICO\"},{\"id\":\"39\",\"ocupacion\":\"PERIODISTA\"},{\"id\":\"40\",\"ocupacion\":\"POLICIA\"},{\"id\":\"41\",\"ocupacion\":\"VENDEDOR AMBULANTE\"},{\"id\":\"42\",\"ocupacion\":\"PSICOLOGO(A)\"},{\"id\":\"43\",\"ocupacion\":\"DENTISTA\"},{\"id\":\"44\",\"ocupacion\":\"GINECOLOGO\"},{\"id\":\"45\",\"ocupacion\":\"OTRO\"},{\"id\":\"46\",\"ocupacion\":\"FOTOGRAFO\"},{\"id\":\"47\",\"ocupacion\":\"EMPRESARIO(A)\"}]";

        etNombre = findViewById(R.id.etNombre);
        etPaterno = findViewById(R.id.etPaterno);
        etMaterno = findViewById(R.id.etMaterno);
        etDomicilio = findViewById(R.id.etDomicilio);
        etColonia = findViewById(R.id.etColonia);
        etSeccion = findViewById(R.id.etSeccion);
        spOcupacion = findViewById(R.id.spOcupacion);
        spCasilla = findViewById(R.id.spCasilla);
        spSexo = findViewById(R.id.spSexo);
        spEscolaridad = findViewById(R.id.spEscolaridad);
        etCurp = findViewById(R.id.etCurp);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        etFacebook = findViewById(R.id.etFacebook);
        etTwitter = findViewById(R.id.etTwitter);
        etEmision = findViewById(R.id.etEmision);
        etVigencia = findViewById(R.id.etVigencia);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnBuscar = findViewById(R.id.btnBuscar);

        try {
            databases( json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        baseU.abrir();
        Cursor cur = baseU.getIne(clave);
        if(cur.getCount()>0){
            while(cur.moveToNext()){
                etNombre.setText(cur.getString(1));
                etPaterno.setText(cur.getString(2));
                etMaterno.setText(cur.getString(3));
                etSeccion.setText(cur.getString(4));
                etDomicilio.setText(cur.getString(5) + " " + cur.getString(6));
                etColonia.setText(cur.getString(7));
                casillas(cur.getString(4));
            }
        }else{
            Toast.makeText(Datos.this, "No se encontró el usuario", Toast.LENGTH_SHORT).show();
            etDomicilio.setText("");
            etColonia.setText("");
            etNombre.setText("");
            etPaterno.setText("");
            etMaterno.setText("");
        }
        baseU.cerrar();

        spSexo.setAdapter(ArrayAdapter.createFromResource(this, R.array.sexo, R.layout.spinner_item));

        spSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selecSexo = String.valueOf(spSexo.getSelectedItem());
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        spEscolaridad.setAdapter(ArrayAdapter.createFromResource(this, R.array.escolaridad, R.layout.spinner_item));

        spEscolaridad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selecEscolaridad = String.valueOf(spEscolaridad.getSelectedItem());
                String[] escolaridad = selecEscolaridad.split("-");
                selecEscolaridad = escolaridad[0];
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        spOcupacion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selecOcupacion = String.valueOf(spOcupacion.getSelectedItem());
                String[] cupacion = selecOcupacion.split("-");
                selecOcupacion = cupacion[0];

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        spCasilla.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                selecCasilla = String.valueOf(spCasilla.getSelectedItem());
                String[] casilla = selecCasilla.split("-");
                selecCasilla = casilla[0];
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }

        });

        etSeccion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                casillas( ""+s );
            }
        });

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String error = "";
                if (etNombre.getText().toString().equals("")){
                    error = "INGRESE EL NOMBRE";
                    etNombre.setError(error);
                } else if (etPaterno.getText().toString().equals("")){
                    error = "INGRESE APELLIDO PATERNO";
                    etPaterno.setError(error);
                } else if (etDomicilio.getText().toString().equals("")){
                    error = "INGRESE DOMICILIO";
                    etDomicilio.setError(error);
                } else if (etColonia.getText().toString().equals("")){
                    error = "INGRESE LA COLONIA";
                    etColonia.setError(error);
                } else if (etSeccion.getText().toString().equals("")){
                    error = "INGRESE LA SECCIÓN";
                    etSeccion.setError(error);
                } else if (selecCasilla.equals("CASILLA")){
                    error = "SELECCIONE LA CASILLA";
                } else if (selecSexo.equals("SEXO")){
                    error = "INGRESE EL SEXO";
                } else if (etTelefono.getText().toString().equals("")){
                    error = "INGRESE EL TELÉFONO";
                    etTelefono.setError(error);
                } else if (etEmision.getText().toString().equals("")){
                    error = "INGRESE LA EMISIÓN";
                    etEmision.setError(error);
                } else if (etVigencia.getText().toString().equals("")){
                    error = "INGRESE LA VIGENCIA";
                    etVigencia.setError(error);
                }

                if( idCoordinador.equals("0") ){
                    idCoordinador = promotor;
                }else if ( idLider.equals("0") ){
                    idLider = promotor;
                }else if ( idJefe.equals("0") ){
                    idJefe = promotor;
                }

                if(error.equals("")) {
                    base.abrir();
                    base.insertar(
                            clave,
                            etNombre.getText().toString(),
                            etPaterno.getText().toString(),
                            etMaterno.getText().toString(),
                            etDomicilio.getText().toString(),
                            etColonia.getText().toString(),
                            etSeccion.getText().toString(),
                            selecOcupacion,
                            etCurp.getText().toString(),
                            etTelefono.getText().toString(),
                            etCorreo.getText().toString(),
                            etFacebook.getText().toString(),
                            etTwitter.getText().toString(),
                            etEmision.getText().toString(),
                            etVigencia.getText().toString(),
                            selecSexo,
                            selecCasilla,
                            selecEscolaridad,
                            promotor,
                            idCoordinador,
                            idLider,
                            idJefe
                    );
                    base.cerrar();
                    Intent intents = new Intent(getApplicationContext(), Fotocapturaanterior.class);
                    startActivity(intents);
                    finish();
                }
            }
        });
    }

    private void casillas( String seccion) {
        baseU.abrir();
        Cursor c = baseU.getCasillas( seccion );
            List<String> sectorLista;
            sectorLista = new ArrayList<String>();
            sectorLista.add( "CASILLA" );

        if(c.getCount() > 0){
            while (c.moveToNext()){
                sectorLista.add(c.getString(3)+ "-" + c.getString(1)+ " " + c.getString(2));
            }
        }
        ArrayAdapter<String> myadapter = new ArrayAdapter<>(MyApp.getContext(), R.layout.spinner_item, sectorLista);
        spCasilla.setAdapter(myadapter);
        baseU.cerrar();
    }

    public void databases(String json) throws JSONException {
        JSONArray jsonobject = new JSONArray(json);
        nameList = new ArrayList<String>();

        nameList.add("OCUPACIÓN");
        for (int i = 0; i < jsonobject.length(); i++) {
            JSONObject pru = jsonobject.getJSONObject(i);
            nameList.add( pru.getString("id") +"-"+ pru.getString("ocupacion")); //id
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, nameList);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        // Apply the adapter to the spinner
        this.spOcupacion.setAdapter(arrayAdapter);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, BuscarOcr.class);
        startActivity(intent);
        finish();
    }
}
