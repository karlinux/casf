package mx.com.aulaxalapa.capturaelec;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import mx.com.aulaxalapa.capturaelec.common.Constantes;
import mx.com.aulaxalapa.capturaelec.common.SharedPreferencesManager;
import mx.com.aulaxalapa.capturaelec.data.FotoViewModel;
import mx.com.aulaxalapa.capturaelec.data.Handler_sqlite;

public class Fotocapturaanterior extends AppCompatActivity {
    private final Handler_sqlite base = new Handler_sqlite(this);

    private static final int CAMARA_PERMISSION = 11;
    Button btnGuardar;
    ImageView btnFoto;
    String imeistring, foto, imagen, fecha, PATH, TAG;
    int id;
    File file;
    TextView tvDatos;
    FotoViewModel fotoViewModel;
    Boolean bool;

    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);
        TAG = getClass().getName();
        fotoViewModel = ViewModelProviders.of(this).get(FotoViewModel.class);
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

        File directory = new File(Environment.getExternalStorageDirectory()+ Constantes.CARPETA);
        if(directory.exists()){
            directory.mkdirs();
        }

        System.gc();

        btnFoto = findViewById(R.id.imgBtnFoto);
        base.abrir();
        id = base.id();
        fecha = base.fecha( Constantes.INE, String.valueOf(id));
        base.cerrar();
        imagen = Environment.getExternalStorageDirectory() + Constantes.CARPETA;

        imeistring = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_CLAVE);

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

            btnFoto.setImageResource(R.drawable.perfil);
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

        btnGuardar = findViewById(R.id.btnIniciar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enviar();
                base.setGuardado("2", String.valueOf(id));
                Intent intent = new Intent(getApplicationContext(), GpsOcr.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void enviar() {
        base.abrir();
        base.insertarFoto( imeistring+".jpg", imeistring);
        base.cerrar();
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
            Intent inteF = new Intent(getApplicationContext(), Fotocapturaanterior.class);
            startActivity(inteF);
            finish();
            foto="";
        }else{
            btnFoto.setImageResource(R.drawable.perfil);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            Intent int1 = new Intent(getApplicationContext(), Fotocapturaanterior.class);
            startActivity(int1);
            return true;
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(requestCode == CAMARA_PERMISSION){

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                Intent inteF = new Intent(getApplicationContext(), Fotocapturaanterior.class);
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