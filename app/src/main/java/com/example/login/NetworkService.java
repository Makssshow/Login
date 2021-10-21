package com.example.login;

import android.view.LayoutInflater;

import com.example.login.interfaces.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "https://dev-6bzrae6x.us.auth0.com/api/v2/";
    Retrofit retrofit;

    public NetworkService() {
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public APIService getJsonAPI() {
        return retrofit.create(APIService.class);
    }
}
