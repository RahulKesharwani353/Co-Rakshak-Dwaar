package com.example.corakshakscanner

import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scan : Button = findViewById(R.id.scan_btn)
        scan.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
        }
        val logout : Button = findViewById(R.id.logout)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        val data = findViewById<Button>(R.id.webView)
        data.setOnClickListener {
            val url = "https://co-rakshak-dwaar.web.app/"
            try {
                val i = Intent("android.intent.action.MAIN")
                i.component =
                    ComponentName.unflattenFromString("com.android.chrome/com.android.chrome.Main")
                i.addCategory("android.intent.category.LAUNCHER")
                i.data = Uri.parse(url)
                startActivity(i)
            } catch (e: ActivityNotFoundException) {
                // Chrome is not installed
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(i)
            }
        }
    }
}