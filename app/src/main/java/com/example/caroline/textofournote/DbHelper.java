package com.example.caroline.textofournote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by caroline on 03/10/2015.
 */

public class DbHelper extends  SQLiteOpenHelper{

    private static final String DB_NAME= "notascurtas.sqlite";
    private static final int DB_SCHEME_VERSION = 1;

    public DbHelper (Context context){
        super (context, DB_NAME, null, DB_SCHEME_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db2) {
        db2.execSQL(DataBaseManager.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db2, int oldVersion, int newVersion) {

    }
}


