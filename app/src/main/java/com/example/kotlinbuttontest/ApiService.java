package com.example.kotlinbuttontest;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/users")
    Call<JsonObject> testcall(@Query("name") String name);

    @GET("/users")
    Call<JsonObject> test();
}
