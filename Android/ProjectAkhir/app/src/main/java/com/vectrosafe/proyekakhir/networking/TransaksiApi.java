package com.vectrosafe.proyekakhir.networking;

import com.vectrosafe.proyekakhir.model.Request.LoginRequest;
import com.vectrosafe.proyekakhir.model.Request.TransaksiRequest;
import com.vectrosafe.proyekakhir.model.Response.TransaksiResponse;
import com.vectrosafe.proyekakhir.model.Response.TransaksisResponse;
import com.vectrosafe.proyekakhir.model.Response.UserResponse;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TransaksiApi {
    @POST("buy_VoucherPrabayar")
    Call<TransaksiResponse> postTransaksi(@Body TransaksiRequest body, @Header("Authorization") String token);
    @GET("trx/{id_nasabah}-from{tgl_awal}-to{tgl_akhir}")
    Call<TransaksisResponse> getTransaksi(@Path("id_nasabah") String id_nasabah,
                                          @Path("tgl_awal") String tgl_awal,
                                          @Path("tgl_akhir") String tgl_akhir,
                                          @Header("Authorization") String token);
}
