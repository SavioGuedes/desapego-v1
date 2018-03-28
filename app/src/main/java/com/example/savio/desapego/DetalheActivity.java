package com.example.savio.desapego;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetalheActivity extends AppCompatActivity {

    private TextView titulo;
    private TextView descricao;
    private ImageView imagem_principal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        final Bundle bundle = getIntent().getExtras();
        titulo = (TextView) findViewById(R.id.detalhe_titulo);
        descricao = (TextView) findViewById(R.id.detalhe_descricao);
        imagem_principal = (ImageView) findViewById(R.id.detalhe_imagem);
        titulo.setText(bundle.getString("ITEM_NAME"));
        descricao.setText(bundle.getString("ITEM_DESCRIPTION"));
        Picasso.get().load(bundle.getString("ITEM_IMAGE_DETAIL")).into(imagem_principal);
    }
}
