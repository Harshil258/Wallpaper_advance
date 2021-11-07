package com.webninjas.wallpaper_advance.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.webninjas.wallpaper_advance.MainActivity.international.Photographer
import com.webninjas.wallpaper_advance.MainActivity.international.favo
import com.webninjas.wallpaper_advance.MainActivity.international.main_img_small_url
import com.webninjas.wallpaper_advance.MainActivity.international.main_img_url
import com.webninjas.wallpaper_advance.MainActivity.international.mainid
import com.webninjas.wallpaper_advance.MainActivity.international.photographer_url
import com.webninjas.wallpaper_advance.R
import com.webninjas.wallpaper_advance.image_open
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
//        Glide.with(context).load(list[position].medium).into(holder.mainimage)
        Picasso.get().load(list[position].medium).into(holder.mainimage)
        if (favo) {

        } else {
            if (position == list.size - 1) {
                holder.SHOW_PROGRESS.visibility = View.VISIBLE
            }
        }

        holder.layout.setOnClickListener {
            main_img_url = list[position].original
            main_img_small_url = list[position].medium
            Photographer = list[position].photographer
            photographer_url = list[position].photographer_url
            mainid = list[position].id
            val intent: Intent = Intent(context, image_open::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}

class Mainviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var photographer: TextView
    var mainimage: ImageView
    var SHOW_PROGRESS: ProgressBar
    var layout: ConstraintLayout

    init {
        photographer = itemView.findViewById(R.id.photographer)
        mainimage = itemView.findViewById(R.id.mainimage)
        SHOW_PROGRESS = itemView.findViewById(R.id.SHOW_PROGRESS)
        layout = itemView.findViewById(R.id.layout)
    }

}
