package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.shared.data.weather.model.TemperatureRange

data class TemperatureUiState(
    val range: TemperatureRange = TemperatureRange(),
    val temporalGradient: Float = 0f,
    val current: Float = 0f
) {

    val isFreezingBreath: Boolean
        get() = current <= FREEZING_BREATH_TEMPERATURE

    val gradientDirection: TemporalGradientDirection =
        when {
            temporalGradient > 0f -> TemporalGradientDirection.HEATING
            temporalGradient < 0f -> TemporalGradientDirection.COOLING
            else -> TemporalGradientDirection.STABLE
        }

    companion object {
        const val FREEZING_BREATH_TEMPERATURE = 5f
    }

    enum class TemporalGradientDirection {
        HEATING,
        COOLING,
        STABLE
    }
}