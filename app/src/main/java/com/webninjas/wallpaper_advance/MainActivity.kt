package com.webninjas.wallpaper_advance

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.webninjas.wallpaper_advance.MainActivity.international.bottomBar
import com.webninjas.wallpaper_advance.adapters.Fragmentadapter
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var main_viewpager: ViewPager2
    private lateinit var fragmentlist: ArrayList<Fragment>
    private lateinit var fragmentadapter: Fragmentadapter
    lateinit var favourite: ImageView

    object international {
        lateinit var bottomBar: SmoothBottomBar
        lateinit var categoryname: String
        lateinit var main_img_url: String
        lateinit var main_img_small_url: String
        lateinit var mainid: String
        lateinit var photographer_url: String
        var directory_name = "Wallpaper"
        var Photographer = "Photographer"

        var favo = false

        var path = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        var RootDirectory = File("${path.absolutePath}/$directory_name")
        fun makedirectory() {

            RootDirectory.mkdirs()
            RootDirectory.createNewFile()

            Log.d("esgazadrsgsrwg", RootDirectory.absolutePath)


        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_viewpager = findViewById(R.id.main_viewpager)
        bottomBar = findViewById(R.id.bottomBar)
        favourite = findViewById(R.id.favourite)

        var onItemSelectedListener = bottomBar.onItemSelectedListener
        onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelect(pos: Int): Boolean {
                main_viewpager.currentItem = pos
                return true
            }
        }

        favourite.setOnClickListener {
            startActivity(Intent(this, favourite_activity::class.java))
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