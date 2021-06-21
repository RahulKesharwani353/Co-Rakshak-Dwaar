package com.example.corakshak

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

       var currentuser: String = FirebaseAuth.getInstance().currentUser!!.uid
       val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("UsersData").child(currentuser)


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val railway : CardView = view.findViewById(R.id.new_railway_btn)
        val airport : CardView = view.findViewById(R.id.new_airport_btn)
        val bus : CardView = view.findViewById(R.id.new_bus_btn)
        val settingbtn : Button = view.findViewById(R.id.settinghome)
        val greeting : TextView = view.findViewById(R.id.greeting)
        val nameGreeting: TextView = view.findViewById(R.id.greeting_name)
        val sdf = SimpleDateFormat("HH")
        val currentDate = sdf.format(Date()).toString().toInt()
        if (currentDate in 1..11){
            greeting.text = "Good Morning,"
        }
        else if (currentDate in 12..17)
            greeting.text = "Good Afternoon,"
        else
            greeting.text = "Good Evening,"
        railway.setOnClickListener {
            var i: Intent = Intent(activity,InstructionActivity::class.java)
            startActivity(i)
        }

        ref.child("profile").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child("name").value.toString()
                nameGreeting.text = name

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        val bookVacc = view.findViewById<ImageButton>(R.id.book_vacc_btn)
        bookVacc.setOnClickListener {
            val i: Intent = Intent(activity,WebViewActivity::class.java)
            i.putExtra("site","https://www.cowin.gov.in/home")
            startActivity(i)
        }
        val bookTest = view.findViewById<ImageButton>(R.id.book_test_btn)
        bookTest.setOnClickListener {
            val i: Intent = Intent(activity,WebViewActivity::class.java)
            i.putExtra("site","https://www.icmr.gov.in/cteststrat.html")
            startActivity(i)
        }

        bus.setOnClickListener {
            Toast.makeText(activity, "Coming Soon", Toast.LENGTH_SHORT).show()
        }
        airport.setOnClickListener {
            Toast.makeText(activity, "Coming Soon", Toast.LENGTH_SHORT).show()
        }
        settingbtn.setOnClickListener{
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.body_container, SettingFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }



        return view
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}