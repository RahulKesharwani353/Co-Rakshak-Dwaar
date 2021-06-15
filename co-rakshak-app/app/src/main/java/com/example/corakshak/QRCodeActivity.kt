package com.example.corakshak

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import org.w3c.dom.Text

class QRCodeActivity : AppCompatActivity() {

    lateinit var code : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        code = findViewById(R.id.QRcode)

        val bundle = intent.extras
        val QRcode = bundle!!.getString("message")

        code.setText(QRcode)
    }
}