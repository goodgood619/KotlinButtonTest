package com.example.kotlinbuttontest

import android.content.Context
import android.media.MediaScannerConnection
import android.util.Log
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*


// 데이터송수신을 받은다음, 간략하게 데이터 재가공 후 리턴
class RetrofitService {

    fun callbackpost(res: String) {
        var retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-13-125-39-193.ap-northeast-2.compute.amazonaws.com:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
//        val file = File(Context.getFiles"C:\\Users\\Jay\\Desktop\\테스트\\2.jpg")
        val file = File("/storage/emulated/0/Download/POL_apple.jpg")
        var fbody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
        var multibody = MultipartBody.Part.createFormData("profile",file.name,fbody)
        val server = retrofit.create(apiService::class.java)
        val random = Random()
        val num = random.nextInt(24)
        var ass = "";
        var cnt = 0;
        var hi = res
        val testcall = server.testcall("$hi","test",multibody)
        cnt = cnt + 1
        var ret = ""
        testcall.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Log.d("test", response?.body().toString())
                ass = response.body().toString()
                // Toast.makeText(applicationContext, "$cnt" + "번 " + "$ms", Toast.LENGTH_LONG).show();
                Log.d("Print", "$ass")
                ret = response?.body().toString()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("error", t.message.toString())
            }
        })
    }

    fun callbackget(res: String): apiService {
        var retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://10.0.2.2:8080/")
            .build().create(apiService::class.java)
        return retrofit;
    }

    fun callbackgetimage(context: Context) :Observable<String>{
        val imageretrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://s3.ap-northeast-2.amazonaws.com/examplebuckets3good/")
            .build().create(apiService::class.java)

        var call = imageretrofit.getImageDetails("036f7102640a41749af35e58aaceff7d.PNG")
        return Observable.create {
            call.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    val t= Observable.fromCallable {
                        if (response.isSuccessful) {
                            Downloadimage(response.body(),context)
                        }
                        else {
                            Log.e("AWS", "AWS error")
                        }
                    }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d("파일다운로드","ok")
                            Toast.makeText(context,"파일 다운로드 완료",Toast.LENGTH_LONG).show()
                        },{
                            Log.e("파일다운로드","error")
                            Toast.makeText(context,"파일 다운로드 실패",Toast.LENGTH_LONG).show()
                        })

                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Log.e("TAG", "error")
                }
            })
        }
//        call.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe {
//                Log.d("msg", it.string())
//                Downloadimage(it)
//                Log.d("image download", "ok")
//            }(
//            Log.d("error","errorerror")
//        )

    }
    fun Downloadimage(response: ResponseBody?,context: Context): String {
        return try {
            Log.d("DownloadImage", "Reading and writing file")
            var input: InputStream? = null
            var output: FileOutputStream? = null
            val futureStudioIconFile = File(
                context.getExternalFilesDir(null)?.absolutePath + File.separator
                        + "testdownload4.png"
            )
            try {
                if (response != null) {
                    input = response.byteStream()
                }
                val fileReader = ByteArray(4096)
                val fileSize: Long = response!!.contentLength()
                var fileSizeDownloaded: Long = 0
                output = FileOutputStream(futureStudioIconFile)
                var c: Int = 0
                if (input != null) {
                    while (true) {
                        val read: Int = input.read(fileReader)
                        if (read == -1) {
                            break
                        }
                        output.write(fileReader, 0, read)
                        fileSizeDownloaded += read
                        Log.d("file download: " + fileSizeDownloaded.toString() + " of " + fileSize,"loading")
                    }
//                    bitmap = BitmapFactory.decodeStream(input)
                    Log.d("downloaded ", "ok download")
                }
            } catch (e: IOException) {
                Log.d("DownloadImage", e.toString())
                return ""
            } finally {
                if (input != null) {
                    input.close()
                }
                if (output != null) {
                    output.close()
                }
            }
            Log.d("after downloaded?", "test")
            Log.d("경로: ",context.getExternalFilesDir(null)?.absolutePath+File.separator+"testdownload4.png")
            if(File(context.getExternalFilesDir(null)?.absolutePath + File.separator+ "testdownload4.png").exists()) {
                Log.d("true","true")
                scanFile(context,context.getExternalFilesDir(null)?.absolutePath + File.separator+ "testdownload4.png")
            }
            else Log.d("false","false")
            return context.getExternalFilesDir(null)?.absolutePath + File.separator+ "testdownload4.png"
        } catch (e: IOException) {
            Log.d("DownloadImage", e.toString())
            return ""
        }
    }
    private fun scanFile(context: Context,path: String) {
        MediaScannerConnection.scanFile(
            context, arrayOf(path), null
        )
        { path, uri -> Log.d("Tag", "Scan finished. You can view the image in the gallery now.") }
    }
}
//                val width: Int
//                val height: Int
//                val image: ImageView = findViewById<View>(android.R.id.imageViewId) as ImageView
//                val bMap =
//                    BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().toString() + File.separator + "A.jpg")
//                width = 2 * bMap.width
//                height = 6 * bMap.height
//                val bMap2 = Bitmap.createScaledBitmap(bMap, width, height, false)
//                image.setImageBitmap(bMap2)

