package com.example.kotlin_test

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinbuttontest.ApiService
import com.google.gson.JsonObject
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*

// 데이터송수신을 받은다음, 간략하게 데이터 재가공 후 리턴
class RetrofitService : AppCompatActivity(){

    fun callbackpost(res: String) {
        var retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-13-125-39-193.ap-northeast-2.compute.amazonaws.com:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
//        val file = File(Context.getFiles"C:\\Users\\Jay\\Desktop\\테스트\\2.jpg")
        val file = File("/storage/emulated/0/Download/POL_apple.jpg")
        var fbody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        var multibody = MultipartBody.Part.createFormData("profile",file.name,fbody)
        val server = retrofit.create(ApiService::class.java)
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

    fun callbackget(res: String): String {
        var retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-13-125-39-193.ap-northeast-2.compute.amazonaws.com:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val server = retrofit.create(ApiService::class.java)
        val hi = res
        val testcall = server.getTotalUser()

        val random = Random()
        val num = random.nextInt(24)
        var ret = "콜백이 오기전"
        testcall.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                Log.d("test", response?.body().toString())

                val ms = (response?.body()?.get("data")?.asJsonArray?.get(num) as JsonObject).get("name").toString()
                ret = ms
                Log.d("Print", response.body().toString())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        return ret
    }
}