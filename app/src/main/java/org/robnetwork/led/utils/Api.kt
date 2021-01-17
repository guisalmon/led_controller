package org.robnetwork.led.utils

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

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

    @GET("brightness/+")
    fun moreLight(): Call<Any>

    @GET("brightness/-")
    fun lessLight(): Call<Any>

    @GET("power/on")
    fun on(): Call<Any>

    @GET("power/off")
    fun off(): Call<Any>

    @GET("color1/{color}")
    fun color1(@Path("color") color: String): Call<Any>

    @GET("color2/{color}")
    fun color2(@Path("color") color: String): Call<Any>
}