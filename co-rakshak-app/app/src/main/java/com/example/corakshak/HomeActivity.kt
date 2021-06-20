package com.example.corakshak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeActivity : AppCompatActivity() {
    lateinit var navigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        DataCheck()
        navigationView = findViewById(R.id.bottom_navigation)

        supportFragmentManager.beginTransaction().replace(R.id.body_container, HomeFragment())
            .commit()
        navigationView.setSelectedItemId(R.id.nav_home)
        navigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_home -> fragment = HomeFragment()
                R.id.nav_profile -> fragment = ProfileFragment()
                R.id.nav_history -> fragment = HistoryFragment()
            }
            if (fragment != null) {
                supportFragmentManager.beginTransaction().replace(R.id.body_container, fragment)
                    .commit()
            }
            true
        })
    }

    override fun onStart() {
        super.onStart()

    }


    private fun  DataCheck() {
      val  currentuser: String = FirebaseAuth.getInstance().currentUser!!.uid
        //var postRef = FirebaseDatabase.getInstance().getReference().child("UsersData")
        val rootRef = FirebaseDatabase.getInstance().reference
        //val fdbRefer = FirebaseDatabase.getInstance().getReference("UsersData/$currentuser")
        val userNameRef: DatabaseReference = rootRef.child("UsersData").child(currentuser).child("profile")
        val eventListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (!dataSnapshot.exists()) {
                    startActivity(Intent(applicationContext, UserInfoFormActivity2::class.java))
                    finish()
                }
                
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(applicationContext, "OPPs! Something Want Wrong", Toast.LENGTH_LONG).show()
            }
        }
        userNameRef.addListenerForSingleValueEvent(eventListener)
    }
}