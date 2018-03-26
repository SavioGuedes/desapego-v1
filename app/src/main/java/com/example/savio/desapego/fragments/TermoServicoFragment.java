package com.example.savio.desapego.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.savio.desapego.R;

/**
 * Created by supanonymous on 25/03/18.
 */

public class TermoServicoFragment extends Fragment {

    private TextView termo;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_termo_servico, container, false);
        termo = (TextView) view.findViewById(R.id.termo_servico);

        return view;
    }


}
