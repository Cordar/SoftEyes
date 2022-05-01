package com.hsd.upc.hack.softeyes2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream


class ScannerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        val extras = intent.extras
        val byteArray = extras!!.getByteArray("image")

        val image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)

        val imageView: ImageView = findViewById<ImageView>(R.id.image)
        imageView.setImageBitmap(image)

        val scan: ImageButton = findViewById<ImageButton>(R.id.scan)
        scan.setOnClickListener {
            try {
                scan(image)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun scan(image: Bitmap) {
        val image64 = encodeImage(image)
        lifecycleScope.launch {
            val results = makeApiCall(img = image64)
            val intent = Intent(this@ScannerActivity, ResultsActivity::class.java)
            val imageByteArray = convertBitmapToByteArray(image)
            intent.putExtra("image", imageByteArray)
            intent.putExtra("description", results.toString())
            startActivity(intent)
        }
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b = baos.toByteArray()
        return Base64.encodeToString(b, Base64.DEFAULT)
    }

    private suspend fun makeApiCall(img: String): MultipredictCaptionModel? {
        try {
            val service = RetrofitClient.retrofitInstance!!.create(
                RetrofitService::class.java
            )
            return service.caption(img)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return null
        }
    }

    private fun convertBitmapToByteArray(bm: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}