package com.example.savio.desapego;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;

import com.example.savio.desapego.helpers.AuthHelper;

public class ConfiguracaoActivity extends AppCompatActivity {

    ImageView btn_voltar;
    CardView btn_sair;

    AuthHelper authHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        btn_voltar = (ImageView) findViewById(R.id.config_voltar);

        btn_sair = (CardView) findViewById(R.id.config_item_sair);

        btn_sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //chama a função de logout
                authHelper.logOut(getApplicationContext());
            }
        });
    }
}
