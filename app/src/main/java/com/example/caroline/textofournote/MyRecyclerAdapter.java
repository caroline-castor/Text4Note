package com.example.caroline.textofournote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carol on 04/12/2015.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<NotasComFotoViewHolder> {

    public List<NotasComFoto> palettes;
    private int position;
    static Context context;
    ContextMenu.ContextMenuInfo info;

    public MyRecyclerAdapter(List<NotasComFoto> palettes, Context context) {
        this.palettes = new ArrayList<>();
        this.palettes.addAll(palettes);
        this.context=context;
    }
    public MyRecyclerAdapter(List<NotasComFoto> palettes) {
        this.palettes = new ArrayList<>();
        this.palettes.addAll(palettes);
    }

    @Override
    public NotasComFotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view, viewGroup, false);
        NotasComFotoViewHolder viewHolder = new NotasComFotoViewHolder( itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NotasComFotoViewHolder paletteViewHolder, final int i) {
        final NotasComFoto palette = palettes.get(i);
        paletteViewHolder.titleText.setText(palette.getName());
        paletteViewHolder.contentText.setText(palette.getHexValue());
        paletteViewHolder.img.setImageBitmap(palette.getFoto ());
        paletteViewHolder.itemView.setLongClickable(true);paletteViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                setPosition(paletteViewHolder.getAdapterPosition());

                return true;
            }
        });
    }



    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int getItemCount() {
        return palettes.size();
    }





}