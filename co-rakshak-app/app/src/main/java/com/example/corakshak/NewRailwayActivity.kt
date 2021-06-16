package com.example.corakshak

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
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
    var VacinationStatus: String = "Non-Vacinateddd"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_railway)

        //spinner start
        //spinner end
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

        //Vacination status

        val languages = resources.getStringArray(R.array.vaccinationStatus)

        // access the spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    VacinationStatus = languages[position]
                    Toast.makeText(this@NewRailwayActivity,
                        getString(R.string.selected_item) + " " +
                                "" + languages[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
        //Vacination end


        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            radioButton = findViewById(checkedId)
            Toast.makeText(this, "sel: "+ radioButton.text.toString(), Toast.LENGTH_SHORT).show()
        }

        date_btn.setOnClickListener {
            selectDate()
        }

        add_btn.setOnClickListener {
            addPNR()
        }



}

    //Date Picker Start
    private fun selectDate() {

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

        val datePickerDialog = DatePickerDialog(this@NewRailwayActivity, dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.datePicker.minDate = System.currentTimeMillis()-1000
        datePickerDialog.show()
    }

    private fun addPNR() {
        val PNR = pnrNo.editText?.text.toString()
        var name = name.editText?.text.toString()
        var Age = age.editText?.text.toString()
        var Address = address.editText?.text.toString()
        var Emailid = emailid.editText?.text.toString()


        if (PNR.isEmpty() ){
            pnrNo.error = "enter PNR no"
            return
        }
        database = FirebaseDatabase.getInstance().getReference("users")

        
        var Pnr_form = PnrFrom(PNR , name , Age , Address , Emailid, datefirebase, VacinationStatus)

        database.child("$currentuser/booking/$PNR").setValue(Pnr_form).addOnSuccessListener {

            Toast.makeText(this,"Done",Toast.LENGTH_SHORT).show()

            val intent = Intent(this@NewRailwayActivity, QRCodeActivity::class.java)
            intent.putExtra("uid", currentuser)
            intent.putExtra("pnr", PNR)
            startActivity(intent)
            finish()

        }.addOnFailureListener{

            Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()


        }

    }

}
