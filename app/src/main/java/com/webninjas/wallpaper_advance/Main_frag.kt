package com.webninjas.wallpaper_advance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.webninjas.wallpaper_advance.adapters.Adapterfor_imagetabs
import me.ibrahimsn.lib.OnItemSelectedListener
import me.ibrahimsn.lib.SmoothBottomBar

class Main_frag : Fragment() {

    lateinit var main_viewpager: ViewPager2
    lateinit var bottomBar: SmoothBottomBar
    private lateinit var fragmentlist: ArrayList<Fragment>
    private lateinit var fragmentadapter: Adapterfor_imagetabs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        main_viewpager = view.findViewById(R.id.main_frag_viewpager)
        bottomBar = view.findViewById(R.id.frag_bottomBar)

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
                }
            }
        })

        init()


    }

    private fun init() {

        fragmentlist = ArrayList()
        fragmentlist.add(main_image())
        fragmentlist.add(image_categories())

        val fragmentManager = requireActivity().supportFragmentManager
        fragmentadapter = Adapterfor_imagetabs(fragmentManager, lifecycle, 2, fragmentlist)
        main_viewpager.adapter = fragmentadapter


    }
}