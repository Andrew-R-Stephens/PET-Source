package com.tritiumgaming.shared.data.investigation.model

import com.tritiumgaming.shared.data.weather.model.Temperature
import com.tritiumgaming.shared.data.weather.model.Temperature.TEMPERATURE_FREEZING_POINT
import com.tritiumgaming.shared.data.weather.model.TemperatureRange

data class TemperatureData(
    val current: Float = Temperature.TEMPERATURE_START_FUSEBOX_ENABLED,
    val previous: Float = current,
    val range: TemperatureRange = TemperatureRange(),
    val temporalGradient: Float = TEMPERATURE_FREEZING_POINT,
    val lastUpdate: Long = 0L
)