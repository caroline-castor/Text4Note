package com.example.caroline.textofournote;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;


/**
 * Created by Carol on 04/12/2015.
 */
public class VisualizarNotasFotos extends Activity {
    TextView tvTitulo;
    TextView tvTexto;
    ImageView ivFoto;
    Bitmap myBitmap=null;
    NotasComFoto ncf;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualizar_nf);
        Bundle extras = getIntent().getExtras();
        int p = extras.getInt("position");
        ArrayList<NotasComFoto> ancf = generateBooks();
        ancf.addAll(ancf);
        ncf=new NotasComFoto(ancf.get(p).getName(),ancf.get(p).getFoto(),ancf.get(p).getHexValue());
        tvTitulo=(TextView)findViewById(R.id.textView6);
        tvTexto=(TextView)findViewById(R.id.textView7);
        ivFoto=(ImageView)findViewById(R.id.imageView4);
        tvTitulo.setText(ncf.getName());

        tvTexto.setText(ncf.getHexValue());
        Activity activity = this;
        tvTexto.setTextSize(getFontSize(activity));


        ivFoto.setImageBitmap(ncf.getFoto());


    }
    public static int getFontSize (Activity activity) {

        DisplayMetrics dMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);

        // lets try to get them back a font size realtive to the pixel width of the screen
        final float WIDE = activity.getResources().getDisplayMetrics().widthPixels;
        int valueWide = (int)(WIDE / 32.0f / (dMetrics.scaledDensity));
        return valueWide;
    }
    public ArrayList<NotasComFoto> generateBooks() {
        SQLiteDatabase banco = null;
        ArrayList<NotasComFoto> palettes = new ArrayList<>();
        String NOME_BANCO="notas1.sqlite";
        Cursor c;

        banco = openOrCreateDatabase(NOME_BANCO, MODE_PRIVATE, null);
        c=banco.rawQuery("SELECT * FROM notasFotos", null);

        if(c!=null){
            if(c.moveToFirst()){
                do{
                    String caminhoFoto = c.getString(c.getColumnIndex("foto"));
                    File imgFile = new File(caminhoFoto);
                    if(imgFile.exists()){
                        myBitmap = decodeFile(imgFile,640,480);
                    }
                    palettes.add(new NotasComFoto(c.getString(c.getColumnIndex("titulo")),myBitmap,c.getString(c.getColumnIndex("texto"))));

                }while (c.moveToNext());


            }
        }
        return palettes;
    }

    private Bitmap decodeFile(File f,int req_Height,int req_Width){
        try {
            //decode image size
            BitmapFactory.Options o1 = new BitmapFactory.Options();
            o1.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o1);


            //Find the correct scale value. It should be the power of 2.
            int width_tmp = o1.outWidth;
            int height_tmp = o1.outHeight;
            int scale = 1;

            if(width_tmp > req_Width || height_tmp > req_Height)
            {
                int heightRatio = Math.round((float) height_tmp / (float) req_Height);
                int widthRatio = Math.round((float) width_tmp / (float) req_Width);


                scale = heightRatio < widthRatio ? heightRatio : widthRatio;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            o2.inScaled = false;
            return BitmapFactory.decodeFile(f.getAbsolutePath(),o2);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}

