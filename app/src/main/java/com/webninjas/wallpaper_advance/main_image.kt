package com.webninjas.wallpaper_advance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webninjas.wallpaper_advance.adapters.mainimage_adapter
import com.webninjas.wallpaper_advance.interfaces.getdatas
import com.webninjas.wallpaper_advance.models.main_data_model
import com.webninjas.wallpaper_advance.models.main_model
import retrofit2.Call
import retrofit2.Response


class main_image : Fragment() {

    lateinit var adapter: mainimage_adapter
    lateinit var list: ArrayList<main_model>
    lateinit var recyclerview: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()

        recyclerview = view.findViewById(R.id.recyclerview)
        var layoutmanger = GridLayoutManager(context, 2)
        recyclerview.layoutManager = layoutmanger
        adapter = mainimage_adapter(requireContext(), list)
        recyclerview.adapter = adapter


        val photos = getdatas.curatedinterface.getcuratedphotos(1, 60)
        photos.enqueue(object : retrofit2.Callback<main_data_model> {
            override fun onResponse(
                call: Call<main_data_model>,
                response: Response<main_data_model>
            ) {
                Log.d("ertfsegrg", response.body().toString())
                for (i in 1 until response.body()?.photos!!.size) {
                    val photographer = response.body()!!.photos[i].photographer
                    val photographer_url = response.body()!!.photos[i].photographer_url
                    val original = response.body()!!.photos[i].src.original
                    val medium = response.body()!!.photos[i].src.medium

                    list.add(main_model(photographer, photographer_url, original, medium))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<main_data_model>, t: Throwable) {
                Log.d("ertfsegrgf", "sghsdgdrgh")
            }
        })


    }
}