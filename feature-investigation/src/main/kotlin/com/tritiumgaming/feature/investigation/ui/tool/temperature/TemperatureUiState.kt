package com.tritiumgaming.feature.investigation.ui.tool.temperature

import com.tritiumgaming.shared.data.weather.model.Temperature
import com.tritiumgaming.shared.data.weather.model.Temperature.TEMPERATURE_FREEZING_BREATH
import com.tritiumgaming.shared.data.weather.model.Temperature.TEMPERATURE_FREEZING_POINT
import com.tritiumgaming.shared.data.weather.model.TemperatureRange

data class TemperatureUiState(
    val range: TemperatureRange = TemperatureRange(),
    val temporalGradient: Float = TEMPERATURE_FREEZING_POINT,
    val current: Float = Temperature.TEMPERATURE_START_FUSEBOX_ENABLED,
    val currentAsString: String = "%4.1f".format(current),
) {

    val isFreezingBreath: Boolean
        get() = current <= TEMPERATURE_FREEZING_BREATH

    val gradientDirection: TemporalGradientDirection =
        when {
            temporalGradient > TEMPERATURE_FREEZING_POINT -> TemporalGradientDirection.HEATING
            temporalGradient < TEMPERATURE_FREEZING_POINT -> TemporalGradientDirection.COOLING
            else -> TemporalGradientDirection.STABLE
        }

    enum class TemporalGradientDirection {
        HEATING,
        COOLING,
        STABLE
    }
}