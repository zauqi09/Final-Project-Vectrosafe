package com.vectrosafe.proyekakhir.networking;

import com.vectrosafe.proyekakhir.model.Response.VoucherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface VoucherApi {
    @GET("getXL")
    Call<VoucherResponse> getXl();

    @GET("getTelkomsel")
    Call<VoucherResponse> getTsel();
}
