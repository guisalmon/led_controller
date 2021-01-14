package org.robnetwork.led.model

interface BaseData

data class MainData(
    val currentState: String? = null
): BaseData