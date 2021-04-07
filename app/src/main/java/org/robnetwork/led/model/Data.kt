package org.robnetwork.led.model

import android.widget.Button
import androidx.core.content.ContextCompat
import org.robnetwork.led.R

interface BaseData

data class MainData(
    val currentState: State? = null,
    val config: ConfigJSONData? = null,
    val levels: LevelsJSONData? = null
) : BaseData {
    enum class State {
        DARK,
        GRADIENT,
        COLOR1,
        COLOR2,
        EQUALIZER
    }

}