package com.example.caroline.textofournote;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

/**
 * Created by Carol on 27/11/2015.
 */
public class ViewImage extends Activity{
    ImageView imageView;

    public void OnCreate(Bundle savedInnstanceState){
        super.onCreate(savedInnstanceState);
        setContentView(R.layout.view_image);
        Intent i = getIntent();
        String filepath = i.getStringExtra("filepath");

        imageView = (ImageView)findViewById(R.id.fullImageView);
        Bitmap bmp = BitmapFactory.decodeFile(filepath);
        imageView.setImageBitmap(bmp);
        startActivity(i);

    }

}
