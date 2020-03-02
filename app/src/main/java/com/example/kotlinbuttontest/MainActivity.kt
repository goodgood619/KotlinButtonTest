package com.example.kotlin_test

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_kotlin.setOnClickListener {
            var ret =RetrofitService().callbackpost(et_kotlin.text.toString())

        }
        btn_kotlin2.setOnClickListener {
            val retrofitService = RetrofitService().callbackget(et_kotlin.text.toString())
            retrofitService.getTotalUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    Log.d("content",it.toString())
                    Log.d("userIdx ",it.getAsJsonArray("data").get(0).asJsonObject.get("userIdx").asString)
                    Log.d("name ",it.getAsJsonArray("data").get(0).asJsonObject.get("name").asString)
                    Log.d("part ",it.getAsJsonArray("data").get(0).asJsonObject.get("part").asString)
                    Log.d("profileUrl ",it.getAsJsonArray("data").get(0).asJsonObject.get("profileUrl").asString)
                    Log.d("message", it.getAsJsonPrimitive("message").asString)
                    Log.d("status",it.getAsJsonPrimitive("status").asString)
                    Toast.makeText(applicationContext, "$it", Toast.LENGTH_LONG).show()
                    val t = it.getAsJsonPrimitive("message").asString
                    val test = it.getAsJsonArray("data").get(0).asJsonObject.get("name").asString
                    retrofitService.getUser(test)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Log.d("content",it.toString())
                            Toast.makeText(applicationContext,"$it",Toast.LENGTH_LONG).show()
                        }
                })
                {
                    Log.e("Error",it.message)
                }


        }

        plusclick.setOnClickListener {
            var num = 0
            num += 1
            Toast.makeText(applicationContext,"$num",Toast.LENGTH_LONG).show()
        }

    }
}
