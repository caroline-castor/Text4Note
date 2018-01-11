package com.example.caroline.textofournote;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityInsertN extends ActionBarActivity implements View.OnClickListener {

    private TextView textTitulo;
    private TextView textCont;
    private FloatingActionButton CancelN;
    private FloatingActionButton AgregarBt;

    private DataBaseManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_insert_n);

        manager = new DataBaseManager(this);
        textTitulo = (TextView) findViewById(R.id.TituloN);
        textCont = (TextView) findViewById(R.id.ConteudoN);
        AgregarBt = (FloatingActionButton) findViewById(R.id.buttonAgregar);
        AgregarBt.setOnClickListener(this);
        CancelN = (FloatingActionButton) findViewById(R.id.buttonCancelar);
        CancelN.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.buttonAgregar) {
            if(textTitulo.length()>0) {

                manager.insertar(textTitulo.getText().toString(), textCont.getText().toString());
                Toast.makeText(getBaseContext(), "Nota Adionada com Exito", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivityInsertN.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else Toast.makeText(getBaseContext(), "Insira o Titulo", Toast.LENGTH_SHORT).show();
            return;
        }

        else
        if (view.getId() == R.id.buttonCancelar)

        {
            Intent intent = new Intent(MainActivityInsertN.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main,/*menu_main_activity_insert_n,*/ menu);
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



