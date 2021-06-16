package com.example.corakshak

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
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
    lateinit var radioGroup: RadioGroup
    lateinit var radioButton: RadioButton
    var datefirebase = "10-10-2021"
    lateinit var currentuser : String

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
        currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        radioGroup = findViewById(R.id.gender)


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            radioButton = findViewById(checkedId)
            Toast.makeText(this, "sel: "+ radioButton.text.toString(), Toast.LENGTH_SHORT).show()
        }


        //Date Picker Start

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd MM yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            date_btn.text = sdf.format(cal.time)
            datefirebase = sdf.format(cal.time)
        }

        date_btn.setOnClickListener {

            val datePickerDialog = DatePickerDialog(this@NewRailwayActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH))
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()-1000
            datePickerDialog.show()
        }

        add_btn.setOnClickListener {
            val PNR = pnrNo.editText?.text.toString()
            var name = name.editText?.text.toString()
            var Age = age.editText?.text.toString()
            var Address = address.editText?.text.toString()
            var Emailid = emailid.editText?.text.toString()


            database = FirebaseDatabase.getInstance().getReference("users")

            var Pnr_form = PnrFrom(PNR , name , Age , Address , Emailid, datefirebase)

            database.child(currentuser).setValue(Pnr_form).addOnSuccessListener {

                var forQRcode = (currentuser + "/" + PNR).toString()
                Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()

                val intent = Intent(this@NewRailwayActivity, QRCodeActivity::class.java)
                intent.putExtra("uid", currentuser)
                intent.putExtra("pnr", PNR)
                startActivity(intent)

            }.addOnFailureListener{

                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


            }

        }

    }

    private fun updateLable(mycalender: Calendar) {
        val myformet = "dd-mm-yyyy"
        val sdf = SimpleDateFormat(myformet, Locale.UK)
        date_btn.text = sdf.format(mycalender.time)
        datefirebase = sdf.format(mycalender.time).toString()
    }

//    private fun Gender(){
//        var Id = radioGroup.checkedRadioButtonId
//        radioButton = findViewById(Id)
//        Toast.makeText(this, "select" + radioButton.text.toString(), Toast.LENGTH_SHORT).show()
//    }
}