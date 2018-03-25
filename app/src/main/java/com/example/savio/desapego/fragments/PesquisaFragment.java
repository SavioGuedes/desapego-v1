package com.example.savio.desapego.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.savio.desapego.MainActivity;
import com.example.savio.desapego.R;

public class PesquisaFragment extends Fragment {

    ImageView pesquisa, linha_doacao, linha_troca, linha_atual;
    TextView pesquisa_tipo_doacao, pesquisa_tipo_troca, tipo_atual;

    String tipo_pesquisa;

    public PesquisaFragment() {
        // Required empty public constructor
    }


//----------------------------ciclo de vida---------------------------------------------------------//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pesquisa, container, false);

        pesquisa = (ImageView) getActivity().findViewById(R.id.menu_pesquisa);

        pesquisa_tipo_doacao = (TextView) view.findViewById(R.id.pesquisa_tipo_doacao);
        pesquisa_tipo_troca = (TextView) view.findViewById(R.id.pesquisa_tipo_troca);
        linha_doacao = (ImageView) view.findViewById(R.id.pesquisa_linha_doacao);
        linha_troca = (ImageView) view.findViewById(R.id.pesquisa_linha_troca);

        tipo_atual = pesquisa_tipo_doacao;
        linha_atual = linha_doacao;

        cliqueTipoPesquisa(pesquisa_tipo_doacao, linha_doacao);
        cliqueTipoPesquisa(pesquisa_tipo_troca, linha_troca);

        pesquisa_tipo_doacao.setClickable(false);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity) getActivity()).mudarCorIcone(pesquisa, R.color.Principal);
    }

    @Override
    public void onPause() {
        super.onPause();

        ((MainActivity) getActivity()).mudarCorIcone(pesquisa, R.color.cinza);
    }


//------------------Para facilitar minha vida-------------------------------------------------------//

    private void cliqueTipoPesquisa(final TextView textView, final ImageView imageView){

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textView.setTextColor(getResources().getColor(R.color.Principal));
                imageView.setBackgroundColor(getResources().getColor(R.color.Principal));

                //o tipo que ficou verde passa para a variavel
                tipo_atual.setTextColor(getResources().getColor(R.color.cinza));
                linha_atual.setBackgroundColor(getResources().getColor(R.color.cinza));

                tipo_atual.setClickable(true);
                textView.setClickable(false);

                tipo_atual = textView;
                linha_atual = imageView;

            }
        });

    }


//----------------------------fim de codigo---------------------------------------------------------//


}
