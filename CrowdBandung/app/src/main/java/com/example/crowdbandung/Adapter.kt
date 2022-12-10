package com.example.crowdbandung

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val dataList : ArrayList<CctvLinkItem>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val cctvTitle : TextView = view.findViewById(R.id.link_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        LayoutInflater.from(parent.context).inflate(R.layout.single_view, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]

        holder.cctvTitle.text = data.title
    }

    override fun getItemCount(): Int = dataList.size

}