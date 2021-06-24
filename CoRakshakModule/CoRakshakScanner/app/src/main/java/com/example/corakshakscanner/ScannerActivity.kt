package com.example.corakshakscanner

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import com.google.zxing.integration.android.IntentIntegrator
import java.util.*


class ScannerActivity : AppCompatActivity() {

    lateinit var dbRef: DatabaseReference
     var status: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
//
//        scanBtn = findViewById(R.id.scanBtn)
//        messageText = findViewById(R.id.textContent)
//        messageFormat = findViewById(R.id.textFormat)
//
//        scanBtn.setOnClickListener{
//
//        }
        dbRef = FirebaseDatabase.getInstance().reference

    }

    override fun onStart() {
        super.onStart()
        val intentIntegrator = IntentIntegrator(this)
        intentIntegrator.setPrompt("Scan Your PNR Code")
        intentIntegrator.setOrientationLocked(true)
        intentIntegrator.initiateScan()

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.contents == null) {
                Toast.makeText(baseContext, "Cancelled", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                setUp(intentResult.contents.toString())
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    fun setUp(qrVal: String){
        val date = Calendar.getInstance().time
        val Dateformatter = SimpleDateFormat("dd MM yyyy")//or use getDateInstance()
        val Timeformatter = SimpleDateFormat("HH:mm:ss")
        val formatedDate = Dateformatter.format(date).toString()
        val formatedTime = Timeformatter.format(date).toString()
        var pnr: String =""
        var name: String=""
        var age: String=""
        var add: String=""
        var vacc: String=""
        var phnNo: String=""
        var uid: String=""
        var gender: String=""




        dbRef.child("users/$qrVal").get().addOnSuccessListener {
            if (it.exists()){
                pnr = it.child("pnr").value.toString()
                name = it.child("name").value.toString()
                age = it.child("age").value.toString()
                gender = it.child("gender").value.toString()
                add = it.child("address").value.toString()
                phnNo = it.child("phone").value.toString()
                vacc = it.child("vacinationStatus").value.toString()
                uid = it.child("uid").value.toString()



                dbRef.child("controller/module1").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        status = snapshot.child("status").value.toString().toInt()
                        if (status ==0){
                            Toast.makeText(this@ScannerActivity, "Processing", Toast.LENGTH_SHORT).show()
                            dbRef.child("controller/module1").child("currentPNR").setValue(pnr)
                            dbRef.child("controller/module1").child("currentDate").setValue(formatedDate)
                            dbRef.child("controller/module1").child("currentURI").setValue(uid)
                            dbRef.child("controller/module1").child("status").setValue(1)

                                .addOnCompleteListener {
                                  if (it.isSuccessful)
                                      Toast.makeText(this@ScannerActivity, "$name-$age-$gender-$uid-$formatedTime", Toast.LENGTH_SHORT).show()

                                    var checkIn = checkIn(pnr , name , age , add, vacc, gender, phnNo, formatedTime, uid)

                                    dbRef.child("record/$formatedDate/$pnr").setValue(checkIn).addOnSuccessListener {

                                        Toast.makeText(this@ScannerActivity,"Done",Toast.LENGTH_SHORT).show()


                                    }.addOnFailureListener{

                                        Toast.makeText(this@ScannerActivity,"Failed",Toast.LENGTH_SHORT).show()


                                    }

                                }


                        }
                        else
                        {
                            Toast.makeText(this@ScannerActivity, "Already Busy", Toast.LENGTH_SHORT).show()
                            return
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
            else
                Toast.makeText(this, "Wrong QR ", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(this, "Failed, Try Again", Toast.LENGTH_SHORT).show()
        }
        }


}

