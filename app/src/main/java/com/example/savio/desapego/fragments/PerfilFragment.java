package com.example.savio.desapego.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.savio.desapego.MainActivity;
import com.example.savio.desapego.NovoItemActivity;
import com.example.savio.desapego.R;
import com.example.savio.desapego.fragments.perfil.PostFragment;
import com.example.savio.desapego.fragments.perfil.SalvoFragment;
import com.example.savio.desapego.helpers.AuthHelper;
import com.squareup.picasso.Picasso;

public class PerfilFragment extends Fragment {

    FragmentManager fragmentManager;
    public TextView posts, salvos;
    ImageView perfil, mais;
    public boolean paused;
    private TextView nome;
    private ImageView img_url;
    AuthHelper authHelper;

    Button perfil_btn_publicar;

    public PerfilFragment() {
        // Required empty public constructor
    }


//----------------------------ciclo de vida---------------------------------------------------------//


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authHelper = new AuthHelper();
//        itemsHelper = new ProfileHelper(MainActivity.this);
//        itemsHelper.showProfile(fragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        Bundle bundle = this.getArguments();
        posts = (TextView) view.findViewById(R.id.perfil_posts);
        salvos = (TextView) view.findViewById(R.id.perfil_salvos);
        perfil = (ImageView) getActivity().findViewById(R.id.menu_perfil);
        mais = (ImageView) view.findViewById(R.id.perfil_mais);
        nome = (TextView) view.findViewById(R.id.perfil_nome);
        img_url = (ImageView) view.findViewById(R.id.perfil_foto);
        perfil_btn_publicar = (Button) view.findViewById(R.id.perfil_btn_publicar);

//        Infos do usuário capturado pela chamada à API e retornado nos parametros de iniciação do fragment
        nome.setText(bundle.getString("USER_NAME"));
        Picasso.get().load(bundle.getString("USER_IMG_URL")).into(img_url);//altera o icone para foto do perfil do facebook
        //infla a o frame com o fragment de posts
        fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.perfil_inflar, new PostFragment(), "posts").commit();

        //botão de mais para ver menu do perfil
        mais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //chama a função de logout
                authHelper.logOut(getContext());
            }
        });

//------------------Listeners do tabs---------------------------------------------------------------//

        cliqueItemMenu(posts, new PostFragment(), "posts");
        cliqueItemMenu(salvos, new SalvoFragment(), "salvos");

        perfil_btn_publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), NovoItemActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        paused = false;
        ((MainActivity) getActivity()).mudarCorIcone(perfil, R.color.Principal);
    }

    @Override
    public void onPause() {
        super.onPause();
        paused = true;
        ((MainActivity) getActivity()).mudarCorIcone(perfil, R.color.cinza);
    }

//------------------Para facilitar minha vida-------------------------------------------------------//

    //infla o fragment apertado no item do menu
    private void cliqueItemMenu(final TextView textView, final Fragment fragment, final String key){

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                //confirmar transição
                transaction.replace(R.id.perfil_inflar, fragment, key).commit();
            }
        });
    }
//----------------------------fim de codigo---------------------------------------------------------//
}