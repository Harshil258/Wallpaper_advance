package com.webninjas.wallpaper_advance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.webninjas.wallpaper_advance.MainActivity.international.bottomBar
import com.webninjas.wallpaper_advance.adapters.mainimage_adapter
import com.webninjas.wallpaper_advance.interfaces.getdatas
import com.webninjas.wallpaper_advance.models.main_data_model
import com.webninjas.wallpaper_advance.models.main_model
import retrofit2.Call
import retrofit2.Response


class main_image : Fragment() {

    lateinit var adapter: mainimage_adapter
    lateinit var list: ArrayList<main_model>
    lateinit var listdummy: ArrayList<main_model>
    lateinit var recyclerview: RecyclerView
    lateinit var backtotop: ImageView
    var isLoading = false
    var page = 1


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
        listdummy = ArrayList()

        recyclerview = view.findViewById(R.id.recyclerview)
        var layoutmanger = GridLayoutManager(context, 2)
        recyclerview.layoutManager = layoutmanger
        adapter = mainimage_adapter(requireContext(), list)
        recyclerview.adapter = adapter

        backtotop = view.findViewById(R.id.backtotop)
        backtotop.setOnClickListener {
            recyclerview.smoothScrollToPosition(0);
        }


        recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && bottomBar.isShown) {
                    bottomBar.visibility = View.GONE
                    backtotop.visibility = View.VISIBLE

                } else if (dy < 0) {
                    bottomBar.visibility = View.VISIBLE
                    backtotop.visibility = View.GONE
                }

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == list.size - 1) {
                        //bottom of list!
                        getdata()
                        isLoading = true
                    }
                }
            }
        })
        getdata()

    }

    private fun getdata() {
        val photos = getdatas.curatedinterface.getsearchphotos("4k wallpaper for android", page, 80)
        photos.enqueue(object : retrofit2.Callback<main_data_model> {
            override fun onResponse(
                call: Call<main_data_model>,
                response: Response<main_data_model>
            ) {
                page = (0..response.body()!!.total_results / 80 - 5).random()
                adapter.notifyItemInserted(list.size - 1)
                Log.d("ertfsegrg", response.body().toString())
                for (i in 1 until response.body()?.photos!!.size) {
                    val photographer = response.body()!!.photos[i].photographer
                    val photographer_url = response.body()!!.photos[i].photographer_url
                    val original = response.body()!!.photos[i].src.original
                    val medium = response.body()!!.photos[i].src.medium

                    listdummy.add(main_model(photographer, photographer_url, original, medium))
                    listdummy.shuffle()
                }
                list.addAll(listdummy)
                listdummy.clear()
                adapter.notifyDataSetChanged()
                isLoading = false
            }

            override fun onFailure(call: Call<main_data_model>, t: Throwable) {
                Log.d("ertfsegrgf", "sghsdgdrgh")
            }
        })
    }
}