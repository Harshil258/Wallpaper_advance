package com.webninjas.wallpaper_advance.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Likes")
data class Wallpaper_entity(
    @PrimaryKey
    @ColumnInfo(name = "mainid") var mainid: String,
    @ColumnInfo(name = "main_img_url") var main_img_url: String,
    @ColumnInfo(name = "main_img_small_url") var main_img_small_url: String,
    @ColumnInfo(name = "Photographer") var Photographer: String,
    @ColumnInfo(name = "photographer_url") var photographer_url: String,
)
