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
    enum class State(
        val colorButton: (Button) -> Unit
    ) {
        DARK({ button ->
            button.setTextColor(ContextCompat.getColor(button.context, R.color.white))
            button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.black))
        }),
        WHITE({ button ->
            button.setTextColor(ContextCompat.getColor(button.context, R.color.black))
            button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.white))
        }),
        GRADIENT({ button ->
            button.setTextColor(ContextCompat.getColor(button.context, R.color.white))
            button.background =
                ContextCompat.getDrawable(button.context, R.drawable.gradient_color1_color2)
        }),
        COLOR1({ button ->
            button.setTextColor(ContextCompat.getColor(button.context, R.color.white))
            button.setBackgroundColor(
                ContextCompat.getColor(
                    button.context,
                    R.color.colorPrimaryDark
                )
            )
        }),
        COLOR2({ button ->
            button.setTextColor(ContextCompat.getColor(button.context, R.color.black))
            button.setBackgroundColor(ContextCompat.getColor(button.context, R.color.colorAccent))
        })
    }

}