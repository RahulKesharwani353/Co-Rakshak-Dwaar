package com.example.corakshak

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import java.io.File
import java.io.FileOutputStream


class QRCodeActivity : AppCompatActivity() {

    lateinit var qrDispaly : ImageView
    lateinit var addpnr : Button
    lateinit var qrsave : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        qrDispaly = findViewById(R.id.imageView)
        addpnr = findViewById(R.id.AddPnr)
        qrsave = findViewById(R.id.QRsave)

        val bundle = intent.extras
        val uid = bundle!!.getString("uid")
        val PNR = bundle!!.getString("pnr")
        val content : String = uid+"/"+PNR

        //qr code Genrate start

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        qrDispaly.setImageBitmap(bitmap)

        //qr code Genrate end

        //New PNR Add Button start
        addpnr.setOnClickListener {
            val intent = Intent(this@QRCodeActivity, NewRailwayActivity::class.java)
            intent.putExtra("key", "Kotlin")
            startActivity(intent)
        }
        //New PNR Add Button end

        //QR code Save Button Start
        qrsave.setOnClickListener {
            saveToGallery()
        }
        //QR code Save Button End

    }

    private fun saveToGallery() {
        val bitmapDrawable = qrDispaly.getDrawable() as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        var outputStream: FileOutputStream? = null
        val file: File = Environment.getExternalStorageDirectory()
        val dir = File(file.getAbsolutePath().toString() + "/MyPics")
        dir.mkdirs()
        val filename = String.format("%d.png", System.currentTimeMillis())
        val outFile = File(dir, filename)
        try {
            outputStream = FileOutputStream(outFile)
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "1Failed$e", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        try {
            if (outputStream != null) {
                outputStream.flush()
            }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "2Failed$e", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
        try {
            if (outputStream != null) {
                outputStream.close()
            }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "3Failed$e", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}