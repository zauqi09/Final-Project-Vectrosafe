package com.vectrosafe.proyekakhir.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.vectrosafe.proyekakhir.model.Request.TransaksiRequest;
import com.vectrosafe.proyekakhir.model.Response.TransaksiResponse;
import com.vectrosafe.proyekakhir.model.Response.TransaksisResponse;
import com.vectrosafe.proyekakhir.model.Transaksi;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiRepository {
    private static TransaksiRepository pulsaRepository;
    private TransaksiApi pulsaApi;

    public static TransaksiRepository getInstance(){
        if (pulsaRepository == null){
            pulsaRepository = new TransaksiRepository();
        }
        return pulsaRepository;
    }

    public TransaksiRepository(){
        pulsaApi = RetrofitService.cteateService(TransaksiApi.class);
    }
    public MutableLiveData<TransaksiResponse> postPulsa(TransaksiRequest pulsaPayload, String token){
        MutableLiveData<TransaksiResponse> Data = new MutableLiveData<>();
        pulsaApi.postTransaksi(pulsaPayload,token).enqueue(new Callback<TransaksiResponse>() {
            @Override
            public void onResponse(Call<TransaksiResponse> call,
                                   Response<TransaksiResponse> response) {
                if (response.isSuccessful()){
                    Data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TransaksiResponse> call, Throwable t) {
                Data.setValue(null);
            }
        });
        return Data;
    }
    public MutableLiveData<TransaksisResponse> getMutasi(String id_nasabah, String tgl_awal, String tgl_akhir, String token){
        MutableLiveData<TransaksisResponse> Data = new MutableLiveData<>();
        Log.d("debugging", "getMutasi: masuk get mutasi trx repository");
        pulsaApi.getTransaksi(id_nasabah,tgl_awal,tgl_akhir,token).enqueue(new Callback<TransaksisResponse>() {
            @Override
            public void onResponse(Call<TransaksisResponse> call,
                                   Response<TransaksisResponse> response) {
                if (response.isSuccessful()){
                    Data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<TransaksisResponse> call, Throwable t) {
                Data.setValue(null);
            }
        });
        return Data;
    }
}
