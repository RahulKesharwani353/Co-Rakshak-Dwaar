package com.example.corakshak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NewRailwayActivity : AppCompatActivity() {

    lateinit var pnrNo : TextInputLayout
    lateinit var name : TextInputLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_railway)

        pnrNo = findViewById(R.id.pnr_no)
        name = findViewById(R.id.pName)

        var Button = findViewById<Button>(R.id.add_new_pnr)
        Button.setOnClickListener {
            Toast.makeText(this, pnrNo.editText?.text.toString() + name.editText?.text.toString(), Toast.LENGTH_SHORT).show()
            name.error = "Eneter re baba"
            pnrNo.error ="tatata"
        }

    }
}