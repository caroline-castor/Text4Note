package com.example.caroline.textofournote;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ShareActionProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Carol on 04/12/2015.
 */
public class MainNotasFotosCriadas extends AppCompatActivity  {

    private SQLiteDatabase banco = null;
    Context context;
    Intent mShareIntent;
    RecyclerView recyclerView;
    MyRecyclerAdapter myRecyclerAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=MainNotasFotosCriadas.this;
        setContentView(R.layout.notasfoto_criada);
        mShareIntent = new Intent();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerList);
        LinearLayoutManager linearLM = new LinearLayoutManager(this);
        linearLM.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLM);
        myRecyclerAdapter = new MyRecyclerAdapter(generateBooks(),context);
        recyclerView.setAdapter(myRecyclerAdapter);
        registerForContextMenu(recyclerView);
        final ArrayList mItems = new ArrayList<>(30);
        for (int i = 0; i < 30; i++) {
            mItems.add(String.format("Card number %2d", i));
        }

        SwipeableRecyclerView swipeTouchListener =
                new  SwipeableRecyclerView ( recyclerView ,
                        new  SwipeableRecyclerView . SwipeListener ()  {
                            @Override
                            public  boolean canSwipe ( int position) {
                                return  true ;
                            }

                            @Override
                            public  void onDismissedBySwipeLeft ( RecyclerView recyclerView ,  int [] reverseSortedPositions )  {
                                for  ( int position : reverseSortedPositions )  {
                                    mItems . remove(position);
                                    delete(position);
                                    finish();

                                }

                            }

                            @Override
                            public  void onDismissedBySwipeRight ( RecyclerView recyclerView ,  int [] reverseSortedPositions )  {
                                    String caminhoFoto="";
                                for  ( int position : reverseSortedPositions )  {
                                    String NOME_BANCO="notas1.sqlite";
                                    Cursor c;
                                    banco = openOrCreateDatabase(NOME_BANCO, MODE_PRIVATE, null);
                                    c = banco.rawQuery("SELECT * FROM notasFotos", null);
                                    NotasComFoto nf = buscaObjeto(position);

                                    if(c!=null){
                                        if(c.moveToFirst()){
                                            do{
                                                caminhoFoto = c.getString(c.getColumnIndex("foto"));
                                                if(c.getString(c.getColumnIndex("titulo")) == nf.getName()){
                                                    Log.d("Caminho","CAMINHO"+caminhoFoto);
                                                    break;
                                                }

                                            }while (c.moveToNext());


                                        }
                                    }





                                    Uri imageUri = Uri.parse(caminhoFoto);
                                    mShareIntent = new Intent();
                                    mShareIntent.setAction(Intent.ACTION_SEND);


                                    String nota = "Titulo: "+nf.getName()+"\n\n"+"Texto: "+nf.getHexValue();
                                    mShareIntent.putExtra(Intent.EXTRA_TEXT, nota);
                                    mShareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                                    mShareIntent.setType("image/*");
                                    mShareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    startActivity(Intent.createChooser(mShareIntent, "Share to:"));


                                }

                            }
                        });
        recyclerView. addOnItemTouchListener
                (swipeTouchListener);


    }




    public ArrayList<NotasComFoto> generateBooks() {
        ArrayList<NotasComFoto> palettes = new ArrayList<>();
        String NOME_BANCO="notas1.sqlite";
        Cursor c;
        Bitmap myBitmap=null;
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

    private Bitmap decodeFile(File f,int req_Height, int req_Width) {
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
    public NotasComFoto buscaObjeto(int position){
        ArrayList<NotasComFoto> aux = generateBooks();
        aux.addAll(aux);
        NotasComFoto nf_aux = new NotasComFoto(aux.get(position).getName(),aux.get(position).getFoto(),aux.get(position).getHexValue());
        return nf_aux;
    }
  public void delete(int position){
      String NOME_BANCO="notas1.sqlite";
      ArrayList<NotasComFoto> aux = generateBooks();
      aux.addAll(aux);
      NotasComFoto nf_aux = new NotasComFoto(aux.get(position).getName(),aux.get(position).getFoto(),aux.get(position).getHexValue());
      banco = openOrCreateDatabase(NOME_BANCO, MODE_PRIVATE, null);
      banco.execSQL("DELETE FROM notasFotos WHERE titulo=" +"'" +nf_aux.getName()+"' AND texto='"+ nf_aux.getHexValue()+"';");
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





