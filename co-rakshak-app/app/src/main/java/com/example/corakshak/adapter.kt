package com.example.corakshak


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
        holder.name.text = listdataModel.get(position).name
        holder.date.text = listdataModel.get(position).pnr
        holder.pnr.text = listdataModel.get(position).date
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, listdataModel.get(position).name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listdataModel.size
    }
    class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name : TextView = view.findViewById(R.id.cardName)
        val date : TextView = view.findViewById(R.id.cardDate)
        val pnr : TextView = view.findViewById(R.id.cardPnr)
    }
}