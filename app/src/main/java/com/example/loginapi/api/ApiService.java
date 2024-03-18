package com.example.loginapi.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    String apiUrl = "https://admin.hoangminhlighting.vn/service_XD/service.svc/";
    String uid = "Uid";
    String p = "p";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("GetSessionId")
    Call<String> getListUser(@Query(uid) String UserID, @Query(p) String PassWord);
}
