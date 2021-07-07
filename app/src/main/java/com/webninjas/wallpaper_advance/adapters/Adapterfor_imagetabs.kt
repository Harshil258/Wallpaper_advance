package com.webninjas.wallpaper_advance.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.webninjas.wallpaper_advance.Main_frag
import com.webninjas.wallpaper_advance.image_categories
import com.webninjas.wallpaper_advance.main_image

class Adapterfor_imagetabs(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    item: Int,
    list: List<Fragment>
) :

    FragmentStateAdapter(fragmentManager, lifecycle) {

    var list: List<Fragment> = list
    var item: Int = item


    override fun getItemCount(): Int {
        return item
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return main_image()
            1 -> return image_categories()
        }
        return Main_frag()
    }
}