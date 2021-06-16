package com.example.corakshak

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import org.jetbrains.annotations.NotNull
import java.io.*


class QRCodeActivity : AppCompatActivity() {

    lateinit var qrDispaly : ImageView
    lateinit var addpnr : Button
    lateinit var qrsave : Button
    lateinit var pnr_no: String

    private val REQUEST_CODE = 100
    var outputStream: OutputStream? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        qrDispaly = findViewById(R.id.imageView)
        addpnr = findViewById(R.id.AddPnr)
        qrsave = findViewById(R.id.QRsave)

        val bundle = intent.extras
        val uid = bundle!!.getString("uid")
        val PNR = bundle!!.getString("pnr")
        val content : String = "$uid/$PNR"
        pnr_no = PNR.toString()

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
            finish()

        }
        //New PNR Add Button end

        //QR code Save Button Start
        qrsave.setOnClickListener {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){

                saveImage();

            }else {


                askPermission();

            }
        }
        //QR code Save Button End

    }


    private fun askPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NotNull permissions: Array<String?>,
        @NotNull grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveImage()
            } else {
                Toast.makeText(
                    this,
                    "Please provide the required permissions",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    private fun saveImage() {
        val dir = File(Environment.getExternalStorageDirectory(), "CoRakshak")
        if (!dir.exists()) {
            dir.mkdir()
        }
        val drawable = qrDispaly.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val file = File(dir, "$pnr_no.jpg")
        try {
            outputStream = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        Toast.makeText(this, "Saved Successfully ", Toast.LENGTH_SHORT).show()
        this.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)))
        try {
            outputStream!!.flush()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "failed: $e", Toast.LENGTH_SHORT).show()
        }
        try {
            outputStream!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "failed: $e", Toast.LENGTH_SHORT).show()
        }
    }

}