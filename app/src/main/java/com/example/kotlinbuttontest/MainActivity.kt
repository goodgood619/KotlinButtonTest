package com.example.kotlin_test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_kotlin.setOnClickListener {
            var ret =RetrofitService().callbackpost(et_kotlin.text.toString())

        }
        btn_kotlin2.setOnClickListener {
            var ret2 = RetrofitService().callbackget(et_kotlin.text.toString())
            Toast.makeText(applicationContext, "$ret2", Toast.LENGTH_LONG).show()
        }
    }
}
