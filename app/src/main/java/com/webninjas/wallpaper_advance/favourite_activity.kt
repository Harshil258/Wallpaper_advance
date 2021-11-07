package com.webninjas.wallpaper_advance

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.webninjas.wallpaper_advance.MainActivity.international.favo
import com.webninjas.wallpaper_advance.adapters.mainimage_adapter
import com.webninjas.wallpaper_advance.database.UserRepository
import com.webninjas.wallpaper_advance.models.main_model

class favourite_activity : AppCompatActivity() {

    lateinit var swipetorefresh: SwipeRefreshLayout
    lateinit var adapter: mainimage_adapter
    lateinit var list: ArrayList<main_model>
    lateinit var recyclerview: RecyclerView
    lateinit var backtotop: ImageView
    lateinit var back: ImageView
    lateinit var repository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)

        favo = true
        list = ArrayList()
        repository = UserRepository(this)
        swipetorefresh = findViewById(R.id.swipetorefresh)
        recyclerview = findViewById(R.id.recyclerview)
        var layoutmanger = GridLayoutManager(this, 2)
        recyclerview.layoutManager = layoutmanger
        adapter = mainimage_adapter(this, list)
        recyclerview.adapter = adapter
        layoutmanger.isSmoothScrollbarEnabled = true

        backtotop = findViewById(R.id.backtotop)
        back = findViewById(R.id.back)
        backtotop.setOnClickListener {
            recyclerview.smoothScrollToPosition(0);
        }
        back.setOnClickListener {
            finish()
        }

        swipetorefresh.setOnRefreshListener {
            list.clear()
            getdata()
            swipetorefresh.isRefreshing = false
        }
        getdata()

    }

    private fun getdata() {
        list.clear()
        for (i in repository.getAllUsers()) {
            list.add(
                main_model(
                    i.mainid,
                    i.Photographer,
                    i.photographer_url,
                    i.main_img_url,
                    i.main_img_small_url
                )
            )
        }
        adapter.notifyDataSetChanged()
        var nofound: ConstraintLayout = findViewById(R.id.nofound)
        if (list.size != 0) {
            nofound.visibility = View.GONE
            backtotop.visibility = View.VISIBLE

        } else {
            nofound.visibility = View.VISIBLE
            backtotop.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        favo = true
        getdata()
    }

    override fun onStop() {
        super.onStop()
        favo = false
    }

}