package com.hsd.upc.hack.softeyes2

import com.hsd.upc.hack.softeyes2.R
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val camera: Button = findViewById<Button>(R.id.camera)
        camera.setOnClickListener {
            try {
                dispatchTakePictureIntent()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val gallery: Button = findViewById<Button>(R.id.gallery)
        gallery.setOnClickListener {
            try {
                dispatchGalleryIntent()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    val REQUEST_IMAGE_SELECTED = 2

    private fun dispatchGalleryIntent() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI).also { selectPictureIntent ->
            selectPictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(selectPictureIntent, REQUEST_IMAGE_SELECTED)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val intent = Intent(this@MainActivity, ScannerActivity::class.java)
            val imageByteArray = convertBitmapToByteArray(imageBitmap)
            intent.putExtra("image", imageByteArray)
            startActivity(intent)
        }

        if (requestCode == REQUEST_IMAGE_SELECTED && resultCode == RESULT_OK) {
            val imageUri = data?.data
            val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri) as Bitmap
            val intent = Intent(this@MainActivity, ScannerActivity::class.java)
            val imageByteArray = convertBitmapToByteArray(imageBitmap)
            intent.putExtra("image", imageByteArray)
            startActivity(intent)
        }
    }

    private fun convertBitmapToByteArray(bm: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}