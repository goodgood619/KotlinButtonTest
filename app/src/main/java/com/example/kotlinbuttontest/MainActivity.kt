package com.example.kotlinbuttontest


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.core.app.ActivityCompat
import com.example.kotlin_test.R

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var num = 0

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_kotlin -> ClickSendPost()
            R.id.btn_kotlin2 -> ClickSendGet()
            R.id.download -> ClickDownload()
            R.id.chageImg -> ClickChangeImg()
            R.id.show -> clickShow()
        }
    }

    override fun onBackPressed() {
        BackEvent().askMainFinish(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermission()

        btn_kotlin.setOnClickListener(this)
        btn_kotlin2.setOnClickListener(this)
        download.setOnClickListener(this)
        chageImg.setOnClickListener(this)
        show.setOnClickListener(this)
    }


    private fun checkPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    private fun ClickSendPost() {
        RetrofitService().callBackPost(et_kotlin.text.toString())
    }

    fun ClickSendGet() {

        val retrofitService = RetrofitService().callBackGet(et_kotlin.text.toString())
        retrofitService.getTotalUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            /*.subscribe({ it ->
                Log.d("content", it.toString())
                Log.d("userIdx ", it.getAsJsonArray("data").get(0).asJsonObject.get("userIdx").asString)
                Log.d("name ", it.getAsJsonArray("data").get(0).asJsonObject.get("name").asString)
                Log.d("part ", it.getAsJsonArray("data").get(0).asJsonObject.get("part").asString)
                Log.d("profileUrl ", it.getAsJsonArray("data").get(0).asJsonObject.get("profileUrl").asString)
                Log.d("message", it.getAsJsonPrimitive("message").asString)
                Log.d("status", it.getAsJsonPrimitive("status").asString)*/
            .subscribe({
                Log.d("content", it.toString())
                Toast.makeText(applicationContext, "$it", Toast.LENGTH_LONG).show()
                it.getAsJsonPrimitive("message").asString
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

    private fun ClickDownload() {
        RetrofitService().callBackGetImage(this)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
            })
        num += 1
        Toast.makeText(applicationContext, "$num", Toast.LENGTH_SHORT).show()
    }

    private fun ClickChangeImg() {
        lateinit var bitmap: Bitmap
        if (num % 4 == 3) {

            val mThread = Thread() {
                val url = URL(
                    "https://examplebuckets3good.s3.ap-northeast-2.amazonaws.com/4f75e44fdfe843b6a0476def54ae190d.gif"
                )
                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.connect()

                val sizen: Int = conn.contentLength
                val bis: BufferedInputStream? = BufferedInputStream(conn.getInputStream(), sizen)
                bitmap = BitmapFactory.decodeStream(bis)
            }
            mThread.start()
            mThread.join()
            imageView.setImageBitmap(bitmap)
        } else if (num % 4 == 2) {
            lateinit var bitmap: Bitmap
            val mThread = Thread() {
                val url = URL(
                    "http://examplebuckets3good.s3.ap-northeast-2.amazonaws.com/015ce15fd51048d29beddd261dbdff31.jpg"
                )

                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.connect()

                val sizen: Int = conn.contentLength
                val bis: BufferedInputStream? = BufferedInputStream(conn.getInputStream(), sizen)
                bitmap = BitmapFactory.decodeStream(bis)
            }
            mThread.start()
            mThread.join()
            imageView.setImageBitmap(bitmap)
        } else if (num % 4 == 1) {
            //            val imgView : ImageView = findViewById(R.id.imageView)
//findViewById(R.id.imageView)
            lateinit var bitmap: Bitmap
            val mThread = Thread() {
                val url = URL(
                    "https://examplebuckets3good.s3.ap-northeast-2.amazonaws.com/96ef91b680af4f8b8a5f54450dba5fda.jpg"
                )

                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.connect()

                val sizen: Int = conn.contentLength
                val bis: BufferedInputStream? = BufferedInputStream(conn.getInputStream(), sizen)
                bitmap = BitmapFactory.decodeStream(bis)

            }
            mThread.start()
            mThread.join()
            imageView.setImageBitmap(bitmap)
        }
        //imageView.setImageBitmap(imgBitmap)
        else {
            lateinit var bitmap: Bitmap
            // 스레드
            val mThread = Thread() {
                val url = URL(
                    "https://img.hani.co.kr/imgdb/resize/2018/0517/00502814_20180517.JPG"
                )

                val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.connect()

                val sizen: Int = conn.contentLength
                val bis: BufferedInputStream? = BufferedInputStream(conn.getInputStream(), sizen)
                bitmap = BitmapFactory.decodeStream(bis)
            }
            mThread.start()
            // 조인을 딴곳으로 뺄것
            mThread.join()

            imageView.setImageBitmap(bitmap)
        }
    }

    private fun clickShow() {
        val intent = Intent(this, ViewActivity::class.java)
        startActivity(intent)
    }

}

