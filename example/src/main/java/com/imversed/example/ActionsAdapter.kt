package com.imversed.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.imversed.example.R

class ActionsAdapter(
    private val onClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<ActionsAdapter.ViewHolder>() {
    private var items = listOf<Int>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view.findViewById(R.id.textView) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_simple_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            itemView.setOnClickListener {
                onClickListener.invoke(position)
            }
            textView.setText(items[position])
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items: List<Int>) {
        this.items = items
        notifyDataSetChanged()
    }
}
