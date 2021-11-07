package com.webninjas.wallpaper_advance.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso
import com.webninjas.wallpaper_advance.Categories_activity
import com.webninjas.wallpaper_advance.MainActivity.international.categoryname
import com.webninjas.wallpaper_advance.R
import com.webninjas.wallpaper_advance.models.categories_model

class categories_adapter(val context: Context, val list: MutableList<categories_model>) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflater: LayoutInflater? = LayoutInflater.from(parent.context)
        val view: View = inflater!!.inflate(R.layout.categories_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("sagsgaghrt", list[position].photo_url2)
        Log.d("sagsgaghrt", list[position].photo_url1)

        Picasso.get().load(list[position].photo_url2).into(object : com.squareup.picasso.Target {
            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.d("sagsgagsrhhrt", e.toString() + " eoorl")
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                // loaded bitmap is here (bitmap)

                holder.mainimage2.setImageBitmap(bitmap)
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

        })

        Picasso.get().load(list[position].photo_url1).fit().into(holder.mainimage)
        holder.title.text = list[position].title

        holder.layout.setOnClickListener {
            categoryname = list[position].title
            if (list[position].title == "AMOLED") {
                categoryname = "black wallpaper"
            }
            val intent: Intent = Intent(context, Categories_activity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mainimage: ShapeableImageView = itemView.findViewById(R.id.mainimage)
    var mainimage2: ShapeableImageView = itemView.findViewById(R.id.mainimage2)
    var title: TextView = itemView.findViewById(R.id.title)
    var layout: ConstraintLayout = itemView.findViewById(R.id.layout)
}
