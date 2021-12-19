package com.example.headsupprep.API

import android.telecom.Call
import com.example.headsupprep.Model.Celebrity
import com.example.headsupprep.Model.CelebrityItem
import retrofit2.Retrofit
import retrofit2.http.*
import javax.security.auth.callback.Callback
import kotlin.random.Random

interface APIInterface {


    @GET("celebrities/")
    fun getCelebrities(): retrofit2.Call<Celebrity>

    @POST("celebrities/")
    fun addCelebrity(@Body celebrity: CelebrityItem) : retrofit2.Call<Celebrity>

    @GET("/celebrities/{id}")
    fun getCelebrity(@Path("id") id: Int): retrofit2.Call<CelebrityItem>

    @PUT("/celebrities/{id}")
    fun updateCelebrity(@Path("id") id : Int, @Body celebrityData : CelebrityItem) : retrofit2.Call<CelebrityItem>


    @DELETE("/celebrities/{id}")
    fun deleteCelebrity(@Path("id") id : Int) : retrofit2.Call<Void>
}