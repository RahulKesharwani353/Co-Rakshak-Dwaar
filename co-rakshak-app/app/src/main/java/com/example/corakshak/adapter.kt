package com.example.testapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.corakshak.R

class adapter(val listdataModel: List<dataModel>)  : RecyclerView.Adapter<adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card,parent,false)

        return  MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = listdataModel.get(position).title
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, listdataModel.get(position).title, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listdataModel.size
    }
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val title : TextView = view.findViewById(R.id.cardText)
    }
}