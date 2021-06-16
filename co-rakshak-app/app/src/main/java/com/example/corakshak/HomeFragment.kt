package com.example.corakshak

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast


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

        var view = inflater.inflate(R.layout.fragment_home, container, false)

        var railway : Button = view.findViewById(R.id.new_railway_btn)

        var logout : Button = view.findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            Toast.makeText(activity, "logout", Toast.LENGTH_SHORT).show()
        }

        railway.setOnClickListener {
            var i: Intent = Intent(activity,NewRailwayActivity::class.java)
            startActivity(i)
        }

        var webBtn= view.findViewById<Button>(R.id.web_page)
        webBtn.setOnClickListener {
            var i: Intent = Intent(activity,WebViewActivity::class.java)
            startActivity(i)
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