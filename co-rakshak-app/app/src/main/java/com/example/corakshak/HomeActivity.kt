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



//        var railway : Button = findViewById(R.id.new_railway_btn)
//
//        var logout : Button = findViewById<Button>(R.id.logout)
//        logout.setOnClickListener {
//            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
//        }
//
//        railway.setOnClickListener {
//            var i: Intent = Intent(this,NewRailwayActivity::class.java)
//            startActivity(i)
//        }
//
//        var webBtn= findViewById<Button>(R.id.web_page)
//        webBtn.setOnClickListener {
//            var i: Intent = Intent(this,WebViewActivity::class.java)
//            startActivity(i)
//        }

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
}