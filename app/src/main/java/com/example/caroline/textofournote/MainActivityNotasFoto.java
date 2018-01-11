package com.example.caroline.textofournote;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.database.sqlite.SQLiteDatabase;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MainActivityNotasFoto extends AppCompatActivity implements View.OnClickListener {
    private TextView edtTitulo;
    private TextView edtTexto;
    private Button AgregarBt;
    private ImageView imgViewFoto;
    private  static final int ACTIVITY_START_CAMERA =0;
    private String mImageFileLocation="";
    private File photoArchive;
    private SQLiteDatabase banco = null;
    String nomeBanco = "notas1.sqlite";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        criarBanco();
        setContentView(R.layout.notasfoto);
        edtTitulo = (TextView) findViewById(R.id.edtTitulo);
        imgViewFoto = (ImageView) findViewById(R.id.imageView);
        edtTexto = (TextView) findViewById(R.id.edtTexto);
        AgregarBt = (Button) findViewById(R.id.btnSalvar);
        AgregarBt.setOnClickListener(this);


        imgViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFullImage(photoArchive);
            }
        });


    }





///ampliar imagem quando clica
    public void openFullImage(File photoArc){
        if(photoArc!=null) {
            Uri uriPhoto =Uri.fromFile(photoArc);
            String sUri = uriPhoto.toString();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(sUri), "image/*");
            startActivity(intent);
        }

    }



    public void takePhoto(View view){
        Intent callCamera = new Intent();
        callCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;
        try{
            photoFile = createImageFile();
        }catch (IOException e){
            e.getCause();
            e.printStackTrace();

        }
        if(photoFile!=null) {
            callCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(callCamera, ACTIVITY_START_CAMERA);
            photoArchive=photoFile;

        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSalvar) {
            salvarNotaFotos(edtTitulo.getText().toString(), mImageFileLocation, edtTexto.getText().toString());
            mImageFileLocation="";

        }

    }

    public String getTitulo(){

        return edtTitulo.getText().toString();
    }

    public String getTexto(){

        return edtTexto.getText().toString();
    }

    public String getFoto(){

        return mImageFileLocation;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==ACTIVITY_START_CAMERA && resultCode == RESULT_OK){

            setReducedImageSize();

        }
    }

    File createImageFile() throws IOException{

        String state  = Environment.getExternalStorageState();

        if(Environment.MEDIA_MOUNTED.equals(state)){
            mImageFileLocation="";
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "IMAGE" + timeStamp + "_";
            //File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            File storageDirectory = getDir();
            File image = File.createTempFile(imageFileName,".jpg",storageDirectory);
            mImageFileLocation = image.getAbsolutePath();//pode ler e escrever na midia

            return image;
        }else{
            if(Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)){
                //só pode ler a midia

                Toast.makeText(this,"Impossível escrever na memória. Acesso Negado (Somente Leitura)",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Não foi possivel acessar a mídia, por favor, verifique o armazenamento",Toast.LENGTH_SHORT).show();
                //não pode acessar a midia

            }
        }
        return null;
    }

    private File getDir(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            File sdcard = Environment.getExternalStorageDirectory()
                    .getAbsoluteFile();
            File dir = new File(sdcard, "4Note" + File.separator + "photo_note");
            if (!dir.exists())
                dir.mkdirs();
            return dir;
        }else{
            return null;
        }

    }
    void setReducedImageSize(){
        int targetImageViewWidth = imgViewFoto.getWidth();
        int targerImageViewHeight = imgViewFoto.getHeight();
        if(targerImageViewHeight!=0 && targetImageViewWidth!=0) {
            BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
            bmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mImageFileLocation, bmpOptions);
            int cameraImageWidth = bmpOptions.outWidth;
            int cameraImageHeight = bmpOptions.outHeight;
            int scaleFactor = Math.min(cameraImageWidth / targetImageViewWidth, cameraImageHeight / targerImageViewHeight);
            bmpOptions.inSampleSize = scaleFactor;
            bmpOptions.inJustDecodeBounds = false;

            Bitmap photoReducedSizeBitmap = BitmapFactory.decodeFile(mImageFileLocation, bmpOptions);
            imgViewFoto.setImageBitmap(photoReducedSizeBitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main,/*menu_main_activity_insert_n,*/ menu);
        return true;
    }

    public boolean criarBanco(){
        boolean aux=true;
        try{

            banco = openOrCreateDatabase(nomeBanco,MODE_PRIVATE,null);
            banco.execSQL("CREATE TABLE IF NOT EXISTS notasFotos (_id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, foto TEXT, texto TEXT);");
            Log.d("Sucesso","Bd criado");


        }catch (Exception e){
            aux=false;

        }
        return aux;
    }


    public void salvarNotaFotos(String titulo, String foto, String texto) {
        if (titulo.length() > 0) {
            if (foto != "") {
                try {

                    banco.execSQL("INSERT INTO notasFotos(titulo,foto,texto) VALUES ('" + titulo + "','" + foto + "','" + texto + "')");
                    String[] colunas = {"titulo", "foto", "texto"};
                    Cursor c = banco.query(true, "notasFotos", colunas, null, null, null, null, null, null);
                    while (c.moveToNext()) {
                        Log.i(String.valueOf(c.getColumnIndex("titulo")), c.getString(c.getColumnIndex("titulo")));
                    }
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
                Toast.makeText(getBaseContext(), "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(getBaseContext(), "É necessário ter uma foto", Toast.LENGTH_SHORT).show();
            }
            } else {
                Toast.makeText(getBaseContext(), "Titulo ja existente", Toast.LENGTH_SHORT).show();
            }
        mImageFileLocation="";
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
