package com.webninjas.wallpaper_advance

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.webninjas.wallpaper_advance.MainActivity.international.categoryname
import com.webninjas.wallpaper_advance.adapters.mainimage_adapter
import com.webninjas.wallpaper_advance.interfaces.getdatas
import com.webninjas.wallpaper_advance.interfaces.getunspalsh
import com.webninjas.wallpaper_advance.models.main_data_model
import com.webninjas.wallpaper_advance.models.main_model
import com.webninjas.wallpaper_advance.models.unsplash_final
import retrofit2.Call
import retrofit2.Response

class Categories_activity : AppCompatActivity() {

    lateinit var adapter: mainimage_adapter
    lateinit var list: ArrayList<main_model>
    lateinit var listdummy: ArrayList<main_model>
    lateinit var recyclerview: RecyclerView


    var isLoading = false
    var page = 1
    lateinit var backtotop: ImageView
    var page2 = 1
    var secondcall = false

    lateinit var swipetorefresh: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)
        list = ArrayList()
        listdummy = ArrayList()

        swipetorefresh = findViewById(R.id.swipetorefresh)
        recyclerview = findViewById(R.id.recyclerview)
        var layoutmanger = GridLayoutManager(this, 2)
        recyclerview.layoutManager = layoutmanger
        adapter = mainimage_adapter(this, list)
        recyclerview.adapter = adapter

        backtotop = findViewById(R.id.backtotop)
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
                if (dy > 0 && MainActivity.international.bottomBar.isShown) {
                    MainActivity.international.bottomBar.visibility = View.GONE
                    backtotop.visibility = View.VISIBLE

                } else if (dy < 0) {
                    MainActivity.international.bottomBar.visibility = View.VISIBLE
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

//    private fun getdata() {
//        val photos = getdatas.curatedinterface.getsearchphotos(categoryname, page, 80)
//        photos.enqueue(object : retrofit2.Callback<main_data_model> {
//            override fun onResponse(
//                call: Call<main_data_model>,
//                response: Response<main_data_model>
//            ) {
////                page = (0..response.body()!!.total_results / 80).random()
//                adapter.notifyItemInserted(list.size - 1)
//                Log.d("ertfrtjsegrg", response.body().toString())
//                Log.d("ertfrtjsegrg", categoryname)
//                for (i in 1 until response.body()?.photos!!.size) {
//                    val photographer = response.body()!!.photos[i].photographer
//                    val photographer_url = response.body()!!.photos[i].photographer_url
//                    val original = response.body()!!.photos[i].src.original
//                    val medium = response.body()!!.photos[i].src.medium
//
//                    list.add(main_model(photographer, photographer_url, original, medium))
//                }
//                adapter.notifyDataSetChanged()
//                isLoading = false
//                page++
//
//                var nofound : ConstraintLayout = findViewById(R.id.nofound)
//                if (list.size != 0) {
//                    nofound.visibility = View.GONE
//
//                } else {
//                    nofound.visibility = View.VISIBLE
//                }
//            }
//
//            override fun onFailure(call: Call<main_data_model>, t: Throwable) {
//                Log.d("ertfsegrgf", "sghsdgdrgh")
//            }
//        })
//    }

    private fun getdata() {
        secondcall = false
        val photos = getdatas.curatedinterface.getsearchphotos(categoryname, page, 30)
        val unsplashphotos = getunspalsh.getunspalshinterface.getunspalshphotos(
            "qHyRQr_c_rkxVQYCRUae3pwliKEoZyMj34HMQhWBX-8",
            categoryname,
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
                    var nofound: ConstraintLayout = findViewById(R.id.nofound)
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

                page2 = (0..response.body()!!.photos.total / 30).random()
                Log.d("ertfsegrgf", response.toString() + " srhyrdrhttr")
                Log.d("ertfsefgrgf", response.body().toString())
                Log.d("ertfsefgrgf", call.toString())

                for (i in 1..response.body()!!.photos.results.size - 1) {
                    val photographer = response.body()!!.photos.results[i].user.name
                    val photographer_url = response.body()!!.photos.results[i].user.profile_image
                    val original = response.body()!!.photos.results[i].urls.full
                    val medium = response.body()!!.photos.results[i].urls.small
                    val id = response.body()!!.photos.results[i].id

                    Log.d("srrhgsrhszerh", medium.toString())
                    listdummy.add(
                        main_model(
                            id,
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
                    var nofound: ConstraintLayout = findViewById(R.id.nofound)
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