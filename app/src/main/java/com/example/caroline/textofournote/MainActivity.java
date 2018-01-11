package com.example.caroline.textofournote;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by caroline on 30/10/2015.
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button ExitApp;
    private Button AgregarN;
    private Button AgregarN2;
    private Button AgregarN4;
    private Button notasComFoto;
    private Button listarNF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExitApp = (Button)findViewById(R.id.buttonFinish);
        ExitApp.setOnClickListener(this);
        AgregarN = (Button) findViewById(R.id.buttonInsert);
        AgregarN.setOnClickListener(this);
        AgregarN2 = (Button) findViewById(R.id.buttonNotasCriadas);
        AgregarN2.setOnClickListener(this);
        AgregarN4 = (Button) findViewById(R.id.buttonInsert3);
        AgregarN4.setOnClickListener(this);
        AgregarN4 = (Button) findViewById(R.id.buttonListasCriadas);
        AgregarN4.setOnClickListener(this);
        listarNF = (Button) findViewById(R.id.buttonListarNF);
        listarNF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityNotasFoto m = new MainActivityNotasFoto();
                m.criarBanco();
                if(criarBanco()) {

                    Intent i = new Intent(MainActivity.this, MainNotasFotosCriadas.class);
                    startActivity(i);
                }
            }
        });

        notasComFoto = (Button) findViewById(R.id.buttonNotasComFoto);
        notasComFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivityNotasFoto.class);
                startActivity(i);


            }
        });

    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.buttonFinish) {
            //Mostra uma mensagem de confirmação antes de realizar o teste
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
            alertDialog.setMessage("Deseja sair do aplicativo?");
            alertDialog.setTitle("Sair");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    //ação do botão "sim"

                    finish();
                }
            });

            alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            alertDialog.show();
        }

        else
        if(view.getId()==R.id.buttonInsert){
            Intent intent = new Intent(MainActivity.this, MainActivityInsertN.class);
            startActivity(intent);

        }
        else
        if(view.getId()==R.id.buttonInsert3){
            Intent intent = new Intent(MainActivity.this, MainActivityItemTarefa.class);
            startActivity(intent);

        }
        else
        if(view.getId()==R.id.buttonNotasCriadas){
            Intent intent = new Intent(MainActivity.this, MainNotasCriadas.class);
            startActivity(intent);

        }
        else
        if(view.getId()==R.id.buttonListasCriadas){
            Intent intent = new Intent(MainActivity.this, MainActivityListaTarefas.class);
            startActivity(intent);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean criarBanco(){
        boolean aux=true;
        try{
            SQLiteDatabase banco= null;
            String nomeBanco = "notas1.sqlite";
            banco = openOrCreateDatabase(nomeBanco,MODE_PRIVATE,null);
            banco.execSQL("CREATE TABLE IF NOT EXISTS notasFotos (_id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, foto TEXT, texto TEXT);");
            Log.d("Sucesso", "Bd criado");


        }catch (Exception e){
            aux=false;

        }
        return aux;
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



