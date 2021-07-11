package com.webninjas.wallpaper_advance

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.webninjas.wallpaper_advance.MainActivity.international.bottomBar
import com.webninjas.wallpaper_advance.adapters.Fragmentadapter
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar

class MainActivity : AppCompatActivity() {

    lateinit var main_viewpager: ViewPager2
    private lateinit var fragmentlist: ArrayList<Fragment>
    private lateinit var fragmentadapter: Fragmentadapter

    object international {
        lateinit var bottomBar: SmoothBottomBar

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_viewpager = findViewById(R.id.main_viewpager)
        bottomBar = findViewById(R.id.bottomBar)

        var onItemSelectedListener = bottomBar.onItemSelectedListener
        onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelect(pos: Int): Boolean {
                main_viewpager.currentItem = pos
                return true
            }
        }

        bottomBar.onItemSelectedListener = onItemSelectedListener
        main_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomBar.itemActiveIndex = position
                if (position == 0) {
//                    navigationView.setCheckedItem(R.id.Home)
                } else if (position == 1) {
//                    navigationView.setCheckedItem(R.id.Downloaded)
                } else if (position == 2) {
//                    navigationView.setCheckedItem(R.id.infoo)
                }
            }
        })

        init()


    }

    private fun init() {

        fragmentlist = ArrayList()
        fragmentlist.add(Main_frag())
        fragmentlist.add(downloaded_frag())
        fragmentlist.add(search_frag())

        val fragmentManager = supportFragmentManager
        fragmentadapter = Fragmentadapter(fragmentManager, lifecycle, 3, fragmentlist)
        main_viewpager.adapter = fragmentadapter


    }
}