package com.vectrosafe.proyekakhir.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.vectrosafe.proyekakhir.model.Response.VoucherResponse;
import com.vectrosafe.proyekakhir.model.Voucher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoucherRepository {
    private static VoucherRepository vouchersRepository;

    public static VoucherRepository getInstance(){
        if (vouchersRepository == null){
            vouchersRepository = new VoucherRepository();
        }
        return vouchersRepository;
    }

    private VoucherApi voucherApi;

    public VoucherRepository(){
        voucherApi = RetrofitService.cteateService(VoucherApi.class);
    }

    public MutableLiveData<VoucherResponse> getXL(){
        MutableLiveData<VoucherResponse> userData = new MutableLiveData<>();
        voucherApi.getXl().enqueue(new Callback<VoucherResponse>() {
            @Override
            public void onResponse(Call<VoucherResponse> call, Response<VoucherResponse> response) {
                if (response.isSuccessful()) {
                    userData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<VoucherResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }

    public MutableLiveData<VoucherResponse> getTsel(){
        MutableLiveData<VoucherResponse> userData = new MutableLiveData<>();
        voucherApi.getTsel().enqueue(new Callback<VoucherResponse>() {
            @Override
            public void onResponse(Call<VoucherResponse> call, Response<VoucherResponse> response) {
                if (response.isSuccessful()) {
                    userData.setValue(response.body());
                    Log.d("Success", "onResponse: "+response.body().getData().get(1).getNama_produk());
                }
            }

            @Override
            public void onFailure(Call<VoucherResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }

}
