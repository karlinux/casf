package com.aulaxalapa.casf;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.SharedPreferencesManager;
import com.aulaxalapa.casf.data.Handler_sqlite;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.io.IOException;

public class FotoIncoCasf extends AppCompatActivity {
    private Handler_sqlite base = new Handler_sqlite(this);

    private String folioCont, guardado, _ID, imagen, imeistring, fecha, foto, error, password, id;
    ConstraintLayout.LayoutParams linear;
    private String TAG = "Fotocaptura";
    private File file;
    Button imgBtnIniciar;
    ImageView imgBtnFoto;
    TextView tvUsuario, tvDatos;
    File photoFile;

    @SuppressLint({"HardwareIds", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotoincon);

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
        base.getTablas("SELECT * FROM CASF", "GPS");
        _ID = base.getId("2");
        base.cerrar();

        imgBtnIniciar = findViewById(R.id.btnCargar);
        imgBtnFoto = findViewById(R.id.imgBtnFoto);
        tvUsuario = findViewById(R.id.tvUsuario);
        String usuario = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_NOMBRE);
        tvUsuario.setText(usuario);

        imagen =  getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();

        base.abrir();
        id = base.getId( "2");
        fecha = base.fecha( Constantes.CASF, String.valueOf(id));
        base.cerrar();

        final String imei = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_IDUSU);

        StringBuilder stringFechaI = new StringBuilder();
        stringFechaI.append(imei);
        stringFechaI.append("_");
        stringFechaI.append(fecha.substring(0,4));
        stringFechaI.append(fecha.substring(5,7));
        stringFechaI.append(fecha.substring(8,10));
        stringFechaI.append(fecha.substring(11,13));
        stringFechaI.append(fecha.substring(14,16));
        stringFechaI.append(fecha.substring(17,19));

        imeistring = stringFechaI.toString();

        StringBuilder stringFecha = new StringBuilder();
        stringFecha.append(imagen);
        stringFecha.append("/");
        stringFecha.append(imeistring);
        stringFecha.append(guardado + ".jpg");

        foto = stringFecha.toString();

        file = new File(foto);

        imgBtnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InconsistenciaCasf.class);
                startActivity(intent);
                finish();
            }
        });

        if(file.exists()){
            Glide.with(this)
                    .load(foto)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(imgBtnFoto);

        }else{

            //imgBtnFoto.setImageResource(R.drawable.perfil);
        }

        //Boton paara tomar la foto
        imgBtnFoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        imgBtnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if(file.exists()){

                base.abrir();
                switch (guardado){
                    case "4":
                        base.setCampo( _ID, Constantes.FOTOINCONSISTENCIA1, imeistring+guardado+".jpg", "5");
                        break;
                    case "6":
                        base.setCampo( _ID, Constantes.FOTOINCONSISTENCIA2, imeistring+guardado+".jpg", "7");
                        break;
                    case "8":
                        base.setCampo( _ID, Constantes.FOTOINCONSISTENCIA3, imeistring+guardado+".jpg", "0");
                        base.setEstado(_ID);
                        break;
                }
                Intent intent = new Intent(getApplicationContext(), InconsistenciaCasf.class);
                startActivity(intent);
                finish();
                base.cerrar();
            }else{
                Toast.makeText(FotoIncoCasf.this, "No ha capturado la foto", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }


    //Acción a ejecutar despues de tomar la foto
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            Glide.with(this)
                    .load(foto)
                    .dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(imgBtnFoto);

            //imgBtnFoto.setImageBitmap(imageBitmap);
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
        Intent intent = new Intent(this, GpsCasf.class);
        startActivity(intent);
        finish();
    }
}
