package com.vectrosafe.networking;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.vectrosafe.model.GetRequest;
import com.vectrosafe.model.Response.ApiResponse;

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

    public MutableLiveData<ApiResponse> getUser(GetRequest get){
        MutableLiveData<ApiResponse> userData = new MutableLiveData<>();
        Log.d("info", "masuk getUser UserRepo ");
        userApi.getUser(get).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call,
                                   Response<ApiResponse> response) {
                if (response.isSuccessful()){
                    Log.d("info", "masuk isSuccessful ");
                    userData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("info", "masuk getUser UserRepo ");
                userData.setValue(null);
            }
        });
        return userData;
    }
}
