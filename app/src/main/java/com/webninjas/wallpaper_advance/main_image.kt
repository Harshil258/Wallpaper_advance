package com.webninjas.wallpaper_advance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.webninjas.wallpaper_advance.MainActivity.international.bottomBar
import com.webninjas.wallpaper_advance.adapters.mainimage_adapter
import com.webninjas.wallpaper_advance.interfaces.getdatas
import com.webninjas.wallpaper_advance.interfaces.getunspalsh
import com.webninjas.wallpaper_advance.models.main_data_model
import com.webninjas.wallpaper_advance.models.main_model
import com.webninjas.wallpaper_advance.models.unsplash_final
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class main_image : Fragment() {

    lateinit var adapter: mainimage_adapter
    lateinit var list: ArrayList<main_model>
    lateinit var listdummy: ArrayList<main_model>

    lateinit var imageList: ArrayList<SlideModel>
    lateinit var recyclerview: RecyclerView
    lateinit var backtotop: ImageView
    var isLoading = false
    var page = 1
    var page2 = 1
    var secondcall = false

    lateinit var swipetorefresh: SwipeRefreshLayout

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

        swipetorefresh = view.findViewById(R.id.swipetorefresh)
        recyclerview = view.findViewById(R.id.recyclerview)
        var layoutmanger = GridLayoutManager(context, 2)
        recyclerview.layoutManager = layoutmanger
        adapter = mainimage_adapter(requireContext(), list)
        recyclerview.adapter = adapter
        layoutmanger.isSmoothScrollbarEnabled = true

        backtotop = view.findViewById(R.id.backtotop)
        backtotop.setOnClickListener {
            recyclerview.smoothScrollToPosition(0);
        }

        swipetorefresh.setOnRefreshListener {
            list.clear()
            getdata()
            swipetorefresh.isRefreshing = false
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
                    Log.d(
                        "agegsegg",
                        linearLayoutManager!!.findLastCompletelyVisibleItemPosition().toString()
                    )

                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == list.size - 1) {
                        //bottom of list!
                        getdata()
                        Log.d("agegsegg", "call")
                        isLoading = true
                    }
                }
            }
        })
        getdata()
        getdataforslider()

//        val imageList = ArrayList<SlideModel>() // Create image list
//
//        imageList.add(SlideModel("https://bit.ly/2YoJ77H", "The animal population decreased by 58 percent in 42 years."))
//        imageList.add(SlideModel("https://bit.ly/2BteuF2", "Elephants and tigers may become extinct."))
//        imageList.add(SlideModel("https://bit.ly/3fLJf72", "And people do that."))
//
//        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)
//        imageSlider.setImageList(imageList)

    }

    private fun getdataforslider() {
        imageList = ArrayList()

        var page = (0..80).random()
        val photos = getdatas.curatedinterface.getcuratedphotos(page, 10)
        photos.enqueue(object : Callback<main_data_model> {
            override fun onResponse(
                call: Call<main_data_model>,
                response: Response<main_data_model>
            ) {
                for (i in 1 until response.body()?.photos!!.size) {
                    val photographer = response.body()!!.photos[i].photographer
                    var mediaum =
                        response.body()!!.photos[i].src.medium.removePrefix("auto=compress&cs=tinysrgb&h=350")
                    mediaum += "auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200"

                    //https://images.pexels.com/photos/9700879/pexels-photo-9700879.jpeg?auto=compress&cs=tinysrgb&h=350
                    //https://images.pexels.com/photos/9700879/pexels-photo-9700879.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200
                    imageList.add(SlideModel(mediaum, photographer))
                }
                imageList.shuffle()

                val imageSlider = view?.findViewById<ImageSlider>(R.id.image_slider)
                imageSlider?.setImageList(imageList, ScaleTypes.FIT)
            }

            override fun onFailure(call: Call<main_data_model>, t: Throwable) {

            }

        })
    }

    private fun getdata() {
        secondcall = false
        val photos = getdatas.curatedinterface.getsearchphotos("wallpaper for android", page, 30)
        val unsplashphotos = getunspalsh.getunspalshinterface.getunspalshphotos(
            "qHyRQr_c_rkxVQYCRUae3pwliKEoZyMj34HMQhWBX-8",
            "wallpaper for android",
            page2, 30
        )

        photos.enqueue(object : retrofit2.Callback<main_data_model> {
            override fun onResponse(
                call: Call<main_data_model>,
                response: Response<main_data_model>
            ) {
                page = (0..response.body()!!.total_results / 30).random()
                Log.d("ertfsegrg", response.body().toString())
                for (i in 1 until response.body()?.photos!!.size) {
                    val photographer = response.body()!!.photos[i].photographer
                    val photographer_url = response.body()!!.photos[i].photographer_url
                    val original = response.body()!!.photos[i].src.original
                    val medium = response.body()!!.photos[i].src.medium
                    val id = response.body()!!.photos[i].id

                    listdummy.add(
                        main_model(
                            id.toString(),
                            photographer,
                            photographer_url,
                            original,
                            medium
                        )
                    )
                }

                Log.d("ertfsfdjdfgefgrgf", listdummy.size.toString())


                if (secondcall == false) {
                    secondcall = true
                } else {
                    listdummy.shuffle()
                    list.addAll(listdummy)
                    Log.d("agegsegg", "added all 1")
                    listdummy.clear()
                    adapter.notifyDataSetChanged()
                    isLoading = false
                    var nofound: ConstraintLayout = requireView().findViewById(R.id.nofound)
                    if (list.size != 0) {
                        nofound.visibility = View.GONE

                    } else {
                        nofound.visibility = View.VISIBLE
                    }
                }

            }

            override fun onFailure(call: Call<main_data_model>, t: Throwable) {
                Log.d("ertfsegrgf", "sghsdgdrgh")
            }
        })

        unsplashphotos.enqueue(object : retrofit2.Callback<unsplash_final> {
            override fun onResponse(
                call: Call<unsplash_final>,
                response: Response<unsplash_final>
            ) {
                Log.d("ergukltfsefgrgf", response.body().toString())

                page2 = (0..response.body()!!.photos.total_pages).random()
                Log.d("ertfsegrgf", response.body()!!.photos.results[0].id + " srhyrdrhttr")
                Log.d("ertfsefgrgf", response.body().toString())
                Log.d("ertfsefdsggrgf", page2.toString())
                Log.d("ertfsefdsggrgf", response.body()!!.photos.total_pages.toString())

                for (i in 1..response.body()!!.photos.results.size - 1) {
                    val photographer = response.body()!!.photos.results[i].user.name
                    val photographer_url = response.body()!!.photos.results[i].user.profile_image
                    val original = response.body()!!.photos.results[i].urls.full
                    val medium = response.body()!!.photos.results[i].urls.small
                    val id = response.body()!!.photos.results[i].id
                    Log.d("srrhgsrhszerh", medium.toString())
                    listdummy.add(
                        main_model(
                            id.toString(),
                            photographer, photographer_url.toString(),
                            original.toString(), medium.toString()
                        )
                    )
                }
                Log.d("ertfsfdjdfgefgrgf", listdummy.size.toString())

                if (secondcall == false) {
                    secondcall = true
                } else {
                    listdummy.shuffle()
                    list.addAll(listdummy)
                    Log.d("agegsegg", "added all 2")
                    listdummy.clear()
                    adapter.notifyDataSetChanged()
                    isLoading = false
                    var nofound: ConstraintLayout = requireView().findViewById(R.id.nofound)
                    if (list.size != 0) {
                        nofound.visibility = View.GONE

                    } else {
                        nofound.visibility = View.VISIBLE
                    }
                }
            }

            override fun onFailure(call: Call<unsplash_final>, t: Throwable) {
                Log.d("ertfsegrgf", "sghsdgdrgh")
            }

        })
    }
}