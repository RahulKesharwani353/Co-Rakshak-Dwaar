package com.example.corakshak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var railway : Button = findViewById(R.id.new_railway_btn)

        var logout : Button = findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
        }

        railway.setOnClickListener {
            var i: Intent = Intent(this,NewRailwayActivity::class.java)
            startActivity(i)
        }
        
    }

    override fun onStart() {
        super.onStart()

    }
}