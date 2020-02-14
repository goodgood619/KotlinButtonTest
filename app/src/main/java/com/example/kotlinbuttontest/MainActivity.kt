package com.example.kotlinbuttontest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var et = findViewById(R.id.et_kotlin) as EditText

        btn_kotlin.setOnClickListener {
            Volleyservice.testVolley(this) { testSuccess ->
                if (testSuccess) {
                    Toast.makeText(this, "통신 성공!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "통신 실패...!", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}
