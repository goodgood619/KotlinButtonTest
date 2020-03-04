package com.example.kotlinbuttontest

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.kotlin_test.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
        btn_kotlin.setOnClickListener {
            var ret = RetrofitService().callbackpost(et_kotlin.text.toString())

        }
        btn_kotlin2.setOnClickListener {
            val retrofitService = RetrofitService().callbackget(et_kotlin.text.toString())
            retrofitService.getTotalUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    Log.d("content", it.toString())
                    Log.d("userIdx ", it.getAsJsonArray("data").get(0).asJsonObject.get("userIdx").asString)
                    Log.d("name ", it.getAsJsonArray("data").get(0).asJsonObject.get("name").asString)
                    Log.d("part ", it.getAsJsonArray("data").get(0).asJsonObject.get("part").asString)
                    Log.d("profileUrl ", it.getAsJsonArray("data").get(0).asJsonObject.get("profileUrl").asString)
                    Log.d("message", it.getAsJsonPrimitive("message").asString)
                    Log.d("status", it.getAsJsonPrimitive("status").asString)
                    Toast.makeText(applicationContext, "$it", Toast.LENGTH_LONG).show()
                    val t = it.getAsJsonPrimitive("message").asString
                    val test = it.getAsJsonArray("data").get(0).asJsonObject.get("name").asString
                    retrofitService.getUser(test)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Log.d("content", it.toString())
                            Toast.makeText(applicationContext, "$it", Toast.LENGTH_LONG).show()
                        }
                })
                {
                    Log.e("Error", it.message)
                }


        }

        download.setOnClickListener {
            RetrofitService().callbackgetimage(this)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                },{
                })
        }
    }

    fun checkPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

}
