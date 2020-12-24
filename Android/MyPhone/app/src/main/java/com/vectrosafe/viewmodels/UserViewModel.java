package com.vectrosafe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vectrosafe.model.ApiResponse;
import com.vectrosafe.networking.UserRepository;


public class UserViewModel extends ViewModel {
    private UserRepository usersRepository;
    private MutableLiveData<ApiResponse> mutableLiveData;
    public void init(){
        if (mutableLiveData != null){
            return;
        }
        usersRepository = UserRepository.getInstance();
        
    }


    public LiveData<ApiResponse> getUser(String no_hp, String operator) {
        if (mutableLiveData == null) {
            usersRepository = UserRepository.getInstance();
        }
        mutableLiveData = usersRepository.getUser(no_hp,operator);

        return mutableLiveData;
    }
}
