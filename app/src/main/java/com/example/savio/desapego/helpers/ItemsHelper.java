package com.example.savio.desapego.helpers;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.example.savio.desapego.DetalheActivity;
import com.example.savio.desapego.NovoItemActivity;
import com.example.savio.desapego.R;
import com.example.savio.desapego.adapters.PrincipalAdapter;
import com.example.savio.desapego.api.model.Fotinha;
import com.example.savio.desapego.api.model.Item;
import com.example.savio.desapego.services.ApiService;
import com.example.savio.desapego.utils.ServiceGenerator;
import com.example.savio.desapego.views.HorizontalDottedProgress;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemsHelper {

    private final Context context;
    private final AccountManager mAccountManager;
    private ApiService apiService;
    private String authToken;
    private Intent intent;

    public ItemsHelper(Context context) {
        this.context = context;
        mAccountManager = AccountManager.get(context);
    }

    public void userItemsList(final List<Item> itemsList, final RecyclerView.Adapter adapter) {
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
                    HorizontalDottedProgress loadingBar = (HorizontalDottedProgress) ((Activity) context).findViewById(R.id.loading_bar);
                    itemsList.addAll(response.body());
                    //Atualiza o adapter com os items da api
                    adapter.notifyDataSetChanged();
                    loadingBar.clearAnimation();
                    loadingBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(context, "Houve um erro.", Toast.LENGTH_SHORT).show();

                Log.i("LISTA", "Erro: " + t.toString());
            }
        });
    }

    public void itemsList(final List<Item> itemsList, final RecyclerView.Adapter adapter) {
        apiService = ServiceGenerator.createService(ApiService.class);
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            //metodos de respostas
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {

                if (!response.isSuccessful()) {
                    Log.i("LISTA", "Erro: " + "Erro: " + response.code());
                } else {
                    //condição se os dados foram capturados

                    HorizontalDottedProgress loadingBar = (HorizontalDottedProgress) ((Activity) context).findViewById(R.id.loading_bar);
                    itemsList.addAll(response.body());
                    //Atualiza o adapter com os items da api
                    adapter.notifyDataSetChanged();
                    loadingBar.clearAnimation();
                    loadingBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Toast.makeText(context, "Houve um erro.", Toast.LENGTH_SHORT).show();

                Log.i("LISTA", "Erro: " + t.toString());
            }
        });
    }

    public void showItem(final Intent intent, String itemId) {
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
                    intent.putExtra("ITEM_IMAGE_DETAIL", response.body().getFotinhas().get(0).getUrl());
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

    public void newItem(final StorageReference storageRef, final ArrayList<Uri> arrayUri, final Item item) {
        final ArrayList<Uri> downloadUrls = new ArrayList<>();
        StorageReference itemImageRef;
        UploadTask uploadTask;
        //Envia as imagens pro firebase
        for (int i = 0; i < arrayUri.size(); i++) {
            itemImageRef = storageRef.child("item/images/" + arrayUri.get(i).getLastPathSegment());
            uploadTask = itemImageRef.putFile(arrayUri.get(i));
            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(context, "Falha ao enviar um dos arquivos, operação cancelada", Toast.LENGTH_SHORT).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    downloadUrls.add(taskSnapshot.getDownloadUrl());
                    //Envia o novo item pra api com suas respectivas urls das imagens
                    if (downloadUrls.size() > 0) {
                        Fotinha fotinha;
                        List<Fotinha> fotinhas = new ArrayList<>();
                        for (Uri fotinhaUrl:
                                downloadUrls) {
                            fotinha = new Fotinha();
                            fotinha.setUrl(fotinhaUrl.toString());
                            fotinhas.add(fotinha);
                        }
                        item.setFotinhas(fotinhas);
                        createItem(item);
                    }

                }
            });

        }

    }

    private void createItem(final Item item){
        Account[] accounts;
        accounts = mAccountManager.getAccountsByType("com.desapego");
        authToken = mAccountManager.peekAuthToken(accounts[0], "full_access");
        apiService = ServiceGenerator.createService(ApiService.class, authToken);
        apiService.createItem(item).enqueue(new Callback<Item>() {
            //metodos de respostas
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {
                if (!response.isSuccessful()) {
//                    final Intent res = new Intent(context, LoginActivity.class);
//                    context.startActivity(res);
                    Log.i("CREATE_ITEM","Erro: " + response.code());
                } else {
                    intent = new Intent(context, DetalheActivity.class);
                    intent.putExtra("ITEM_NAME", response.body().getName());
                    intent.putExtra("ITEM_DESCRIPTION", response.body().getDescription());
                    intent.putExtra("ITEM_IMAGE_DETAIL", response.body().getFotinhas().get(0).getUrl());
                    context.startActivity(intent);
                    ((Activity)context).finish();
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