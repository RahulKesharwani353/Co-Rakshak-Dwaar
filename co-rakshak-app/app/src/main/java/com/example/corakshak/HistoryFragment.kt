package com.example.corakshak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class HistoryFragment : Fragment() {

    var database: DatabaseReference? = null
   var  currentuser: String = FirebaseAuth.getInstance().currentUser!!.uid
    lateinit var adapter: adapter
    var listdataModel: ArrayList<dataModel> = ArrayList()

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
        addData()
        val view: View = inflater.inflate(R.layout.fragment_history, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = adapter(listdataModel)
        recyclerView.adapter = adapter
        return view

    }

    fun addData(){
        database = FirebaseDatabase.getInstance().getReference("users/$currentuser/booking");
        database!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val booking = dataSnapshot.getValue(dataModel::class.java)
                    if (booking != null) {
                        listdataModel.add(booking)
                    }
                    else
                        Toast.makeText(activity, "Emplty", Toast.LENGTH_SHORT).show()
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })

    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}