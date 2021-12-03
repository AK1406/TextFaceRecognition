package com.ml.textfacedetection

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText

class TextExtractionActivity : AppCompatActivity() {
    // creating variables for our
    // image view, text view and two buttons.
    private lateinit var img: ImageView
    private lateinit var textview: TextView
    private lateinit var snapBtn: Button
    private lateinit var detectBtn: Button

    // variable for our image bitmap.
    private var imageBitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_extraction)

        img = findViewById<View>(R.id.image) as ImageView
        textview = findViewById<View>(R.id.text) as TextView
        snapBtn = findViewById<View>(R.id.snapbtn) as Button
        detectBtn = findViewById<View>(R.id.detectbtn) as Button

        // adding on click listener for detect button.

        // adding on click listener for detect button.
        detectBtn.setOnClickListener{

                // calling a method to
                // detect a text .
                detectTxt()

        }
        snapBtn.setOnClickListener {

                // calling a method to capture our image.
                dispatchTakePictureIntent()

        }

    }
    private val REQUEST_IMAGE_CAPTURE = 1

    @SuppressLint("QueryPermissionsNeeded")
    private fun dispatchTakePictureIntent() {
        // in the method we are displaying an intent to capture our image.
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        // on below line we are calling a start activity
        // for result method to get the image captured.
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // calling on activity result method.
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // on below line we are getting
            // data from our bundles. .
            val extras = data!!.extras
            imageBitmap = extras!!["data"] as Bitmap?

            // below line is to set the
            // image bitmap to our image.
            img.setImageBitmap(imageBitmap)
        }
    }

    private fun detectTxt() {
        // this is a method to detect a text from image.
        // below line is to create variable for firebase
        // vision image and we are getting image bitmap.
        val image = FirebaseVisionImage.fromBitmap(imageBitmap!!)

        // below line is to create a variable for detector and we
        // are getting vision text detector from our firebase vision.
        val detector = FirebaseVision.getInstance().visionTextDetector

        // adding on success listener method to detect the text from image.
        detector.detectInImage(image).addOnSuccessListener { firebaseVisionText -> // calling a method to process
            // our text after extracting.
            processTxt(firebaseVisionText)
        }.addOnFailureListener { // handling an error listener.
            Toast.makeText(this@TextExtractionActivity, "Fail to detect the text from image..", Toast.LENGTH_SHORT).show()
        }
    }

    private fun processTxt(text: FirebaseVisionText) {
        // below line is to create a list of vision blocks which
        // we will get from our firebase vision text.
        val blocks = text.blocks

        // checking if the size of the
        // block is not equal to zero.
        if (blocks.size == 0) {
            // if the size of blocks is zero then we are displaying
            // a toast message as no text detected.
            Toast.makeText(this@TextExtractionActivity, "No Text ", Toast.LENGTH_LONG).show()
            return
        }
        // extracting data from each block using a for loop.
        for (block in text.blocks) {
            // below line is to get text
            // from each block.
            val txt = block.text

            // below line is to set our
            // string to our text view.
            textview.text = txt
        }
    }

}