package com.vectrosafe.networking;


import com.vectrosafe.model.GetRequest;
import com.vectrosafe.model.Response.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
    @POST("operatorpulsa/getUser")
    Call<ApiResponse> getUser(@Body GetRequest get);
}
