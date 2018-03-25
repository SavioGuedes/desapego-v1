package com.example.savio.desapego.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.savio.desapego.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

public class NovoItemAdapter extends RecyclerView.Adapter<NovoItemAdapter.Visao>{

    ArrayList<Bitmap> fotos_galeria;

    public NovoItemAdapter(ArrayList<Bitmap> fotos_galeria){

        this.fotos_galeria = fotos_galeria;
    }

    @Override
    public NovoItemAdapter.Visao onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_novo_item, parent, false);

        Visao visao = new Visao(v);

        return visao;
    }

    @Override
    public void onBindViewHolder(NovoItemAdapter.Visao visao, int position) {

        visao.imagem_galeria.setImageBitmap(fotos_galeria.get(position));
    }

    @Override
    public int getItemCount() {
        return fotos_galeria.size();
    }

    public class Visao extends RecyclerView.ViewHolder{

        RoundedImageView imagem_galeria;

        public Visao(View itemView) {
            super(itemView);

             imagem_galeria = (RoundedImageView) itemView.findViewById(R.id.novo_item_img);
        }
    }

}
