    package com.example.corakshak

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.database.*


    class ProfileFragment : Fragment() {

    lateinit var currentuser : String
    lateinit var ref : DatabaseReference
    lateinit var Pname : TextView
    lateinit var email : TextView
    lateinit var gender : TextView
    lateinit var Ppnumber : TextView
    lateinit var Pcity : TextView
    lateinit var pDOB : TextView
    lateinit var btnLog : Button
    lateinit var firebaseAuth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val auth = FirebaseAuth.getInstance()
//        val authListener =
//            AuthStateListener { firebaseAuth ->
//                val firebaseUser = firebaseAuth.currentUser
//                if (firebaseUser != null) {
//                    val userId = firebaseUser.uid
//                    val userEmail = firebaseUser.email
//                }
//            }




        currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        Pname = view.findViewById(R.id.Pro_Name)
        email = view.findViewById(R.id.Pro_Email)
        gender = view.findViewById(R.id.Pro_gender)
        Ppnumber = view.findViewById(R.id.Pro_number)
        Pcity = view.findViewById(R.id.Pro_city)
        pDOB = view.findViewById(R.id.Pro_dob)
        btnLog = view.findViewById(R.id.logOut_btn_pro)

        btnLog.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finishAffinity()
        }
        var editBtn : Button = view.findViewById(R.id.edit_btn_pro)
        editBtn.setOnClickListener {
            startActivity(Intent(activity, UserInfoFormActivity2::class.java))
        }


        RetriveData()

        // Inflate the layout for this fragment
        return view
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    fun RetriveData(){

        ref = FirebaseDatabase.getInstance().getReference("UsersData").child(currentuser).child("profile")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                val name = dataSnapshot.child("name").getValue().toString()
                val Gender = dataSnapshot.child("gender").getValue().toString()
                val Email = dataSnapshot.child("email").getValue().toString()
                val DOB = dataSnapshot.child("dob").getValue().toString()
                val city = dataSnapshot.child("city").getValue().toString()
                val Pnumber = dataSnapshot.child("phonenumber").getValue().toString()
                val firebaseAuth = FirebaseAuth.getInstance()
                val firebaseUser = firebaseAuth.currentUser
                var phonenumber = firebaseUser?.phoneNumber
                Ppnumber.text = phonenumber
                Pname.text = name
                gender.text = Gender
                email.text = Email
                pDOB.text = DOB
                Pcity.text = city

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()

            }
        })
    }
}