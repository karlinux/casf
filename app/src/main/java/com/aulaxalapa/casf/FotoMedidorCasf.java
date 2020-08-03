package com.aulaxalapa.casf;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.aulaxalapa.casf.common.Constantes;
import com.aulaxalapa.casf.common.SharedPreferencesManager;
import com.aulaxalapa.casf.data.Handler_sqlite;
import com.aulaxalapa.casf.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FotoMedidorCasf extends AppCompatActivity {
    private final Handler_sqlite base = new Handler_sqlite(this);

    String carpeta = "CASF";

    private static final int CAMARA_PERMISSION = 11;
    Button btnGuardar;
    ImageView btnFoto;
    String imeistring, _ID, foto, imagen, fecha, folio, dm, PATH, guardado, folioCont;
    File file;
    TextView tvDatos;
    Boolean bool;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        PATH = getApplicationContext().getPackageName();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMARA_PERMISSION);

                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMARA_PERMISSION);

                }

            } else {

            }
        }

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
        base.cerrar();
        //inserta.actualizaGuardado(String.valueOf(nameFoto), String.valueOf(0));
        base.cerrar();

        File directory = new File(Environment.getExternalStorageDirectory()+ "/" + carpeta);
        if(directory.exists()){
            directory.mkdirs();
        }

        System.gc();

        btnFoto = findViewById(R.id.imgBtnFoto);
        base.abrir();
        _ID = base.getId(guardado);
        fecha = base.fecha( Constantes.CASF, _ID);
        base.cerrar();
        imagen = Environment.getExternalStorageDirectory() + "/"+ carpeta +"/";
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
        stringFecha.append(imeistring);
        stringFecha.append( ".jpg");

        foto = stringFecha.toString();
        file = new File(foto);

        if(file.exists()){
            Bitmap bmSource = BitmapFactory.decodeFile(foto);
            int width3 = bmSource.getWidth();
            int height3 = bmSource.getHeight();
            if(height3<width3){
                bmSource = Bitmap.createScaledBitmap(bmSource,
                        300, 226, false);
            }else{
                bmSource = Bitmap.createScaledBitmap(bmSource,
                        226, 300, false);
            }

            btnFoto.setImageBitmap(bmSource);
        }else{

            btnFoto.setImageResource(R.drawable.medidoragua);
        }

        btnFoto = findViewById(R.id.imgBtnFoto);
        btnFoto.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri output = FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getOpPackageName() + ".provider", new File(foto));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                startActivityForResult(intent, 1);
            }
        });

        btnGuardar = findViewById(R.id.btnRegresar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                base.abrir();
                if(file.exists()){
                    base.setCampo( _ID, Constantes.FOTOMEDIDOR, imeistring+".jpg", "3");
                }else{
                    base.setCampo( _ID, Constantes.FOTOMEDIDOR, "", "3");
                }
                base.cerrar();
                Intent intent = new Intent(getApplicationContext(), InconsistenciaCasf.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @SuppressLint("MissingSuperCall")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        File file = new File(foto);

        boolean bools = false;
        bools = file.exists();

        if(bools){
            Bitmap bmSource = BitmapFactory.decodeFile(foto);
            int width3 = bmSource.getWidth();
            int height3 = bmSource.getHeight();
            if(height3<width3){

                bmSource = Bitmap.createScaledBitmap(bmSource,600, 450, false);
                Matrix matrix = new Matrix();
                float degrees = -90;
                matrix.postRotate(degrees);
                bmSource = Bitmap.createBitmap(bmSource, 0, 0, bmSource.getWidth(), bmSource.getHeight(), matrix, true);

            }else{
                bmSource = Bitmap.createScaledBitmap(bmSource,450, 600, false);
            }
            FileOutputStream fos1 = null;
            try
            {
                fos1 = new FileOutputStream(foto);
                bmSource.compress(Bitmap.CompressFormat.JPEG, 100, fos1);
                btnFoto.setImageBitmap(bmSource);
                fos1.flush();
                fos1.close();
                fos1 = null;

            }
            catch (IOException e)
            {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error " + e.getMessage().toString(), Toast.LENGTH_LONG).show();

            }
            Intent inteF = new Intent(getApplicationContext(), FotoMedidorCasf.class);
            startActivity(inteF);
            finish();
            foto="";
        }else{
            btnFoto.setImageResource(R.drawable.medidoragua);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent int1 = new Intent(getApplicationContext(), GpsCasf.class);
            startActivity(int1);
            return true;
        }else if (keyCode == 139){

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            dm =  Build.PRODUCT;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Uri output = FileProvider.getUriForFile(getApplicationContext(),
                        PATH + ".provider", new File(foto));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                startActivityForResult(intent, 1);
            }else {
                Uri output = Uri.fromFile(new File(foto));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, output);
                startActivityForResult(intent, 1);
            }
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == CAMARA_PERMISSION){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Intent inteF = new Intent(getApplicationContext(), FotoMedidorCasf.class);
                startActivity(inteF);
                finish();

            }else{
                finish();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapDrawable bmd = (BitmapDrawable) btnFoto.getDrawable();
        if(bmd != null){
            bmd.getBitmap().recycle();
        }
        btnFoto.setImageBitmap(null);
    }
}