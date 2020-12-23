package com.vectrosafe.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vectrosafe.model.GetRequest;
import com.vectrosafe.model.Response.ApiResponse;
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


    public LiveData<ApiResponse> getUser(GetRequest get) {
        if (mutableLiveData == null) {
            usersRepository = UserRepository.getInstance();
        }
        mutableLiveData = usersRepository.getUser(get);

        return mutableLiveData;
    }
}
