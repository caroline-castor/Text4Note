package com.example.caroline.textofournote;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by caroline on 30/10/2015.
 */

public class MainNotasCriadas extends AppCompatActivity implements View.OnClickListener {

    private DataBaseManager manager;
    private Cursor cursor;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private TextView CampText;
    private ImageButton buscarNota;
    private ImageButton DeleteN;
    private Button CancelN;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notas_criadas);

        manager = new DataBaseManager(this);
        lista = (ListView) findViewById(R.id.listView);

        CampText = (TextView) findViewById(R.id.editText);
        buscarNota = (ImageButton) findViewById(R.id.imageButton);
        buscarNota.setOnClickListener(this);
        DeleteN = (ImageButton) findViewById(R.id.buttonEliminar);
        DeleteN.setOnClickListener(this);
        CancelN = (Button) findViewById(R.id.buttonCancelar);
        CancelN.setOnClickListener(this);

            CampText.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                    // When user changed the Text
                    MainNotasCriadas.this.adapter.getFilter().filter(cs);
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }


                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        String[] from = new String[]{manager.CN_TITLE,manager.CN_CONTEND};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        cursor = manager.carregarCursorNotas();
        adapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item, cursor,from , to, 0);
        lista.setAdapter(adapter);
        }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonCancelar)
        {
                Intent intent = new Intent(MainNotasCriadas.this, MainActivity.class);
                startActivity(intent);
                finish();
        }
        else
        if (view.getId() == R.id.imageButton) {

                 if (CampText.length() > 0) {
                         Cursor c = manager.buscarNota(CampText.getText().toString());
                        adapter.changeCursor(c);
                } else
                        Toast.makeText(getBaseContext(), "Escrever um valor de busca", Toast.LENGTH_SHORT).show();
                return;
        }
        else
            if (view.getId() == R.id.buttonEliminar) {
                if (CampText.length() > 0) {
                        //Mostra uma mensagem de confirmação antes de realizar o teste
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainNotasCriadas.this);
                        alertDialog.setMessage("Deseja eliminar a nota?");
                        alertDialog.setTitle("Eliminar nota");
                         alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                        alertDialog.setCancelable(false);
                        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                        manager.eliminar(CampText.getText().toString());
                                        Toast.makeText(getBaseContext(), "Nota Eliminada com Exito", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainNotasCriadas.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                        }
        }
        );

                        alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText(getBaseContext(), "Nota Eliminada com Exito", Toast.LENGTH_SHORT).show();
                        return;
                        }
                        });
                alertDialog.show();
        //-----------------------------------------------------------------

                }
                else
                Toast.makeText(getBaseContext(), "Coloque um valor", Toast.LENGTH_SHORT).show();
                return;
        }


        }

        @Override
                public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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



