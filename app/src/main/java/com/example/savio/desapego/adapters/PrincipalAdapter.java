package com.example.savio.desapego.adapters;

import com.example.savio.desapego.DetalheActivity;
import com.example.savio.desapego.R;
import com.example.savio.desapego.api.model.Item;
import com.example.savio.desapego.helpers.ItemsHelper;
import com.hendraanggrian.widget.PaginatedRecyclerView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PrincipalAdapter extends RecyclerView.Adapter<PrincipalAdapter.Visao> {

    List<Item> dados;
    Context context;
    private ItemsHelper itemsHelper;

    public PrincipalAdapter(List<Item> dados, Context context) {

        this.dados = dados;
        this.context = context;
    }

    @Override
    public PrincipalAdapter.Visao onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_principal, parent, false);

        Visao visao = new Visao(v);
        return visao;
    }

    @Override
    public void onBindViewHolder(PrincipalAdapter.Visao visao, int position) {

        visao.titulo.setText(dados.get(position).getName());
        if (dados.get(position).getFotinhas().size() > 0)

        Picasso.get().load(dados.get(position).getFotinhas().get(0).getUrl()).into(visao.item_image);//altera o icone

    }

    @Override
    public int getItemCount() {

        return dados.size();
    }

    public class Visao extends RecyclerView.ViewHolder{

        TextView titulo;

        RoundedImageView item_image;

        public Visao(final View itemView) {
            super(itemView);

            titulo = (TextView) itemView.findViewById(R.id.principal_lista_titulo);
            item_image = (RoundedImageView) itemView.findViewById(R.id.principal_lista_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemsHelper = new ItemsHelper(context);
                    Intent intent = new Intent(context, DetalheActivity.class);
                    itemsHelper.showItem(intent, dados.get(getAdapterPosition()).getId());
                }
            });
        }

    }
}
