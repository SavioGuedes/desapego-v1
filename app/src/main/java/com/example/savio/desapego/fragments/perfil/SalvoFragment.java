package com.example.savio.desapego.fragments.perfil;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.savio.desapego.MainActivity;
import com.example.savio.desapego.R;
import com.example.savio.desapego.adapters.PrincipalAdapter;
import com.example.savio.desapego.api.model.Item;
import com.example.savio.desapego.fragments.PerfilFragment;
import com.example.savio.desapego.helpers.ItemsHelper;

import java.util.ArrayList;
import java.util.List;


public class SalvoFragment extends Fragment {
    PerfilFragment perfilFragment;
    FragmentManager fragmentManager;

    RecyclerView recyclerview;
    RecyclerView.LayoutManager layoutmanager;
    RecyclerView.Adapter adapter;

    private ItemsHelper itemsHelper;
    private List<Item> listItems;


    public SalvoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_salvo, container, false);
        itemsHelper = new ItemsHelper(getContext());
        listItems = new ArrayList<>();
        //capitura o fragment pai "PerfilFragment"
        fragmentManager = getFragmentManager();
        perfilFragment = (PerfilFragment) fragmentManager.findFragmentByTag("perfil");


//----------------------------configuração do recyclerview------------------------------------------//


        recyclerview = (RecyclerView) view.findViewById(R.id.perfil_salvos_lista);
        recyclerview.setHasFixedSize(true);

        layoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerview.setLayoutManager(layoutmanager);

        itemsHelper = new ItemsHelper(getActivity());
        PrincipalAdapter itemsListAdapter = new PrincipalAdapter(listItems, getContext());
        recyclerview.setAdapter(itemsListAdapter);
//        Pega os items da api
        itemsHelper.userItemsList(listItems, itemsListAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(!perfilFragment.paused){

            //muda o tab a para a cor tema do app
            ((MainActivity) getActivity()).mudarCorTextoTab(perfilFragment.salvos, R.color.Principal);
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        //muda o tab a para a cinza
        ((MainActivity) getActivity()).mudarCorTextoTab(perfilFragment.salvos, R.color.cinza);
    }


//------------------Para facilitar minha vida-------------------------------------------------------//


}
