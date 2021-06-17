package com.example.corakshakscanner

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator


class ScannerActivity : AppCompatActivity() {

    lateinit var scanBtn: Button
    lateinit var messageText: TextView
    lateinit var messageFormat:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        scanBtn = findViewById(R.id.scanBtn)
        messageText = findViewById(R.id.textContent)
        messageFormat = findViewById(R.id.textFormat)

        scanBtn.setOnClickListener{
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setPrompt("Scan Your PNR Code")
            intentIntegrator.setOrientationLocked(true)
            intentIntegrator.initiateScan()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.contents == null) {
                Toast.makeText(baseContext, "Cancelled", Toast.LENGTH_SHORT).show()
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                messageText!!.text = intentResult.contents
                messageFormat!!.text = intentResult.formatName
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}

