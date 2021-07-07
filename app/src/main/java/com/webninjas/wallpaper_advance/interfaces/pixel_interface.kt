package com.webninjas.wallpaper_advance.interfaces

import com.webninjas.wallpaper_advance.models.main_data_model
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val BASE_URL = "https://api.pexels.com/v1/"


interface pixel_interface {

    @Headers("Authorization: 563492ad6f9170000100000158fc14b816de461ca6b31c1d5afa86b2")
    @GET("curated?")
    fun getcuratedphotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<main_data_model>

}

object getdatas {

    val curatedinterface: pixel_interface

    init {
        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
        curatedinterface = retrofit.create(pixel_interface::class.java)
    }

}