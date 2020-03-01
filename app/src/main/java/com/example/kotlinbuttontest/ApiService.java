package com.example.kotlinbuttontest;

import com.google.gson.JsonObject;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import retrofit2.Call;
import retrofit2.http.*;


public interface ApiService {

    @Multipart
    @POST("/users")
    Call<ResponseBody> testcall(@Query("name") String name, @Query("part") String part, @Part MultipartBody.Part image);

    @POST("/users")
    Call<ResponseBody> test();

    @GET("/users")
    Call<JsonObject> getUser(@Query("name") String name);

    @GET("/users")
    Call<JsonObject> getTotalUser();
}

