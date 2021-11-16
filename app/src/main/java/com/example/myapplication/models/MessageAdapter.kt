package com.example.myapplication.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class MessageAdapter<T>(
    private val items: List<Messages>,
    private val onItemClicked: (message: Messages) -> Unit): RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)
        return MyViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = items[position].toString()
        viewHolder.textView.setOnClickListener{
            onItemClicked(items.get(position))}
    }

    class MyViewHolder(itemView: View, private val onItemClicked: (message: Messages) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val textView: TextView = itemView.findViewById(R.id.textview_list_item)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            // gradle     implementation "androidx.recyclerview:recyclerview:1.2.1"

        }
    }
}