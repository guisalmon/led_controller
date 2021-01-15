package org.robnetwork.led.utils

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("white")
    fun white(): Call<Any>

    @GET("dark")
    fun dark(): Call<Any>

    @GET("gradient")
    fun gradient(): Call<Any>

    @GET("color1")
    fun color1(): Call<Any>

    @GET("color2")
    fun color2(): Call<Any>
}