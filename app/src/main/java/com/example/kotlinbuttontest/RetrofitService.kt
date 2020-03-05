package com.example.kotlinbuttontest


import android.content.Context
import android.media.MediaScannerConnection
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import android.util.Log
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

// 데이터송수신을 받은다음, 간략하게 데이터 재가공 후 리턴
class RetrofitService {
    fun callBackPost(res: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-13-209-73-204.ap-northeast-2.compute.amazonaws.com:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val file = File("/storage/emulated/0/Download/i14566164523.gif")
        val fbody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val multibody = MultipartBody.Part.createFormData("profile", file.name, fbody)
        val server = retrofit.create(apiService::class.java)
        val hi = res
        var ass = ""
        val testcall = server.testcall("$hi", "test", multibody)
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

    fun callBackGet(res: String): apiService {
        var retrofit = Retrofit.Builder()
            .baseUrl("http://ec2-13-209-73-204.ap-northeast-2.compute.amazonaws.com:8080")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiService::class.java)
        return retrofit
    }

    fun callBackGetImage(context: Context): Observable<String> {
        val imageretrofit = Retrofit.Builder()
            .baseUrl("https://postfiles.pstatic.net/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(apiService::class.java)
        var call = imageretrofit.getImageDetails("MjAyMDAzMDJfNjEg/MDAxNT" +
                "gzMTEwOTA2Mjg4.w6xBoCd_pl6f0nLqIuwD4f-2LW7L4fZVbLlOTsGZ6tUg.mwITBI6MBGXmfIeFLaX9FVwWEcNHXB82oQUh8xv5ZqIg.J" +
                "PEG.jjujub8870/0034ef1057db2ed6a86312649a8cbc61.jpg")

        return Observable.create {
            call.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                    val t = Observable.fromCallable {
                        if (response.isSuccessful) {
                            Downloadimage(response.body(), context)
                        } else {
                            Log.e("AWS", "AWS error")
                        }
                    }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            Log.d("파일다운로드", "ok")
                            Toast.makeText(context, "파일 다운로드 완료", Toast.LENGTH_LONG).show()
                        }, {
                            Log.e("파일다운로드", "error")
                            Toast.makeText(context, "파일 다운로드 실패", Toast.LENGTH_LONG).show()
                        })

                }
                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Log.e("TAG", "error")
                }
            })
        }
    }

    fun Downloadimage(response: ResponseBody?, context: Context): String {
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
                if (input != null) {
                    while (true) {
                        val read: Int = input.read(fileReader)
                        if (read == -1) {
                            break
                        }
                        output.write(fileReader, 0, read)
                        fileSizeDownloaded += read
                        Log.d("file download: " + fileSizeDownloaded.toString() + " of " + fileSize, "loading")
                    }
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
            Log.d("경로: ", context.getExternalFilesDir(null)?.absolutePath + File.separator + "testdownload4.png")

            if (File(context.getExternalFilesDir(null)?.absolutePath + File.separator + "testdownload4.png").exists()) {
                Log.d("true", "true")
                scanFile(
                    context,
                    context.getExternalFilesDir(null)?.absolutePath + File.separator + "testdownload4.png"
                )
            } else Log.d("false", "false")

            return context.getExternalFilesDir(null)?.absolutePath + File.separator + "testdownload4.png"
        } catch (e: IOException) {
            Log.d("DownloadImage", e.toString())
            return ""
        }
    }

    private fun scanFile(context: Context, path: String) {
        MediaScannerConnection.scanFile(
            context, arrayOf(path), null
        )
        { path, uri -> Log.d("Tag", "Scan finished. You can view the image in the gallery now.") }
    }
}