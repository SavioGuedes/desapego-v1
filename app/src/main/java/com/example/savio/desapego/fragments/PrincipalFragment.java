package com.example.savio.desapego.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.savio.desapego.R;
import com.example.savio.desapego.helpers.ItemsHelper;

public class PrincipalFragment extends Fragment {
    RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    CardView mulher, homem, animal, infantil, eletronico, livro, lazer, esporte, casa, outros;
    private ItemsHelper itemsHelper;
    public PrincipalFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_principal, container, false);
        mulher = (CardView) view.findViewById(R.id.principal_categoria_mulher);
        homem = (CardView) view.findViewById(R.id.principal_categoria_homem);
        animal = (CardView) view.findViewById(R.id.principal_categoria_animal);
        infantil = (CardView) view.findViewById(R.id.principal_categoria_infantil);
        eletronico = (CardView) view.findViewById(R.id.principal_categoria_eletronico);
        livro = (CardView) view.findViewById(R.id.principal_categoria_livro);
        lazer = (CardView) view.findViewById(R.id.principal_categoria_lazer);
        esporte = (CardView) view.findViewById(R.id.principal_categoria_esporte);
        casa = (CardView) view.findViewById(R.id.principal_categoria_casa);
        outros = (CardView) view.findViewById(R.id.principal_categoria_outros);

//----------------------------configuração do recyclerview------------------------------------------//

        recyclerview = (RecyclerView) view.findViewById(R.id.principal_lista);
        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(layoutManager);
        itemsHelper = new ItemsHelper(getContext());
//        Pega os items da api
        itemsHelper.itemsList(recyclerview);

//------------------listener clique na categoria----------------------------------------------------//

        cliqueCard(mulher, "mulher");cliqueCard(homem, "homem");cliqueCard(animal, "animal");
        cliqueCard(infantil, "infantil");cliqueCard(eletronico, "eletronico");
        cliqueCard(livro, "livro");cliqueCard(lazer, "lazer");cliqueCard(esporte, "esporte");
        cliqueCard(casa, "casa");cliqueCard(outros, "outros");
        return view;
    }

//------------------Para facilitar minha vida-------------------------------------------------------//

    //infla o fragment apertado no item do menu
    private void cliqueCard(final CardView cardView, final String key){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(), key, Toast.LENGTH_LONG).show();
            }

        });
    }
}