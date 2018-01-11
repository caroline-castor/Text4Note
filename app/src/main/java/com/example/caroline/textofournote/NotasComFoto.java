package com.example.caroline.textofournote;

import android.graphics.Bitmap;

/**
 * Created by Carol on 04/12/2015.
 */
public class NotasComFoto {
    private String name;
    private String hexValue;
    private Bitmap foto;

    public NotasComFoto(String name, Bitmap foto, String hexValue) {
        this.name = name;
        this.hexValue = hexValue;
        this.foto=foto;
    }

    public String getName() {
        return name;
    }

    public String getHexValue() {
        return hexValue;
    }

    public Bitmap getFoto() {
        return foto;
    }
}
