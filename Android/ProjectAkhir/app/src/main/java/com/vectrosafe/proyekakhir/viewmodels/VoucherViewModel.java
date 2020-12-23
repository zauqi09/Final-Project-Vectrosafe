package com.vectrosafe.proyekakhir.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vectrosafe.proyekakhir.model.Response.VoucherResponse;
import com.vectrosafe.proyekakhir.networking.VoucherRepository;


public class VoucherViewModel extends ViewModel {
    private MutableLiveData<VoucherResponse> mutableLiveData;
    private VoucherRepository productsRepository;
    public void init(){
        if (mutableLiveData != null){
            return;
        }
        productsRepository = VoucherRepository.getInstance();
    }

    public LiveData<VoucherResponse> getXL() {
        return mutableLiveData;
    }
    public void refreshXL(){
        if (mutableLiveData != null){
            mutableLiveData = productsRepository.getXL();
            return;
        }
        productsRepository = VoucherRepository.getInstance();
        mutableLiveData = productsRepository.getXL();
    }

    public void refreshTSEL(){
        if (mutableLiveData != null){
            mutableLiveData = productsRepository.getTsel();
            return;
        }
        productsRepository = VoucherRepository.getInstance();
        mutableLiveData = productsRepository.getTsel();
    }

    public LiveData<VoucherResponse> getTsel() {
        return mutableLiveData;
    }

}