package com.webninjas.wallpaper_advance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webninjas.wallpaper_advance.adapters.categories_adapter
import com.webninjas.wallpaper_advance.models.categories_model


class image_categories : Fragment() {

    lateinit var recyclerview: RecyclerView
    lateinit var adapter: categories_adapter
    lateinit var list: ArrayList<categories_model>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()

        recyclerview = view.findViewById(R.id.recyclerview)
        var layoutmanger = GridLayoutManager(context, 2)
        recyclerview.layoutManager = layoutmanger
        adapter = categories_adapter(requireContext(), list)
        recyclerview.adapter = adapter

        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && MainActivity.international.bottomBar.isShown) {
                    MainActivity.international.bottomBar.visibility = View.GONE

                } else if (dy < 0) {
                    MainActivity.international.bottomBar.visibility = View.VISIBLE
                }
            }
        })

        list.add(
            categories_model(
                "https://images.pexels.com/photos/1918290/pexels-photo-1918290.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "https://images.pexels.com/photos/3727771/pexels-photo-3727771.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "AMOLED"
            )
        )
        list.add(
            categories_model(
                "https://images.pexels.com/photos/1496373/pexels-photo-1496373.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "https://images.pexels.com/photos/2033997/pexels-photo-2033997.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "NATURE"
            )
        )

        list.add(
            categories_model(
                "https://images.pexels.com/photos/2272853/pexels-photo-2272853.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "https://images.pexels.com/photos/1845534/pexels-photo-1845534.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "Raw Portrait Photos"
            )
        )

        list.add(
            categories_model(
                "https://images.pexels.com/photos/3283186/pexels-photo-3283186.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "https://images.pexels.com/photos/2693529/pexels-photo-2693529.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "Beautiful Zoom Backgrounds"
            )
        )

        list.add(
            categories_model(
                "https://images.pexels.com/photos/221189/pexels-photo-221189.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "https://images.pexels.com/photos/3582/abstract-glare-visual-art.jpg?auto=compress&cs=tinysrgb&h=650&w=940",
                "Abstract Zoom Backgrounds"
            )
        )

        list.add(
            categories_model(
                "https://images.pexels.com/photos/3052361/pexels-photo-3052361.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "https://images.pexels.com/photos/3623207/pexels-photo-3623207.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "wallpaper 4k"
            )
        )


        list.add(
            categories_model(
                "https://images.pexels.com/photos/5007442/pexels-photo-5007442.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "https://images.pexels.com/photos/561463/pexels-photo-561463.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "wallpaper for iphone"
            )
        )


        list.add(
            categories_model(
                "https://images.pexels.com/photos/799443/pexels-photo-799443.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "https://images.pexels.com/photos/1723637/pexels-photo-1723637.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "mobile wallpaper"
            )
        )

        list.add(
            categories_model(
                "https://images.pexels.com/photos/556662/pexels-photo-556662.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "https://images.pexels.com/photos/1122639/pexels-photo-1122639.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "love wallpaper mobile"
            )
        )


        var nofound: ConstraintLayout = requireView().findViewById(R.id.nofound)
        if (list.size != 0) {
            nofound.visibility = View.GONE

        } else {
            nofound.visibility = View.VISIBLE
        }

//        https://images.pexels.com/photos/347139/pexels-photo-347139.jpeg?   auto=compress&cs=tinysrgb&h=350


    }
}