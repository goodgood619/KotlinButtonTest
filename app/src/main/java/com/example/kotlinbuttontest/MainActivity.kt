package com.example.kotlinbuttontest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_test.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_kotlin.setOnClickListener(this)
        btn_kotlin2.setOnClickListener(this)
        download.setOnClickListener(this)
        chageImg.setOnClickListener(this)
        show.setOnClickListener(this)
    }
    //모든 클릭이벤트
    override fun onClick(v: View) {
        ClickEvents().lee(v, this)
    }
    // 뒤로가기 버튼 처리
    override fun onBackPressed() {
        BackEvent().askMainFinish(this)
    }
}
