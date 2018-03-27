package com.example.savio.desapego.services;

import com.example.savio.desapego.api.model.Item;
import com.example.savio.desapego.api.model.Login;
import com.example.savio.desapego.api.model.LoginResponse;
import com.example.savio.desapego.api.model.Perfil;
import com.example.savio.desapego.api.model.Register;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("users")
    Call<LoginResponse> createUser(@Body Register register);

    @POST("users/login")
    Call<LoginResponse> login(@Body Login user_login);

    @POST("users/login")
    Call<String> refreshAccessToken(@Body String user_token);

    @GET("inventory")
    Call<List<Item>> getItems();

    @GET("perfil/inventory")
    Call<List<Item>> getUserItems();

    @GET("perfil")
    Call<Perfil> getProfile();

    @GET("inventory/{id}")
    Call<Item> getItem(@Path("id") String itemId);

    @POST("users/verify_user")
    Call<LoginResponse> verifyUser(@Body Login login);

    @POST("inventory")
    Call<Item> createItem(@Body Item item);
}
