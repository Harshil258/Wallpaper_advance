package com.webninjas.wallpaper_advance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mancj.materialsearchbar.MaterialSearchBar
import com.webninjas.wallpaper_advance.adapters.mainimage_adapter
import com.webninjas.wallpaper_advance.interfaces.getdatas
import com.webninjas.wallpaper_advance.interfaces.getunspalsh
import com.webninjas.wallpaper_advance.models.main_data_model
import com.webninjas.wallpaper_advance.models.main_model
import com.webninjas.wallpaper_advance.models.unsplash_final
import retrofit2.Call
import retrofit2.Response


class search_frag : Fragment(), MaterialSearchBar.OnSearchActionListener {

    lateinit var adapter: mainimage_adapter
    lateinit var list: ArrayList<main_model>
    lateinit var listdummy: ArrayList<main_model>
    lateinit var recyclerview: RecyclerView
    lateinit var backtotop: ImageView
    var isLoading = false
    var page = 1
    lateinit var searchBar: MaterialSearchBar
    var search_txt: String = ""


    var page2 = 1
    var secondcall = false

    lateinit var swipetorefresh: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        listdummy = ArrayList()
        searchBar = view.findViewById(R.id.searchBar) as MaterialSearchBar

        swipetorefresh = view.findViewById(R.id.swipetorefresh)
        recyclerview = view.findViewById(R.id.recyclerview)
        var layoutmanger = GridLayoutManager(context, 2)
        recyclerview.layoutManager = layoutmanger
        adapter = mainimage_adapter(requireContext(), list)
        recyclerview.adapter = adapter

        backtotop = view.findViewById(R.id.backtotop)
        backtotop.setOnClickListener {
            recyclerview.smoothScrollToPosition(0);
        }

        swipetorefresh.setOnRefreshListener {
            list.clear()
            if (search_txt != "") {
                getdata(search_txt)
            }
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
                        getdata(search_txt)
                        isLoading = true
                    }
                }
            }
        })

        searchBar.setHint("Search you want")
        searchBar.setOnSearchActionListener(this)
        searchBar.setSpeechMode(false)


    }


//    private fun getdata(search_txt: String) {
//        Log.d("ertfsegdegrg", search_txt.toString())
//        val photos = getdatas.curatedinterface.getsearchphotos(search_txt, page, 20)
//        photos.enqueue(object : retrofit2.Callback<main_data_model> {
//            override fun onResponse(
//                call: Call<main_data_model>,
//                response: Response<main_data_model>
//            ) {
//                adapter.notifyItemInserted(list.size - 1)
//                for (i in 1 until response.body()?.photos!!.size) {
//                    val photographer = response.body()!!.photos[i].photographer
//                    val photographer_url = response.body()!!.photos[i].photographer_url
//                    val original = response.body()!!.photos[i].src.original
//                    val medium = response.body()!!.photos[i].src.medium
//
//                    list.add(main_model(photographer, photographer_url, original, medium))
//                }
//
//                adapter.notifyDataSetChanged()
//                page++
//                isLoading = false
//
//                var nofound : ConstraintLayout = requireView().findViewById(R.id.nofound)
//
//                if (list.isNotEmpty()) {
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


    private fun getdata(search_txt: String) {
        secondcall = false
        val photos = getdatas.curatedinterface.getsearchphotos(search_txt, page, 30)
        val unsplashphotos = getunspalsh.getunspalshinterface.getunspalshphotos(
            "qHyRQr_c_rkxVQYCRUae3pwliKEoZyMj34HMQhWBX-8",
            search_txt,
            page2, 30
        )

        photos.enqueue(object : retrofit2.Callback<main_data_model> {
            override fun onResponse(
                call: Call<main_data_model>,
                response: Response<main_data_model>
            ) {
                page = (0..response.body()!!.page).random()
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
                            photographer, photographer_url, original, medium
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


    override fun onSearchStateChanged(enabled: Boolean) {
        search_txt = ""
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        search_txt = ""
        search_txt = text.toString()
        if (text.toString().isEmpty()) {
            Toast.makeText(context, "Please put something in searchbar", Toast.LENGTH_LONG).show()
        } else {
            list.clear()
            page = 1
            adapter.notifyDataSetChanged()
            getdata(search_txt)
        }
    }


    override fun onButtonClicked(buttonCode: Int) {

    }


}