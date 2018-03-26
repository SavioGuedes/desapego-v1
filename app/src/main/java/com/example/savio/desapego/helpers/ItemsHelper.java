package com.example.savio.desapego.helpers;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import com.example.savio.desapego.adapters.PrincipalAdapter;
import com.example.savio.desapego.api.model.Item;
import com.example.savio.desapego.services.ApiService;
import com.example.savio.desapego.utils.ServiceGenerator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsHelper {

    private final Context context;
    private final AccountManager mAccountManager;
    private ApiService apiService;
    private String authToken;

    public ItemsHelper(Context context) {
        this.context = context;
        mAccountManager = AccountManager.get(context);
    }

    public void userItemsList(final RecyclerView recyclerView) {
        Account[] accounts = mAccountManager.getAccountsByType("com.desapego");
        authToken = mAccountManager.peekAuthToken(accounts[0], "full_access");
        apiService = ServiceGenerator.createService(ApiService.class, authToken);
        apiService.getUserItems().enqueue(new Callback<List<Item>>() {
            //metodos de respostas
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                if (!response.isSuccessful()) {
//                    final Intent res = new Intent(context, LoginActivity.class);
//                    context.startActivity(res);
                    Log.i("LISTA", "Erro: " + "Erro: " + response.code());
                } else {
                    //condição se os dados foram capturados
                    RecyclerView.Adapter itemsListAdapter = new PrincipalAdapter(response.body(), context);
                    recyclerView.setAdapter(itemsListAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(context, "Houve um erro.", Toast.LENGTH_SHORT).show();

                Log.i("LISTA", "Erro: " + t.toString());
            }
        });
    }

    public void itemsList(final RecyclerView recyclerView) {
        apiService = ServiceGenerator.createService(ApiService.class);
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            //metodos de respostas
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                if (!response.isSuccessful()) {
//                    final Intent res = new Intent(context, LoginActivity.class);
//                    context.startActivity(res);
                    Log.i("LISTA", "Erro: " + "Erro: " + response.code());
                } else {
                    //condição se os dados foram capturados
                    RecyclerView.Adapter itemsListAdapter = new PrincipalAdapter(response.body(), context);
                    recyclerView.setAdapter(itemsListAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(context, "Houve um erro.", Toast.LENGTH_SHORT).show();

                Log.i("LISTA", "Erro: " + t.toString());
            }
        });
    }

    public void showItem(final Context context, final Intent intent, String itemId) {
        apiService = ServiceGenerator.createService(ApiService.class);
        apiService.getItem(itemId).enqueue(new Callback<Item>() {
            //metodos de respostas
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {

                if (!response.isSuccessful()) {
//                    final Intent res = new Intent(context, LoginActivity.class);
//                    context.startActivity(res);
                    Log.i("LISTA", "Erro: " + "Erro: " + response.code());
                } else {
                    intent.putExtra("ITEM_NAME", response.body().getName());
                    intent.putExtra("ITEM_DESCRIPTION", response.body().getDescription());
                    intent.putExtra("ITEM_IMG_URL", response.body().getImageUrl());
                    context.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                Toast.makeText(context, "Houve um erro.", Toast.LENGTH_SHORT).show();

                Log.i("LISTA", "Erro: " + t.toString());
            }
        });
    }
}