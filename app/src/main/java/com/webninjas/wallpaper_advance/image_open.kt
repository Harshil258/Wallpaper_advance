package com.webninjas.wallpaper_advance

import android.app.AlertDialog
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.webninjas.wallpaper_advance.MainActivity.international.RootDirectory
import com.webninjas.wallpaper_advance.MainActivity.international.main_img_small_url
import com.webninjas.wallpaper_advance.MainActivity.international.main_img_url
import com.webninjas.wallpaper_advance.MainActivity.international.mainid
import com.webninjas.wallpaper_advance.MainActivity.international.makedirectory
import com.webninjas.wallpaper_advance.MainActivity.international.photographer_url
import com.webninjas.wallpaper_advance.database.UserRepository
import com.webninjas.wallpaper_advance.database.Wallpaper_entity
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur
import java.io.File
import java.io.FileOutputStream


class image_open : AppCompatActivity() {

    lateinit var mainimage: ImageView
    lateinit var imageView1: ImageView
    lateinit var imageView3: ImageView
    lateinit var customloder: CardView
    lateinit var Photographer: TextView
    lateinit var setback: ConstraintLayout
    lateinit var like: ConstraintLayout
    lateinit var mainlike: ImageView

    lateinit var download: ConstraintLayout
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
    lateinit var blurView1: BlurView
    private lateinit var blurView: BlurView
    val color = arrayOf(
        R.color.yelloworwhite,
        R.color.Indigo,
        R.color.Blue,
        R.color.Green,
        R.color.Yellow,
        R.color.Orange,
        R.color.Red
    )

    var colorcount = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_open)

        window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window?.statusBarColor = Color.TRANSPARENT

        var repository: UserRepository = UserRepository(this)

        customloder = findViewById<CardView>(R.id.customloder)
        imageView3 = findViewById(R.id.imageView3)
        imageView1 = findViewById(R.id.imageView1)
        mainlike = findViewById(R.id.mainlike)

        download = findViewById(R.id.download)
        Photographer = findViewById(R.id.Photographer)
        setback = findViewById(R.id.setback)
        like = findViewById(R.id.like)

        mainimage = findViewById(R.id.mainimage)
        mainimage.isDrawingCacheEnabled = true;
        val bottomsheet: View = findViewById(R.id.bottomSheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomsheet).apply {
            peekHeight = 150
            this.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        val radius = 2f
        val radius1 = 5f

        blurView1 = findViewById(R.id.blurView1)
        blurView = findViewById(R.id.blurView)
        val decorView = window.decorView
        val rootView = decorView.findViewById<View>(android.R.id.content) as ViewGroup

        for (i in repository.getAllUsers()) {
            if (i.mainid == mainid) {
                mainlike.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_24))
                Log.d("sdfgdsgdfg", "${i.mainid}     $mainid    liked")
                break

            } else {
                mainlike.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_border_24))
                Log.d("sdfgdsgdfg", "${i.mainid}     $mainid    not liked")

            }
        }

        like.setOnClickListener {
            if (repository.getAllUsers().contains(
                    Wallpaper_entity(
                        mainid, main_img_url,
                        main_img_small_url,
                        MainActivity.international.Photographer,
                        photographer_url
                    )
                )
            ) {
                try {
                    repository.deleteUser(
                        Wallpaper_entity(
                            mainid, main_img_url,
                            main_img_small_url,
                            MainActivity.international.Photographer,
                            photographer_url
                        )
                    )
                    mainlike.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_border_24))
                    Log.d("srgfhdfhdfh", "delete")
                    Log.d("srgfhdfhdfh", repository.getAllUsers().toString())
                    Toast.makeText(
                        this, "un liked", Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Log.d("srgfhdfhdfh", e.toString())
                }
            } else {
                try {
                    repository.insertUser(
                        Wallpaper_entity(
                            mainid, main_img_url,
                            main_img_small_url,
                            MainActivity.international.Photographer,
                            photographer_url
                        )
                    )
                    mainlike.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_favorite_24))

                    Log.d("srgfhdfhdfh", "inserted")
                    Log.d("srgfhdfhdfh", repository.getAllUsers().toString())
                    Toast.makeText(
                        this, "liked", Toast.LENGTH_SHORT
                    ).show()
                } catch (e: Exception) {
                    Log.d("srgfhdfhdfh", e.toString())
                }
            }


        }

        setback.setOnClickListener {
            val factory = LayoutInflater.from(this)
            val deleteDialogView: View = factory.inflate(R.layout.set_back_layout, null)
            val customdialog = AlertDialog.Builder(this).create()
            customdialog.setView(deleteDialogView)
            customdialog.setContentView(R.layout.set_back_layout)

            var homescreen = deleteDialogView.findViewById<CardView>(R.id.homescreen)
            var lockscreen = deleteDialogView.findViewById<CardView>(R.id.lockscreen)
            var both = deleteDialogView.findViewById<CardView>(R.id.both)

            var wallpaperManager = WallpaperManager.getInstance(this)
            homescreen.setOnClickListener {
                Log.d("Adgfsedgsd", "clicked")
                var imgpath = downloadimage()
                val bmOptions = BitmapFactory.Options()
                var bitmap = BitmapFactory.decodeFile(imgpath, bmOptions)
                wallpaperManager.setBitmap(bitmap)
                Toast.makeText(
                    this, "You can see your wallpaper", Toast.LENGTH_SHORT
                ).show()
                customdialog.dismiss()
            }

            lockscreen.setOnClickListener {
                Log.d("Adgfsedgsd", "lock")
                var imgpath = downloadimage()
                val bmOptions = BitmapFactory.Options()
                var bitmap = BitmapFactory.decodeFile(imgpath, bmOptions)
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                Toast.makeText(
                    this, "You can see your lock screen wallpaper", Toast.LENGTH_SHORT
                ).show()
                customdialog.dismiss()

            }

            both.setOnClickListener {
                var imgpath = downloadimage()
                val bmOptions = BitmapFactory.Options()
                var bitmap = BitmapFactory.decodeFile(imgpath, bmOptions)
                wallpaperManager.setBitmap(bitmap)
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_LOCK)
                Toast.makeText(
                    this, "You can see your wallpaper and lock screen wallpaper", Toast.LENGTH_SHORT
                ).show()
                customdialog.dismiss()
            }

            customdialog.show()

        }

        Photographer.text = MainActivity.international.Photographer
        val windowBackground = decorView.background

        blurView1.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(applicationContext))
            .setBlurRadius(radius)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)

        blurView.setupWith(rootView)
            .setFrameClearDrawable(windowBackground)
            .setBlurAlgorithm(RenderScriptBlur(applicationContext))
            .setBlurRadius(radius1)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)

        Log.d("gwsxegsrhrh", main_img_url)
        Picasso.get()
            .load(main_img_url)
            .into(mainimage, object : Callback {
                override fun onSuccess() {
                    Log.d("gwsxegsrhrh", "sucess")
                    customloder.visibility = View.INVISIBLE

                    mainimage.setOnClickListener {
                        if (colorcount == 0) {
                            Toast.makeText(
                                this@image_open,
                                "Hold on the image to get back original form",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        val animation: Animation = AnimationUtils.loadAnimation(
                            applicationContext,
                            R.anim.image_animation
                        )
                        mainimage.startAnimation(animation)
                        mainimage.setColorFilter(
                            ContextCompat.getColor(this@image_open, color[colorcount]),
                            android.graphics.PorterDuff.Mode.OVERLAY
                        )
                        if (colorcount == 6) {
                            colorcount = 0
                        } else {
                            colorcount++
                        }
                    }
                    download.setOnClickListener {

                        downloadimage()

                    }

                    mainimage.setOnLongClickListener {
                        val animation: Animation = AnimationUtils.loadAnimation(
                            applicationContext,
                            R.anim.image_animation
                        )
                        mainimage.startAnimation(animation)
                        mainimage.clearColorFilter()
                        Toast.makeText(this@image_open, "Here is normal image", Toast.LENGTH_SHORT)
                            .show()
                        return@setOnLongClickListener true
                    }

                }

                override fun onError(e: Exception?) {
                    Log.d("gwsxegsrhrh", e.toString() + "  Error")
                    customloder.visibility = View.INVISIBLE
                    Toast.makeText(this@image_open, "Something error", Toast.LENGTH_SHORT).show()
                }
            })


        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

                Log.d("sdrrhgdrhrhedr", slideOffset.toString())

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {

                Log.d("sdrrhgtyjdrhrhedr", newState.toString())
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
//                        blurView.setBackgroundResource(R.drawable.back)
                        blurView1.visibility = View.VISIBLE
                        imageView3.visibility = View.GONE
                        imageView1.visibility = View.GONE
                    }

                    BottomSheetBehavior.STATE_COLLAPSED -> {
//                        blurView.setBackgroundColor(Color.parseColor("#00000000"))
                        blurView1.visibility = View.GONE
                        imageView3.visibility = View.VISIBLE
                        imageView1.visibility = View.VISIBLE
                    }
                    else -> blurView1.visibility = View.GONE
                }
            }
        })

    }

    private fun downloadimage(): String {
        customloder.visibility = View.VISIBLE
        setback.isEnabled = false
        Photographer.isEnabled = false
        download.isEnabled = false

        setback.alpha = 0.5f
        Photographer.alpha = 0.5f
        download.alpha = 0.5f

        try {
            makedirectory()
            RootDirectory.mkdirs()
            var list = RootDirectory.listFiles()
            for (i in list) {
                Log.d("Sdgsgsdfgsdg", "${i.name.removeSuffix(".png")}         ${mainid}")
                if (i.name.removeSuffix(".png") == mainid) {
                    Toast.makeText(
                        applicationContext,
                        "Image has been downloaded already...",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    setback.isEnabled = true
                    Photographer.isEnabled = true
                    download.isEnabled = true

                    setback.alpha = 1f
                    Photographer.alpha = 1f
                    download.alpha = 1f
                    customloder.visibility = View.INVISIBLE
                    return "${RootDirectory.path}/${i.name}"
                }
            }

            var outStream: FileOutputStream? = null
            val bitmap = mainimage.drawingCache
            val fileName = "${MainActivity.international.mainid.toString()}.png"
            val outFile: File = File(RootDirectory.absolutePath, fileName)
            outStream = FileOutputStream(outFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
            outStream.flush()
            outStream.close()
            Log.d("esgazrsgsrwg", outFile.path)
            Toast.makeText(applicationContext, "Image has been downloaded...", Toast.LENGTH_SHORT)
                .show()
            setback.isEnabled = true
            Photographer.isEnabled = true
            download.isEnabled = true

            setback.alpha = 1f
            Photographer.alpha = 1f
            download.alpha = 1f
            customloder.visibility = View.INVISIBLE
            return outFile.path


        } catch (e: Exception) {
            Log.d("esgazrsgsdvrwg", e.toString())
            Toast.makeText(this@image_open, e.toString(), Toast.LENGTH_SHORT).show()
            setback.isEnabled = true
            Photographer.isEnabled = true
            download.isEnabled = true

            setback.alpha = 1f
            Photographer.alpha = 1f
            download.alpha = 1f
            customloder.visibility = View.INVISIBLE
            return null.toString()

        }
    }
}