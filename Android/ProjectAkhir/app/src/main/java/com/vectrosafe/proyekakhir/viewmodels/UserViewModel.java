package com.vectrosafe.proyekakhir.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.vectrosafe.proyekakhir.model.Request.RegisterRequest;
import com.vectrosafe.proyekakhir.model.Response.ApiResponse;
import com.vectrosafe.proyekakhir.model.Request.LoginRequest;
import com.vectrosafe.proyekakhir.model.Response.UserResponse;
import com.vectrosafe.proyekakhir.networking.UserRepository;


public class UserViewModel extends ViewModel {
    private UserRepository usersRepository;
    private MutableLiveData<UserResponse> mutableLiveData;
    private MutableLiveData<ApiResponse> mutableLiveDataApiResponse;
    public void init(){
        if (mutableLiveData != null){
            return;
        }
        if (mutableLiveDataApiResponse !=null){
            return;
        }
        usersRepository = UserRepository.getInstance();
        
    }


    public LiveData<UserResponse> postLogin(LoginRequest loginRequest) {
        if (mutableLiveData == null) {
            usersRepository = UserRepository.getInstance();
        }
        mutableLiveData = usersRepository.postLogin(loginRequest);

        return mutableLiveData;
    }

    public LiveData<UserResponse> postRegister(RegisterRequest registerRequest) {
        if (mutableLiveData == null) {
            usersRepository = UserRepository.getInstance();
        }
        mutableLiveData = usersRepository.postRegister(registerRequest);

        return mutableLiveData;
    }

    public LiveData<ApiResponse> getNasabah(String id, String token) {
        if (mutableLiveDataApiResponse == null) {
            usersRepository = UserRepository.getInstance();
        }
        mutableLiveDataApiResponse = usersRepository.getNasabah(id,token);

        return mutableLiveDataApiResponse;
    }


}
