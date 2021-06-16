package com.example.corakshak

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.adapter
import com.example.testapp.dataModel

class HistoryFragment : Fragment() {


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
        listdataModel.add(dataModel("Anuj"))
        listdataModel.add(dataModel("Rahul"))
        listdataModel.add(dataModel("surayansh"))
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