package com.example.kotlin_test

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinbuttontest.apiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*

// 데이터송수신을 받은다음, 간략하게 데이터 재가공 후 리턴
class RetrofitService : AppCompatActivity(){

    fun callbackpost(res: String) {
        var retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-13-209-73-204.ap-northeast-2.compute.amazonaws.com:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
//        val file = File(Context.getFiles"C:\\Users\\Jay\\Desktop\\테스트\\2.jpg")
        val file = File("/storage/emulated/0/Download/i14566164523.gif")
        var fbody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        var multibody = MultipartBody.Part.createFormData("profile",file.name,fbody)
        val server = retrofit.create(apiService::class.java)
        val random = Random()
        val num = random.nextInt(24)
        var ass = "";
        var cnt = 0;
        var hi = res
        val testcall = server.testcall("$hi","test",multibody)
        cnt = cnt + 1
        var ret = ""
        testcall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("test", response?.body().toString())
                ass = response.body().toString()
                // Toast.makeText(applicationContext, "$cnt" + "번 " + "$ms", Toast.LENGTH_LONG).show();
                Log.d("Print", "$ass")
                ret = response?.body().toString()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error", t.message.toString())
            }
        })
    }

    fun callbackget(res: String): apiService {
        var retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.0.18:8080/")
            .build().create(apiService::class.java)
        return retrofit
    }

}