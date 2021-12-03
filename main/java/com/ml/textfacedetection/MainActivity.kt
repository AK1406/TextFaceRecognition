package com.ml.textfacedetection

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

    lateinit var textScan : ImageView
    lateinit var faceScan : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textScan = findViewById(R.id.text)
        faceScan = findViewById(R.id.face)

        textScan.setOnClickListener{
            val intent : Intent = Intent(this,TextExtractionActivity::class.java)
            startActivity(intent)
        }

    }
}