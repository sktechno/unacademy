package com.sk.unacademy

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class MainActivity : AppCompatActivity() {

    companion object {

        const val IMAGE_1 = "https://homepages.cae.wisc.edu/~ece533/images/boat.png"
        const val IMAGE_2 = "https://homepages.cae.wisc.edu/~ece533/images/fruits.png"
    }

    private val serviceWorker1 = ServiceWorker<Bitmap>("service_worker_1");
    private val serviceWorker2 = ServiceWorker<Bitmap>("service_worker_2");


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        button1.setOnClickListener(View.OnClickListener { fetchImage1AndSet() })
        button2.setOnClickListener(View.OnClickListener { fetchImage2AndSet() })


    }


    private fun fetchImage1AndSet() {
        progress1.visibility = View.VISIBLE
        serviceWorker1.addTask(object : Task<Bitmap> {
            override fun onExecuteTask(): Bitmap {
                //Fetching image1 through okhttp
                val request: Request = Request.Builder().url(IMAGE_1).build()
                val response: Response = OkHttpClient().newCall(request).execute()
                return BitmapFactory.decodeStream(response.body?.byteStream())
            }

            override fun onTaskComplete(result: Bitmap) {
                progress1.visibility = View.GONE
                imageView1.setImageBitmap(result);
            }


        })
    }

    private fun fetchImage2AndSet() {
        progress2.visibility = View.VISIBLE
        serviceWorker2.addTask(object : Task<Bitmap> {
            override fun onExecuteTask(): Bitmap {
                //Fetching image2 through okhttp
                val request: Request = Request.Builder().url(IMAGE_2).build()
                val response: Response = OkHttpClient().newCall(request).execute()
                return BitmapFactory.decodeStream(response.body?.byteStream())

            }

            override fun onTaskComplete(result: Bitmap) {
                progress2.visibility = View.GONE
                imageView2.setImageBitmap(result);
            }


        })
    }
}