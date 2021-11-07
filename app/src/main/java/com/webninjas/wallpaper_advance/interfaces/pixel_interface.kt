package com.webninjas.wallpaper_advance.interfaces


import com.webninjas.wallpaper_advance.models.main_data_model
import com.webninjas.wallpaper_advance.models.unsplash_final
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

const val BASE_URL = "https://api.pexels.com/v1/"
const val UNSPLASH_URL = "https://api.unsplash.com/"


interface pixel_interface {

    @Headers("Authorization: 563492ad6f9170000100000158fc14b816de461ca6b31c1d5afa86b2")
    @GET("curated?orientation=landscape")
    fun getcuratedphotos(
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<main_data_model>

    @Headers("Authorization: 563492ad6f9170000100000158fc14b816de461ca6b31c1d5afa86b2")
    @GET("search?orientation=portrait")
    fun getsearchphotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<main_data_model>

    @Headers("Authorization: qHyRQr_c_rkxVQYCRUae3pwliKEoZyMj34HMQhWBX-8")
    @GET("search?orientation=portrait")
    fun getunspalshphotos(
        @Query("client_id") client_id: String,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") per_page: Int
    ): Call<unsplash_final>

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

object getunspalsh {
    val getunspalshinterface: pixel_interface

    init {
        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl(UNSPLASH_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        getunspalshinterface = retrofit.create(pixel_interface::class.java)
    }
}