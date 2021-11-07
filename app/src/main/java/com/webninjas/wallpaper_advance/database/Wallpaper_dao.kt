package com.webninjas.wallpaper_advance.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Wallpaper_dao {

    @Query("SELECT * FROM Likes")
    fun getalllikes(): List<Wallpaper_entity>

    @Insert
    fun add(wallpaper_entity: Wallpaper_entity)

    @Delete
    fun delete(wallpaper_entity: Wallpaper_entity)
}