package com.vectrosafe.proyekakhir.networking;


import com.vectrosafe.proyekakhir.model.Response.ApiResponse;
import com.vectrosafe.proyekakhir.model.Request.LoginRequest;
import com.vectrosafe.proyekakhir.model.Request.RegisterRequest;
import com.vectrosafe.proyekakhir.model.Response.UserResponse;
import com.vectrosafe.proyekakhir.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @POST("login")
    Call<UserResponse> postLogin(@Body LoginRequest body);

    @GET("nasabah/{id}")
    Call<ApiResponse> getNasabah(@Path("id") String id, @Header("Authorization") String token);

    @POST("register")
    Call<UserResponse> postRegister(@Body RegisterRequest body);
}
