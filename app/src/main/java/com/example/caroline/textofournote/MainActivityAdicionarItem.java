package com.example.caroline.textofournote;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityAdicionarItem extends ActionBarActivity implements View.OnClickListener {

    private TextView conteudoItem;
    private Button AgregarBt;
    private Button CancelN;


    private DataBaseManagerTarefas manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_adicionar_item);

        manager = new DataBaseManagerTarefas(this);
        conteudoItem = (TextView) findViewById(R.id.ConteudoN);
        AgregarBt = (Button) findViewById(R.id.buttonAgregar);
        AgregarBt.setOnClickListener(this);
        CancelN = (Button) findViewById(R.id.buttonCancelar);
        CancelN.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

       if (view.getId() == R.id.buttonAgregar) {
            if (conteudoItem.length() > 0) {
                manager.insertar(conteudoItem.getText().toString());
            // ESTAVA DANDO ERRO, SO ACHEI ESSE METODO DE CIMA NO MANAGER
            //    manager.insertarItem(conteudoItem.getText().toString());
                Toast.makeText(getBaseContext(), "Item de Tarefa Adionada com Exito", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivityAdicionarItem.this, MainActivityItemTarefa.class);
                startActivity(intent);
                finish();
            } else
                Toast.makeText(getBaseContext(), "Insira o Item a Tarefa", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivityAdicionarItem.this, MainActivityItemTarefa.class);
                startActivity(intent);
                finish();
                return;
            }

        else
            if (view.getId() == R.id.buttonCancelar) {
                Intent intent = new Intent(MainActivityAdicionarItem.this, MainActivityItemTarefa.class);
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



