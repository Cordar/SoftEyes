package com.hsd.upc.hack.softeyes2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class ResultsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val extras = intent.extras
        val byteArray = extras!!.getByteArray("image")

        val image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        val imageView: ImageView = findViewById<ImageView>(R.id.image)
        imageView.setImageBitmap(image)

        val description = intent.getStringExtra("description")
        val text: Button = findViewById<Button>(R.id.button)
        text.setText(description)


        val backButton: Button = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            try {
                val intent = Intent(this@ResultsActivity, MainActivity::class.java)
                startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}