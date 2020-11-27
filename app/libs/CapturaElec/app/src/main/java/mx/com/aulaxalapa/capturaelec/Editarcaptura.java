package mx.com.aulaxalapa.capturaelec;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.IOException;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.SharedPreferencesManager;
import mx.com.aulaxalapa.capturaelec.data.FotoViewModel;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqlite;

public class Editarcaptura extends AppCompatActivity {

    Handler_sqlite base = new Handler_sqlite(this);
    FotoViewModel fotoViewModel;
    EditText etPaterno, etMaterno, etNombre, etDomicilio, etEstado, etMunicipio,
            etLocalidad, etDomicilioActual, etTelefono, etCorreo, etClave, etSeccion;

    File photoFile;
    ImageView ivCredencial;
    String foto, path, imagen, idimagen, status, id;
    private String TAG = "Editarcaptura";
    Button btnActualizar;
    String actualizado, imei;
    File file;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fotoViewModel = ViewModelProviders.of(this).get(FotoViewModel.class);

        etPaterno = findViewById(R.id.etPaterno);
        etMaterno = findViewById(R.id.etMaterno);
        etNombre = findViewById(R.id.etNombre);
        etDomicilio = findViewById(R.id.etDomicilio);
        etEstado = findViewById(R.id.etEstado);
        etMunicipio = findViewById(R.id.etMunicipio);
        etLocalidad = findViewById(R.id.etLocalidad);
        etDomicilioActual = findViewById(R.id.etDomicilioActual);
        etTelefono = findViewById(R.id.etTelefono);
        etCorreo = findViewById(R.id.etCorreo);
        etClave = findViewById(R.id.etClave);
        etSeccion = findViewById(R.id.etSeccion);


        ivCredencial = findViewById(R.id.ivCredencial);
        btnActualizar = findViewById(R.id.btnActualizar);
        etDomicilioActual.setVisibility(View.GONE);
        imagen = Environment.getExternalStorageDirectory() + Constantes.CARPETA;
        base.abrir();
        Cursor c;
        id = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_ID);

        c = base.getDatos( id );
        c.moveToFirst();
        etPaterno.setText(c.getString(2));
        etMaterno.setText(c.getString(3));
        etNombre.setText(c.getString(1));
        etDomicilio.setText(c.getString(4));
        etEstado.setText(c.getString(5));
        etMunicipio.setText(c.getString(6));
        etLocalidad.setText(c.getString(7));
        etClave.setText(c.getString(8));
        etSeccion.setText(c.getString(9));
        etDomicilioActual.setText(c.getString(10));
        etTelefono.setText(c.getString(11));
        etCorreo.setText(c.getString(12));

        String fecha = c.getString(13);
        status = c.getString(14);


        foto = "";

        file = new File(foto);
        if( file.exists() ){
            Glide.with(this)
                    .load( foto )
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into( ivCredencial );
        }else{

        }

        base.cerrar();

        ivCredencial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dispatchTakePictureIntent();
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String error = "";

                if (etNombre.getText().toString().equals("")) {
                    error = "INGRESE EL NOMBRE";
                    etNombre.setError(error);
                } else if (etPaterno.getText().toString().equals("")) {
                    error = "INGRESE APELLIDO PATERNO";
                    etPaterno.setError(error);
                } else if (etDomicilio.getText().toString().equals("")) {
                    error = "INGRESE DOMICILIO";
                    etDomicilio.setError(error);
                }else if (etMunicipio.getText().toString().equals("")) {
                    error = "INGRESE EL MUNICIPIO";
                    etMunicipio.setError(error);
                }else if (etLocalidad.getText().toString().equals("")) {
                    error = "INGRESE LA LOCALIDAD";
                    etLocalidad.setError(error);
                }else if (etClave.getText().toString().equals("")) {
                    error = "INGRESE LA CLAVE DE INE";
                    etClave.setError(error);
                }else if (etSeccion.getText().toString().equals("")) {
                    error = "INGRESE LA SECCION";
                    etSeccion.setError(error);
                }else if (etTelefono.getText().toString().equals("")) {
                    error = "INGRESE EL TELÉFONO";
                    etTelefono.setError(error);
                }else if (etCorreo.getText().toString().equals("")) {
                    error = "INGRESE EL CORREO";
                    etCorreo.setError(error);
                }

                if(status.equals("1")){
                    actualizado = "1";
                }else{
                    actualizado = "0";
                }
                if(error.equals("")){

                    String nombre = etNombre.getText().toString();
                    String apaterno = etPaterno.getText().toString();
                    String amaterno = etMaterno.getText().toString();
                    String domicilioine = etDomicilio.getText().toString();
                    String domicilioactual =  etDomicilioActual.getText().toString();
                    String idestado =  etDomicilioActual.getText().toString();
                    String idmunicipio =  etMunicipio.getText().toString();
                    String localidad =   etLocalidad.getText().toString();
                    String idseccion =  etSeccion.getText().toString();
                    String fingreso =  etDomicilioActual.getText().toString();
                    String telefono =  etTelefono.getText().toString();
                    String correo =  etCorreo.getText().toString();
                    String urlcred =  etDomicilioActual.getText().toString();
                    String clave =  etClave.getText().toString();

                    base.abrir();
                    base.actualzaEditar(
                            id,
                            nombre,
                            apaterno,
                            amaterno,
                            domicilioine,
                            idmunicipio,
                            localidad,
                            idseccion,
                            clave,
                            domicilioactual,
                            correo,
                            telefono,
                            actualizado
                    );
                    base.cerrar();
                    //enviar();
                    Intent intent = new Intent( getApplicationContext(), InicioCaptura.class);
                    startActivity( intent );
                    finish();

                }else{
                    Toast.makeText(Editarcaptura.this, error, Toast.LENGTH_SHORT).show();
                }
                base.cerrar();

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            ivCredencial.setImageBitmap(imageBitmap);

            base.abrir();
            if(path.equals("0") && file.exists()){
                base.insertarFoto( idimagen+".jpg", idimagen);
            }else if( file.exists() ){
                base.actualizaImagen(idimagen, "1");
            }
            base.cerrar();
            //fotoViewModel.uploadFoto( photoFile.getAbsolutePath() );
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                    photoURI = FileProvider.getUriForFile(this,
                            getApplicationContext().getOpPackageName() + ".provider",
                            photoFile);
                }
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    public void registro(View v){
        Intent intent = new Intent(getApplicationContext(), RegistroCaptura.class);
        startActivity(intent);
        finish();

    }
    private File createImageFile() throws IOException {
        // Create an image file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File image = new File(foto);
        // Save a file: path for use with ACTION_VIEW intents
        String currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent( getApplicationContext(), InicioCaptura.class );
        startActivity(intent);
        finish();
    }
}
