package com.example.savio.desapego;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.savio.desapego.fragments.PerfilFragment;
import com.example.savio.desapego.fragments.PesquisaFragment;
import com.example.savio.desapego.fragments.PrincipalFragment;
import com.example.savio.desapego.helpers.AuthHelper;
import com.example.savio.desapego.helpers.ProfileHelper;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    ImageView ic_pesquisa, ic_principal, ic_perfil;
    private StorageReference mStorageRef;
    private FloatingActionButton btn_novo;
    public static final int RESULT_GALLERY = 0;
    private String extra = "";
    private ProfileHelper profileHelper;
    private AuthHelper authHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authHelper = new AuthHelper();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        ic_pesquisa = (ImageView) findViewById(R.id.menu_pesquisa);
        ic_principal = (ImageView) findViewById(R.id.menu_principal);
        ic_perfil = (ImageView) findViewById(R.id.menu_perfil);

        //infla a tela principal ao inicar o app
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.inflar, new PrincipalFragment(), "principal").commit();


//------------------Listeners do menu----------------------------------------------------------------//

        cliqueItemMenu(ic_pesquisa, new PesquisaFragment(), "pesquisa");
        cliqueItemMenu(ic_principal, new PrincipalFragment(), "principal");
        cliqueItemMenu(ic_perfil, new PerfilFragment(), "perfil");

//------------------fim do onCreate-----------------------------------------------------------------//

    }


//------------------Para facilitar minha vida-------------------------------------------------------//


    //infla o fragment apertado no item do menu
    private void cliqueItemMenu(final ImageView imageView, final Fragment fragment, final String key) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (key) {
                    case "perfil":
                        //Se houver usuario adeus main activity... olá login activity;
                        if (authHelper.isAnyUser(v.getContext())) {
                            profileHelper = new ProfileHelper(MainActivity.this);
                            profileHelper.showProfile(fragment);
                        }else { // Se não houver usuario chama login

                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            //            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        break;
                    default:
                        fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        //confirmar transição
                        transaction.replace(R.id.inflar, fragment, key).commit();
                        break;
                }
            }
        });
    }

    //chamado no fragment para mudar a cor do icone selecionado
    @SuppressLint("NewApi")
    public void mudarCorIcone(ImageView imageView, int color) {

        imageView.setColorFilter(ContextCompat.getColor(MainActivity.this, color));
    }

    //chamado no tabs do PerfilFragment para mudar a cor do texto
    public void mudarCorTextoTab(TextView textView, int color) {

        textView.setTextColor(getResources().getColor(color));
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    //------------------fim do código-------------------------------------------------------------------//

}