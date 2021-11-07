package com.webninjas.wallpaper_advance.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Wallpaper_entity::class], version = 1, exportSchema = false)
abstract class mydatabase : RoomDatabase() {

    abstract fun Wallpaper_dao(): Wallpaper_dao?

    companion object {
        private var INSTANCE: mydatabase? = null

        fun getInstance(context: Context): mydatabase? {
            if (INSTANCE == null) {
                synchronized(mydatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        mydatabase::class.java, "likes.db"
                    ).allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}