package com.webninjas.wallpaper_advance.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.webninjas.wallpaper_advance.R
import com.webninjas.wallpaper_advance.models.main_model

class mainimage_adapter(val context: Context, val list: MutableList<main_model>) :
    RecyclerView.Adapter<Mainviewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Mainviewholder {
        var inflater: LayoutInflater? = LayoutInflater.from(parent.context)
        val view: View = inflater!!.inflate(R.layout.image_item, parent, false)
        return Mainviewholder(view)
    }

    override fun onBindViewHolder(holder: Mainviewholder, position: Int) {
        holder.photographer.text = list[position].photographer
        Glide.with(context).load(list[position].medium).into(holder.mainimage)
        if (position == list.size) {
            holder.SHOW_PROGRESS.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class Mainviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var photographer: TextView
    lateinit var mainimage: ImageView
    lateinit var SHOW_PROGRESS: ProgressBar

    init {
        photographer = itemView.findViewById(R.id.photographer)
        mainimage = itemView.findViewById(R.id.mainimage)
        SHOW_PROGRESS = itemView.findViewById(R.id.SHOW_PROGRESS)
    }

}
