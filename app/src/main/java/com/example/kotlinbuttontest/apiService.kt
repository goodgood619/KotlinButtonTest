package com.example.kotlinbuttontest

import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface apiService {
    @Multipart
    @POST("/users")
    fun testcall(@Query("name") name: String?, @Query("part") part: String?, @Part image: MultipartBody.Part?): Call<ResponseBody>

    @POST("/users")
    fun test(): Call<ResponseBody?>?

    @GET("/users")
    fun getUser(@Query("name") name: String?): Call<JsonObject?>?

    @GET("/users")
    fun getTotalUser(): Single<JsonObject>
}