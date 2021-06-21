package com.example.corakshak


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class adapter(val listdataModel: List<dataModel>, val currentuser: String)  : RecyclerView.Adapter<adapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card,parent,false)

        return  MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = listdataModel.get(position).name
        holder.date.text = listdataModel.get(position).date
        holder.pnr.text = listdataModel.get(position).pnr
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, listdataModel.get(position).name, Toast.LENGTH_SHORT).show()
            val i = Intent(it.context, QRCodeActivity::class.java)
            i.putExtra("uid", currentuser)
            i.putExtra("pnr", listdataModel.get(position).pnr)
            it.context.startActivity(i)
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