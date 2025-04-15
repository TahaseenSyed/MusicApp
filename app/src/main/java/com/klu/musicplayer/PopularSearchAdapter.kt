package com.klu.musicplayer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class PopularSearchAdapter(private val searchList: List<String>) :
    RecyclerView.Adapter<PopularSearchAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val searchItem: TextView = view.findViewById(R.id.searchItem)
        val cardView: CardView = view.findViewById(R.id.searchItemCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.searchItem.text = searchList[position]
    }

    override fun getItemCount(): Int {
        return searchList.size
    }
}
