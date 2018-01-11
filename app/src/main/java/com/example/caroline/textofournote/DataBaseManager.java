package com.example.caroline.textofournote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by caroline on 03/10/2015.
 */
public class DataBaseManager {
    public static final String TABLE_NAME = "notas";
    public static final String CN_ID2 = "id";
    public static final String CN_TITLE = "titulo";
    public static final String CN_CONTEND = "nota";

    public static final String CREATE_TABLE = "create table " + TABLE_NAME + "("
            + CN_ID2 + " integer primary key autoincrement,"
            + CN_TITLE + " text not null, "
            + CN_CONTEND + " text"
            + ")";


    private DbHelper helper2;
    private SQLiteDatabase db2;

    public DataBaseManager(Context context){
        helper2 = new DbHelper(context);
        db2 = helper2.getWritableDatabase();
    }

    public ContentValues generarContentValues (String título, String nota){
        ContentValues valores = new ContentValues();
        valores.put(CN_TITLE,título);
        valores.put(CN_CONTEND, nota);

        return valores;
    }

    public void insertar(String titulo, String nota){
        db2.insert(TABLE_NAME, null, generarContentValues(titulo, nota));
    }

    public void eliminar(String titulo){
        db2.delete(TABLE_NAME, CN_TITLE + "=?", new String[]{titulo});
    }

    public Cursor carregarCursorNotas(){
        String[] colunas = new String[]{CN_ID2+" _id",CN_TITLE,CN_CONTEND};
        return db2.query(TABLE_NAME,colunas,null, null, null, null, null);

    }

    public Cursor buscarNota(String titulo){
        String[] colunas = new String[]{CN_ID2+" _id",CN_TITLE,CN_CONTEND};
        return db2.query(TABLE_NAME, colunas, CN_TITLE+"=?", new String[]{titulo}, null, null,  null);
    }

}