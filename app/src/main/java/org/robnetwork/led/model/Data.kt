package org.robnetwork.led.model

import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import org.robnetwork.led.R

interface BaseData

data class MainData(
    val currentState: State? = null
): BaseData {
    enum class State(
        @DrawableRes
        val color: Int,
        @ColorRes
        val textColor: Int
    ) {
        DARK(R.color.black, R.color.white),
        WHITE(R.color.white, R.color.black),
        GRADIENT(R.drawable.gradient_color1_color2, R.color.white),
        COLOR1(R.color.colorPrimaryDark, R.color.white),
        COLOR2(R.color.colorAccent, R.color.black)
    }

}