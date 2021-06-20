package com.example.corakshakscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scan : Button = findViewById(R.id.scan_btn)
        scan.setOnClickListener {
            var  intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
        }
    }
}