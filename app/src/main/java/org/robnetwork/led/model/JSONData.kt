package org.robnetwork.led.model

import com.google.gson.annotations.SerializedName

data class LevelsJSONData(
    @SerializedName("audioLvls")
    var levels: List<List<Float>>,

    @SerializedName("memSize")
    var memSize: Int
) {
    fun reverseLevels() = Array(12) { Array(memSize) { 0f } }.apply {
        levels.forEachIndexed { index, list ->
            list.forEachIndexed { column, lvl ->
                this[column][index] = lvl
            }
        }
    }
}

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
    var brightness: Int,

    @SerializedName("meanMaxLvls")
    var meanMaxLvls: MutableList<Float>,

    @SerializedName("meanMinLvls")
    var meanMinLvls: MutableList<Float>,

    @SerializedName("autoMinMax")
    var autoMinMax: Boolean
)