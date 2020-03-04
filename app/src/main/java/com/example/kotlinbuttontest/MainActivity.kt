package com.example.kotlinbuttontest


import android.Manifest
import android.content.pm.PackageManager

import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.core.app.ActivityCompat
import com.example.kotlin_test.R

import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.http.Url
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL


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

                .subscribe({
                    Log.d("content", it.toString())
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
        var num = 0


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

        plusclick.setOnClickListener {
            num += 1
            Toast.makeText(applicationContext, "$num", Toast.LENGTH_SHORT).show()
        }
        chageImg.setOnClickListener {
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
                mThread.join()
                imageView.setImageBitmap(bitmap)
            }

        }
    }

}
