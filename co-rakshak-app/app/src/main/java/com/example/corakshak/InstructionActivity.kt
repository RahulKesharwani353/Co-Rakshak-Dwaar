package com.example.corakshak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView

class InstructionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instruction)

        val cont: CardView = findViewById(R.id.continue_btn)
        cont.setOnClickListener {
            startActivity(Intent(this, NewRailwayActivity::class.java))
            finish()
        }
    }
}