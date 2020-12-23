package com.vectrosafe.proyekakhir.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vectrosafe.proyekakhir.model.Request.TransaksiRequest;
import com.vectrosafe.proyekakhir.model.Response.TransaksiResponse;
import com.vectrosafe.proyekakhir.model.Response.TransaksisResponse;
import com.vectrosafe.proyekakhir.model.Transaksi;
import com.vectrosafe.proyekakhir.networking.TransaksiRepository;
import com.vectrosafe.proyekakhir.networking.VoucherRepository;

import java.util.Date;

public class TransaksiViewModel extends ViewModel {
    private TransaksiRepository pulsaRepository;
    private MutableLiveData<TransaksiResponse> mutableLiveData;
    private MutableLiveData<TransaksisResponse> mutableLiveDatas;
    public void init(){
        if (mutableLiveData != null){
            return;
        }
        pulsaRepository = TransaksiRepository.getInstance();
    }

    public LiveData<TransaksiResponse> postTransaksiRepository(TransaksiRequest pulsaPayload,String token) {
        if (mutableLiveData == null) {
            pulsaRepository = TransaksiRepository.getInstance();
        }
        mutableLiveData = pulsaRepository.postPulsa(pulsaPayload,token);

        return mutableLiveData;
    }

    public void refreshMutasi(String id_nasabah, String tgl_awal, String tgl_akhir, String token){
        if (mutableLiveDatas != null){
            mutableLiveDatas = pulsaRepository.getMutasi(id_nasabah, tgl_awal, tgl_akhir, token);
            return;
        }
        pulsaRepository = TransaksiRepository.getInstance();
        Log.d("debugging", "tgl_awal = "+tgl_awal);
        Log.d("debugging", "tgl_akhir = "+tgl_akhir);
        Log.d("debugging", "refreshMutasi: masuk refresh mutasi transaksi view model ");
        mutableLiveDatas = pulsaRepository.getMutasi(id_nasabah, tgl_awal, tgl_akhir, token);
    }
    public LiveData<TransaksisResponse> getTransaksiRepository() {
        return mutableLiveDatas;
    }
}
