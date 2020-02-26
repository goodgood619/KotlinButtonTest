package com.example.kotlinbuttontest;

import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {

    @POST("/users")
    Call<ResponseBody> testcall(@Query("name") String name);

    @POST("/users")
    Call<ResponseBody> test();
}

