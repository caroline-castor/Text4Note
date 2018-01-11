package com.example.caroline.textofournote;

/**
 * Created by caroline on 06/12/2015.
 */
public class Tarefa {
            String Titulo;
        boolean selected = false;

        public Tarefa(){
        }

        public Tarefa(String Titulo){
            super();
            this.Titulo = Titulo;
        }

        public String getTitulo(){
            return Titulo;
        }

        public void setTitulo(String Titulo){
            this.Titulo = Titulo;
        }

        public boolean isSelected(){
            return selected;
        }

        public void setSelected(boolean selected){

            this.selected = selected;
        }
    }
