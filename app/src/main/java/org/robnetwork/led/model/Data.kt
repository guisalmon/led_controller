package org.robnetwork.led.model

interface BaseData

data class MainData(
    val connexionStatus: ConnexionStatus = ConnexionStatus.OFF,
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

    enum class ConnexionStatus {
        UNREACHABLE,
        OFF,
        ON
    }
}