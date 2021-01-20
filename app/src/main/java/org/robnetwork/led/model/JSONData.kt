package org.robnetwork.led.model

import com.google.gson.annotations.SerializedName

data class ConfigJSONData(
    @SerializedName("freqMin")
    var freqMin: Int?,

    @SerializedName("freqMax")
    var freqMax: Int?,

    @SerializedName("meansMax")
    var meansMax: Float?,

    @SerializedName("meansMin")
    var meansMin: Float?,

    @SerializedName("fftCorrection")
    var fftCorrection: List<Float>,

    @SerializedName("color1")
    var color1: String,

    @SerializedName("color2")
    var color2: String,

    @SerializedName("brightness")
    var brightness: Int
)