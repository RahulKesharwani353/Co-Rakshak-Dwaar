package com.example.corakshak

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class UserInfoFormActivity2 : AppCompatActivity() {

    lateinit var firstName : TextInputLayout
    lateinit var lastName : TextInputLayout
    lateinit var DOB : Button
    lateinit var add_btn : Button
    lateinit var cancle_btn : Button
    lateinit var email : TextInputLayout
    lateinit var city : TextInputLayout
    lateinit var currentuser : String
    private lateinit var database : DatabaseReference
    var datefirebase = ""
    lateinit var radioGroup: RadioGroup
    lateinit var radioButton: RadioButton
    var gender : String = "Male"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_form3)

        //val user = Firebase.auth.currentUser


        firstName = findViewById(R.id.DFName)
        lastName = findViewById(R.id.DLName)
        DOB = findViewById(R.id.Ddate_btn)
        add_btn = findViewById(R.id.Dadd_but)
        cancle_btn = findViewById(R.id.Dcancel)
        email = findViewById(R.id.DEmail)
        city = findViewById(R.id.DCity)
        radioGroup = findViewById(R.id.Dgender)
        currentuser = FirebaseAuth.getInstance().currentUser!!.uid

        DOB.setOnClickListener{
            selectDate();
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            radioButton = findViewById(checkedId)
            gender = radioButton.text.toString()

        }

        add_btn.setOnClickListener{

            var FirstName = firstName.editText?.text.toString().trim()
            var LastName = lastName.editText?.text.toString().trim()
            var Email = email.editText?.text.toString()
            var city = city.editText?.text.toString()
            var Name = "$FirstName $LastName"

            database = FirebaseDatabase.getInstance().getReference("users")

            var UserForm = UserForm(Name , datefirebase , Email , city, currentuser , gender)

            database.child(currentuser).child("profile").setValue(UserForm).addOnSuccessListener {

                Toast.makeText(this,"Done", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@UserInfoFormActivity2, HomeActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener{
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()
            }
        }


    }
    private fun selectDate(){
        var cal = Calendar.getInstance()
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "dd MM yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            DOB.text = sdf.format(cal.time)
            datefirebase = sdf.format(cal.time)
        }
        val datePickerDialog = DatePickerDialog(this@UserInfoFormActivity2, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()-1000
        datePickerDialog.show()
    }
}