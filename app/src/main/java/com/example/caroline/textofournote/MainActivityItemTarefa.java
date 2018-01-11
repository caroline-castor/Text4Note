package com.example.caroline.textofournote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by caroline on 30/10/2015.
 */

public class MainActivityItemTarefa extends AppCompatActivity implements View.OnClickListener {

    private TextView textTitulo;
    private Button AgregarBt;
    private Button CancelN;
    private DataBaseManagerTarefas manager;
    private CheckBox checkBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_item_tarefa);

        manager = new DataBaseManagerTarefas(this);
        textTitulo = (EditText) findViewById(R.id.TituloN);
        CancelN = (Button) findViewById(R.id.buttonCancelar);
        CancelN.setOnClickListener(this);
        AgregarBt = (Button) findViewById(R.id.buttonAgregar);
        AgregarBt.setOnClickListener(this);

    }

    @Override


    public void onClick(View view) {
        if (view.getId() == R.id.buttonAgregar) {
            if (textTitulo.length() > 0) {
                manager.insertar(textTitulo.getText().toString());
                Toast.makeText(getBaseContext(), "Lista de Tarefa Adionada com Exito", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivityItemTarefa.this, MainActivityListaTarefas.class);
                startActivity(intent);
                finish();
            }
        }

        else
        if (view.getId() == R.id.buttonCancelar)

        {
            Intent intent = new Intent(MainActivityItemTarefa.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main,/*menu_main_activity_insert_n2,*/ menu);
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



