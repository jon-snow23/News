package com.vipin.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder as ViewHolder1

class NewsListAdapter( private val listener: NewsItemClick): RecyclerView.Adapter<ViewHolder>() {
    val items : ArrayList<NewsModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news , parent , false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder

    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = items[position]
        holder.title.text = currentItem.title
    }
    fun updateNews (updatedNews:ArrayList<NewsModel>) {
        items.clear()
        items.addAll(updatedNews)

        notifyDataSetChanged()
    }


}

interface NewsItemClick {
    fun onItemClicked(item:NewsModel)
}

class ViewHolder(item:View): ViewHolder1(item) {
    val title : TextView = itemView.findViewById(R.id.title)
}