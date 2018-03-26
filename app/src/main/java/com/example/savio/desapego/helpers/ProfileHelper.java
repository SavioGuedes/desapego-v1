package com.example.savio.desapego.helpers;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.example.savio.desapego.R;
import com.example.savio.desapego.api.model.Item;
import com.example.savio.desapego.api.model.Perfil;
import com.example.savio.desapego.services.ApiService;
import com.example.savio.desapego.utils.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileHelper {
    private final Context context;
    private ApiService apiService;
    private AccountManager mAccountManager;
    private List<Item> itemsList;
    private String authToken;
    private Account account;

    public ProfileHelper(Context context){
        this.context = context;
        mAccountManager = AccountManager.get(context);
    }


    public void showProfile(final Fragment fragment){
        Account[] accounts;
        accounts = mAccountManager.getAccountsByType("com.desapego");
        if (accounts.length > 0) {
            authToken = mAccountManager.peekAuthToken(accounts[0], "full_access");
            apiService = ServiceGenerator.createService(ApiService.class, authToken);

            apiService.getProfile().enqueue(new Callback<Perfil>() {
                //metodos de respostas
                @Override
                public void onResponse(Call<Perfil> call, Response<Perfil> response) {
                    //condição se os dados foram capturados
                    if (!response.isSuccessful()) {
                        //                    final Intent res = new Intent(context, LoginActivity.class);
                        //                    context.startActivity(res);
                        Log.i("LISTA", "Erro: " + "Erro: " + response.code());
                    } else {
                        Perfil perfil = response.body();
                        Bundle bundle = new Bundle();
                        bundle.putString("USER_NAME", response.body().getName());
                        bundle.putString("USER_IMG_URL", response.body().getImg_url());
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        //confirmar transição
                        transaction.replace(R.id.inflar, fragment, "perfil").commit();
                    }
                }

                @Override
                public void onFailure(Call<Perfil> call, Throwable t) {

                    Log.i("LISTA", "Erro: " + t.toString());
                }
            });
        }

    }


}