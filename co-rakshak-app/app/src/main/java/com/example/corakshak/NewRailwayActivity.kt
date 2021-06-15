package com.example.corakshak

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class NewRailwayActivity : AppCompatActivity() {

    private lateinit var database : DatabaseReference
    lateinit var pnrNo : TextInputLayout
    lateinit var name : TextInputLayout
    lateinit var add_btn : Button
    lateinit var age : TextInputLayout
    lateinit var address : TextInputLayout
    lateinit var emailid : TextInputLayout
    lateinit var date_btn : Button
    var datefirebase = "10-10-2021"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_railway)

        //val currentFirebaseUser = FirebaseAuth.getInstance().currentUser

        pnrNo = findViewById(R.id.pnr_no)
        name = findViewById(R.id.pName)
        add_btn = findViewById(R.id.add_but)
        age = findViewById(R.id.pAge)
        address = findViewById(R.id.pAddress)
        emailid = findViewById(R.id.pEmail)
        date_btn = findViewById(R.id.date_btn)

        //Date Picker Start

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd.MM.yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            date_btn.text = sdf.format(cal.time)
            datefirebase = sdf.format(cal.time)

        }

        date_btn.setOnClickListener {
            DatePickerDialog(this@NewRailwayActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        add_btn.setOnClickListener {

            var PNR = pnrNo.editText?.text.toString()
            var name = name.editText?.text.toString()
            var Age = age.editText?.text.toString()
            var Address = address.editText?.text.toString()
            var Emailid = emailid.editText?.text.toString()
            val currentuser = FirebaseAuth.getInstance().currentUser!!.uid

            database = FirebaseDatabase.getInstance().getReference("users")

            var Pnr_form = PnrFrom(PNR , name , Age , Address , Emailid, datefirebase)

            database.child(currentuser).setValue(Pnr_form).addOnSuccessListener {

                var forQRcode = (currentuser + "/" + PNR).toString()
                Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()

                val intent = Intent(this@NewRailwayActivity, QRCodeActivity::class.java)
                intent.putExtra("message", forQRcode)
                startActivity(intent)

            }.addOnFailureListener{

                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


            }

//            name.error = "Eneter re baba"
//            PNR.error ="tatata"
        }

    }

    private fun updateLable(mycalender: Calendar) {
        val myformet = "dd-mm-yyyy"
        val sdf = SimpleDateFormat(myformet, Locale.UK)
        date_btn.text = sdf.format(mycalender.time)
        datefirebase = sdf.format(mycalender.time).toString()



    }
}