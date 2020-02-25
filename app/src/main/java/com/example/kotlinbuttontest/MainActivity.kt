package com.example.kotlin_test

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinbuttontest.ApiService
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
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
        var ms : String? = null


        btn_kotlin.setOnClickListener {

            var retrofit = Retrofit.Builder()
                .baseUrl("http://ec2-52-79-169-96.ap-northeast-2.compute.amazonaws.com:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
            val server = retrofit.create(ApiService::class.java)
            hi = et_kotlin.text.toString()
            val testcall = server.test()

            val random = Random()
            val num = random.nextInt(24)

            testcall.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Log.d("test", response?.body().toString())
                    server.test().enqueue(object : Callback<JsonObject> {

                        override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {

                            ms = (response?.body()?.get("data")?.asJsonArray?.get(num) as JsonObject).get("name").toString()
                            Toast.makeText(applicationContext, "$ms", Toast.LENGTH_LONG).show();
                            Log.d("Print", response.body().toString())
                        }

                        override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }

                    })
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Log.e("SSS", t.toString())
                }

            })
        }
    }
}
