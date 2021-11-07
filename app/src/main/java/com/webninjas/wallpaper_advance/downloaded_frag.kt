package com.webninjas.wallpaper_advance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.webninjas.wallpaper_advance.MainActivity.international.RootDirectory
import com.webninjas.wallpaper_advance.adapters.FileListAdapter
import java.io.File
import java.util.*


class downloaded_frag : Fragment(), FileListClickInterface {

//    private var RootDirectoryInstaShow =
//        File(
//            Environment.getExternalStorageDirectory()
//                .toString() + "/Download/instareeldownloder/Insta"
//        )

    private var fileArrayList: ArrayList<File>? = null
    private var fileListAdapter: FileListAdapter? = null
    lateinit var recyclervew: RecyclerView
    lateinit var swiperefresh: SwipeRefreshLayout
    lateinit var tv_NoResult: TextView


    companion object {
        private const val WRITE_INTERNAL_STORAGE = 22
        private const val READ_EXTERNAL_STORAGE = 1
    }


//    "android.permission.WRITE_INTERNAL_STORAGE""android.permission.READ_EXTERNAL_STORAGE"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_downloaded_frag, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    override fun onResume() {
        super.onResume()
        getAllFiles()
    }

    private fun initViews() {
        swiperefresh = view?.findViewById(R.id.swiperefresh)!!
        swiperefresh.setOnRefreshListener {
            Log.d("segfseg", "files.toString()")
            getAllFiles()
            swiperefresh.isRefreshing = false
        }
    }

    private fun getAllFiles() {
        recyclervew = view?.findViewById(R.id.rv_fileList)!!
        fileArrayList = ArrayList()
        Log.d("Drhdrhdrh", RootDirectory.toString())

        if (RootDirectory.exists()) {
            val files = RootDirectory.listFiles()

            Log.d("Drhdrhdrh", files!!.toString() + "null ")

            tv_NoResult = requireView().findViewById(R.id.tv_NoResult)
//            if (files.isEmpty()) {
//                tv_NoResult.visibility = View.VISIBLE
//            }
            if (files != null) {
                for (file in files) {
                    fileArrayList!!.add(file)
                }
                fileListAdapter = FileListAdapter(activity, fileArrayList, this@downloaded_frag)
                recyclervew.adapter = fileListAdapter
                if (fileArrayList!!.size != 0) {
                    tv_NoResult.visibility = View.GONE
                } else {
                    tv_NoResult.visibility = View.VISIBLE
                }
            }


        }
    }

    override fun getPosition(position: Int, file: File?) {
        val inNext = Intent(activity, FullViewActivity::class.java)
        inNext.putExtra("ImageDataFile", fileArrayList)
        inNext.putExtra("Position", position)
        requireActivity().startActivity(inNext)
    }
}