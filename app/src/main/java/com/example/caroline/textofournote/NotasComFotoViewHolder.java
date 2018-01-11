package com.example.caroline.textofournote;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by Carol on 04/12/2015.
 */
public class NotasComFotoViewHolder extends RecyclerView.ViewHolder  {
    protected TextView titleText;
    protected TextView contentText;
    protected ImageView img;
    protected CardView card;




    public NotasComFotoViewHolder(final View itemView) {
        super(itemView);
        titleText = (TextView) itemView.findViewById(R.id.name);
        contentText = (TextView) itemView.findViewById(R.id.hexValue);
        img = (ImageView) itemView.findViewById(R.id.imageView3);
        card = (CardView) itemView;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getAdapterPosition();
                Intent i = new Intent(v.getContext(), VisualizarNotasFotos.class);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("position", position);
                i.putExtras(dataBundle);
                v.getContext().startActivity(i);

            }
        });



    }

}


