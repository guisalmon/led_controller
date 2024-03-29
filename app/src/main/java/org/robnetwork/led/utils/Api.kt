package org.robnetwork.led.utils

import org.robnetwork.led.model.ConfigJSONData
import org.robnetwork.led.model.LevelsJSONData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @GET("equalizer")
    fun equalizer(): Call<Any>

    @GET("brightness/+")
    fun moreLight(): Call<ConfigJSONData>

    @GET("brightness/-")
    fun lessLight(): Call<ConfigJSONData>

    @GET("brightness/{value}")
    fun brightness(@Path("value") value: Int): Call<ConfigJSONData>

    @GET("power/on")
    fun on(): Call<ConfigJSONData>

    @GET("power/off")
    fun off(): Call<ConfigJSONData>

    @GET("toggle/sound")
    fun toggleSound(): Call<Any>

    @GET("noise_start")
    fun noiseStart(): Call<Any>

    @GET("noise_reuse")
    fun noiseReuse(): Call<Any>

    @GET("config")
    fun config(): Call<ConfigJSONData>

    @POST("config")
    fun updateConfig(@Body config: ConfigJSONData): Call<ConfigJSONData>

    @GET("color1/{color}")
    fun color1(@Path("color") color: String): Call<ConfigJSONData>

    @GET("color2/{color}")
    fun color2(@Path("color") color: String): Call<ConfigJSONData>

    @GET("levels/stripes")
    fun levels(): Call<LevelsJSONData>

    @GET("levels/toggle")
    fun toggleAutoLevels(): Call<ConfigJSONData>

    @GET("sensibility/+")
    fun increaseSensibility(): Call<ConfigJSONData>

    @GET("sensibility/reset")
    fun resetSensibility(): Call<ConfigJSONData>

    @GET("source/toggle")
    fun toggleSource(): Call<ConfigJSONData>

    @GET("ambient/on")
    fun ambientOn(): Call<ConfigJSONData>

    @GET("ambient/off")
    fun ambientOff(): Call<ConfigJSONData>

    @GET("eq/on")
    fun eqOn(): Call<ConfigJSONData>

    @GET("eq/off")
    fun eqOff(): Call<ConfigJSONData>

    @GET("clock/backlight")
    fun backlightToggle(): Call<ConfigJSONData>
}