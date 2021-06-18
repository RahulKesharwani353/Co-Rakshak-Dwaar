package com.example.corakshak

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView


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

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val railway : CardView = view.findViewById(R.id.new_railway_btn)

        railway.setOnClickListener {
            var i: Intent = Intent(activity,InstructionActivity::class.java)
            startActivity(i)
        }

        var bookVacc = view.findViewById<ImageButton>(R.id.book_vacc_btn)
        bookVacc.setOnClickListener {
            var i: Intent = Intent(activity,WebViewActivity::class.java)
            i.putExtra("site","https://www.cowin.gov.in/home")
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