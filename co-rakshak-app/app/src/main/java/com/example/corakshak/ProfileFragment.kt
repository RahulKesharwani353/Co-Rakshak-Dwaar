    package com.example.corakshak

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var currentuser : String
    lateinit var ref : DatabaseReference
    lateinit var Pname : TextView
    lateinit var email : TextView
    lateinit var gender : TextView
    lateinit var Ppnumber : TextView
    lateinit var Pcity : TextView
    lateinit var pDOB : TextView
    lateinit var btn : Button
    private lateinit var viewOfLayout: View



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //val view = inflater.inflate(R.layout.fragment_profile, container, false)
//        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//        view.Pro_Email.text = "Anuj Randi"
//        Pname = view.findViewById(R.id.Pro_Name)
//        email = view.findViewById(R.id.Pro_Email)
//        gender = view.findViewById(R.id.Pro_gender)
//        Ppnumber = view.findViewById(R.id.Pro_number)
//        Pcity = view.findViewById(R.id.Pro_city)
//        pDOB = view.findViewById(R.id.Pro_dob)
//        btn = view.findViewById(R.id.btnnnnn)
      //  Pname.text = "Randi Anuj"

//        btn.setOnClickListener{
//            Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
//
//        }

//        val view = inflater.inflate(R.layout.fragment_profile, container, false)
//        val railway : TextView = view.findViewById(R.id.text_name)
//        railway.setText("sdjhfva");

        //viewOfLayout = inflater.inflate(R.layout.fragment_profile, container, false)
        RetriveData()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    fun RetriveData(){
        currentuser = FirebaseAuth.getInstance().currentUser!!.uid
        ref = FirebaseDatabase.getInstance().getReference("UsersData").child(currentuser)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot){
                val fname = dataSnapshot.child("firstName").getValue().toString()
                val lname = dataSnapshot.child("lastName").getValue().toString()
                val Gender = dataSnapshot.child("gender").getValue().toString()
                val Email = dataSnapshot.child("email").getValue().toString()
                val DOB = dataSnapshot.child("dob").getValue().toString()
                val city = dataSnapshot.child("city").getValue().toString()
                val Pnumber = dataSnapshot.child("phonenumber").getValue().toString()

                Toast.makeText(activity, "$fname $lname $Gender", Toast.LENGTH_SHORT).show()

//                Pname.text = "$fname $lname"
//                gender.text = Gender
//                email.text = Email
//                pDOB.text = DOB
//                Pcity.text = city
//                Ppnumber.text = Pnumber

               // Pname.setText(fname + " " + lname)




            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()

            }
        })
    }
}