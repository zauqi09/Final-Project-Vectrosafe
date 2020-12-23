package com.vectrosafe.proyekakhir.networking;

import androidx.lifecycle.MutableLiveData;

import com.vectrosafe.proyekakhir.model.Response.ApiResponse;
import com.vectrosafe.proyekakhir.model.Request.LoginRequest;
import com.vectrosafe.proyekakhir.model.Request.RegisterRequest;
import com.vectrosafe.proyekakhir.model.Response.UserResponse;
import com.vectrosafe.proyekakhir.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private static UserRepository usersRepository;

    public static UserRepository getInstance(){
        if (usersRepository == null){
            usersRepository = new UserRepository();
        }
        return usersRepository;
    }

    private UserApi userApi;

    public UserRepository(){
        userApi = RetrofitService.cteateService(UserApi.class);
    }

    public MutableLiveData<UserResponse> postLogin(LoginRequest loginRequest){
        MutableLiveData<UserResponse> userData = new MutableLiveData<>();
        userApi.postLogin(loginRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call,
                                   Response<UserResponse> response) {
                if (response.isSuccessful()){
                    userData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }

    public MutableLiveData<UserResponse> postRegister(RegisterRequest registerRequest){
        MutableLiveData<UserResponse> userData = new MutableLiveData<>();
        userApi.postRegister(registerRequest).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call,
                                   Response<UserResponse> response) {
                if (response.isSuccessful()){
                    userData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                userData.setValue(null);
            }
        });
        return userData;
    }
    public MutableLiveData<ApiResponse> getNasabah(String id, String token){
        MutableLiveData<ApiResponse> nasabahData = new MutableLiveData<>();
        userApi.getNasabah(id,token).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call,
                                   Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    nasabahData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                nasabahData.setValue(null);
            }
        });
        return nasabahData;
    }

}
