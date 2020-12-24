package com.vectrosafe.networking;


import com.vectrosafe.model.ApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserApi {
    @GET("getUser/{operator}-{nomor}")
    Call<ApiResponse> getUser(@Path("operator") String operator,@Path("nomor")String nomor );
}
