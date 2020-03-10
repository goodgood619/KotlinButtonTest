package com.example.kotlinbuttontest

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin_test.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class ClickEvents : AppCompatActivity() {

    fun lee(v: View, context: Activity) {
        when (v.id) {
            R.id.btn_kotlin -> clickSendPost(context)
            R.id.btn_kotlin2 -> clickSendGet(context)
            R.id.download -> clickDownload(context)
            R.id.chageImg -> clickChangeImg(v, context)
            R.id.show -> clickShow(context)
        }
    }

    private fun clickSendPost(context: Activity) {
        RetrofitService().callBackPost(context.et_kotlin.text.toString())
    }


    private fun clickDownload(context: Activity) {
        RetrofitService().callBackGetImage(context)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
            }, {
            })
        Auth.num += 1
        var r = Auth.num
        Toast.makeText(context, "$r", Toast.LENGTH_SHORT).show()
    }

    private fun clickChangeImg(v: View, context: Activity) {

        //            val imgView : ImageView = findViewById(R.id.imageView)
//findViewById(R.id.imageView)
        lateinit var bitmap: Bitmap
        lateinit var url : URL
        val mThread = Thread() {
            when (Auth.num % 4) {
                0 -> {
                    url = URL(
                        "https://img.hani.co.kr/imgdb/resize/2018/0517/00502814_20180517.JPG"
                    )

                }
                1 -> {
                    url = URL(
                        "https://examplebuckets3good.s3.ap-northeast-2.amazonaws.com/96ef91b680af4f8b8a5f54450dba5fda.jpg"
                    )
                }
                2 -> {
                     url = URL(
                        "http://examplebuckets3good.s3.ap-northeast-2.amazonaws.com/015ce15fd51048d29beddd261dbdff31.jpg"
                    )
                }
                3 -> {
                    url = URL(
                        "https://examplebuckets3good.s3.ap-northeast-2.amazonaws.com/4f75e44fdfe843b6a0476def54ae190d.gif"
                    )
                }
            }
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.connect()

            val sizen: Int = conn.contentLength
            val bis: BufferedInputStream? = BufferedInputStream(conn.getInputStream(), sizen)
            bitmap = BitmapFactory.decodeStream(bis)

        }
        mThread.start()
        mThread.join()
        context.imageView.setImageBitmap(bitmap)
    }
    fun clickSendGet(context: Activity) {

        val retrofitService = RetrofitService().callBackGet(context.et_kotlin.text.toString())
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
                Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
                it.getAsJsonPrimitive("message").asString
                val test = it.getAsJsonArray("data").get(0).asJsonObject.get("name").asString
                retrofitService.getUser(test)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.d("content", it.toString())
                        Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
                    }
            })
            {
                Log.e("Error", it.message)
            }
    }

    private fun clickShow(context: Activity) {
        val intent = Intent(context, ViewActivity::class.java)
        context.startActivity(intent)
    }

}



