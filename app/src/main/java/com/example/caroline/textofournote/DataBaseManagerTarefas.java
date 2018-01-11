package com.example.caroline.textofournote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by caroline on 03/10/2015.
 */
public class DataBaseManagerTarefas {
    public static final String TABLE_NAME = "notas";
    public static final String CN_ID = "id";
    public static final String CN_TITLE = "titulo";
    public static final String CN_CONTEND = "nota";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + CN_ID + " integer primary key autoincrement,"
            + CN_TITLE + " text not null, "
            + CN_CONTEND + " text"
            + ")";


    private DbHelperTarefas helper;
    private SQLiteDatabase db;

    public DataBaseManagerTarefas(Context context){
        helper = new DbHelperTarefas(context);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues (String titulo){
        ContentValues valores = new ContentValues();
        valores.put(CN_TITLE, titulo);



        return valores;
    }

    public void insertar(String titulo){
        db.insert(TABLE_NAME, null, generarContentValues(titulo));
    }

    public void eliminar(String titulo){
        db.delete(TABLE_NAME, CN_TITLE + "=?", new String[]{titulo});
    }

    public Cursor carregarCursorNotas(){
        String[] colunas = new String[]{CN_ID+" _id",CN_TITLE,CN_CONTEND};
        return db.query (TABLE_NAME,colunas,null, null, null, null, null);

    }

    public ArrayList<Tarefa> carregarNotas(){
        ArrayList<Tarefa> vTarefa = new ArrayList();

        Cursor c = carregarCursorNotas();

        for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            Tarefa oTarefa = new Tarefa();
            oTarefa.setTitulo(c.getString(1));

            vTarefa.add(oTarefa);
        }

        return vTarefa;
    }

    public Cursor buscarNota(String titulo){
        String[] colunas = new String[]{CN_ID+" _id",CN_TITLE,CN_CONTEND};
        return db.query(TABLE_NAME, colunas, CN_TITLE+"=?", new String[]{titulo}, null, null, null);
    }





}