package com.example.caroline.textofournote;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.app.AlertDialog;
import android.content.DialogInterface;

import java.util.ArrayList;

/**
 * Created by caroline on 30/10/2015.
 */

public class MainActivityListaTarefas extends AppCompatActivity implements View.OnClickListener {

    MyCustomAdapter dataAdapter = null;
    private DataBaseManagerTarefas manager;
    private Cursor cursor;
    private ListView lista;
    private SimpleCursorAdapter adapter;
    private TextView CampText;
    private ImageButton buscarNota;
    private ImageButton DeleteN;
    private Button CancelN;
    private CheckBox check;
    private Context context;
    ArrayList<Tarefa> tarefaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_lista_tarefas);

        manager = new DataBaseManagerTarefas(this);
        lista = (ListView) findViewById(R.id.listView);
        ArrayList<Tarefa> tarefaList;
        displayTarefaList();

        //ItemTarefaAdapter plTarefa;
        CampText = (TextView) findViewById(R.id.edtTitulo);
        buscarNota = (ImageButton) findViewById(R.id.imageButton);
        buscarNota.setOnClickListener(this);
        DeleteN = (ImageButton) findViewById(R.id.buttonEliminar);
        DeleteN.setOnClickListener(this);
        CancelN = (Button) findViewById(R.id.buttonCancelar);
        CancelN.setOnClickListener(this);


        String[] from = new String[]{manager.CN_TITLE, manager.CN_CONTEND};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        //cursor = manager.carregarCursorNotas();
        ArrayList<Tarefa> vTarefa = new ArrayList();
        vTarefa.addAll(manager.carregarNotas());

        //adapter = new SimpleCursorAdapter(this, R.layout.tarefas_list, cursor, from, to, 0);
        //adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_checked, cursor, from, to, 0);
        dataAdapter = new MyCustomAdapter(this, R.layout.tarefas_list, vTarefa);

        lista.setAdapter(dataAdapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tarefa oTarefa = (Tarefa) parent.getItemAtPosition(position);

                Toast.makeText(getBaseContext(), "CLICK " + oTarefa.getTitulo(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayTarefaList(){
       tarefaList = new ArrayList<Tarefa>();
        //ItemTarefaAdapter plAdapter = new ItemTarefaAdapter(tarefaList, context);
        ListView lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(dataAdapter);
    }

    public void onChechedChaged(CompoundButton buttonView, boolean isChecked){
        int pos = lista.getPositionForView(buttonView);
        if(pos != ListView.INVALID_POSITION){
            Tarefa t  = tarefaList.get(pos);
           // t = setSelected (isChecked);

            Toast.makeText(this, "Clicou no Item: " + t.Titulo + isChecked, Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonCancelar) {
            Intent intent = new Intent(MainActivityListaTarefas.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.imageButton) {
            if (CampText.length() > 0) {
                Cursor c = manager.buscarNota(CampText.getText().toString());
                adapter.changeCursor(c);
            }
            return;
        } else if (view.getId() == R.id.buttonEliminar) {
            if (CampText.length() > 0) {
                //Mostra uma mensagem de confirmação antes de realizar o teste
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivityListaTarefas.this);
                alertDialog.setMessage("Deseja eliminar a nota?");
                alertDialog.setTitle("Eliminar nota");
                alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alertDialog.setCancelable(false);
                alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                manager.eliminar(CampText.getText().toString());
                                Toast.makeText(getBaseContext(), "Nota Eliminada com Exito", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivityListaTarefas.this, MainActivity.class);
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

            } else
                Toast.makeText(getBaseContext(), "Coloque um valor", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    private class MyCustomAdapter extends ArrayAdapter<Tarefa> {

        private ArrayList<Tarefa> tarefasList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               ArrayList<Tarefa> tarefasList) {
            super(context, textViewResourceId, tarefasList);
            this.tarefasList = new ArrayList<Tarefa>();
            this.tarefasList.addAll(tarefasList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.tarefas_list, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.code);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        Tarefa tar = (Tarefa) cb.getTag();

                        //UTILIZAR O CLICK DO CHECK BOX NESSE MOMENTO


                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        tar.setSelected(cb.isChecked());
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            Tarefa tar = tarefasList.get(position);
            holder.code.setText(tar.getTitulo());
            holder.name.setText(tar.getTitulo());
            holder.name.setChecked(tar.isSelected());
            holder.name.setTag(tar);

            return convertView;

        }
    }
}

