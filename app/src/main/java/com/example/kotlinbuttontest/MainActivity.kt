package com.example.kotlin_test

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.ArrayMap
import com.example.kotlinbuttontest.ApiService
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var hi: String?
        var ho: String? = "Lee"
        var ms: String? = null
        var ass: String?
        var cnt: Int = 0;


        btn_kotlin.setOnClickListener {

            var retrofit = Retrofit.Builder()
                .baseUrl("http://ec2-13-125-39-193.ap-northeast-2.compute.amazonaws.com:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            val server = retrofit.create(ApiService::class.java)
            val random = Random()
            val num = random.nextInt(24)

            hi = et_kotlin.text.toString()

            val testcall = server.testcall("$hi")
            cnt = cnt + 1

            testcall.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.d("test", response?.body().toString())

                    ass = response.body().toString()
                    // Toast.makeText(applicationContext, "$cnt" + "번 " + "$ms", Toast.LENGTH_LONG).show();
                    Log.d("Print", "$ass")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }
}
