package com.c.cristopher.restaurante;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends BaseAdapter {
    private final Activity contexto;
    private final List<Restaurante> restaurantes;

    public CustomListAdapter(Activity contexto, List<Restaurante> restaurantes){
        this.contexto = contexto;
        this.restaurantes = restaurantes;
    }

    @Override
    public int getCount() {
        return restaurantes.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return restaurantes.get(position).getId();
    }

    public View getView(int position, View view, ViewGroup parent){
        Restaurante restaurante = restaurantes.get(position);

        LayoutInflater inflater = LayoutInflater.from(contexto);
        View tela = view;
        if (tela == null) {
            tela = inflater.inflate(R.layout.mylist, null);
        }
        TextView campoNome = tela.findViewById(R.id.tv_restaurante_nome);
        campoNome.setText(restaurante.getNome());

        TextView campoEndereco = tela.findViewById(R.id.tv_restaurante_endereco);
        campoEndereco.setText(restaurante.getEndereco());

        TextView campoTipo = tela.findViewById(R.id.tv_tipo);
        campoTipo.setText(restaurante.getTipo());

        ImageView img = tela.findViewById(R.id.img_lista);
        img.setImageResource(desenhar(restaurante.getTipo()));

        return tela;
    }

    public static int desenhar(String tipo){
        int img;
        if(tipo.equals("Rodízio")){
            img = R.drawable.pizza;
        } else if(tipo.equals("Fast Food")){
            img = R.drawable.fast_food;
        } else{
            img = R.drawable.lunch;
        }
        return img;
    }
}
