package com.tritiumgaming.shared.data.weather.model

data class TemperatureRange(
    val low: Float = 0f,
    val high: Float = 0f
)

fun TemperatureRange.fahrenheit(): TemperatureRange =
    TemperatureRange(
        low = (low * 1.8f) + 32,
        high = (high * 1.8f)
    )

fun TemperatureRange.celsius(): TemperatureRange = this