package com.example.caroline.textofournote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by caroline on 03/10/2015.
 */

public class DbHelperTarefas extends  SQLiteOpenHelper{

    private static final String DB_NAME= "tarefas.sqlite";
    private static final int DB_SCHEME_VERSION = 2;

    public DbHelperTarefas (Context context){
        super (context, DB_NAME, null, DB_SCHEME_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {db.execSQL(DataBaseManagerTarefas.CREATE_TABLE);}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


