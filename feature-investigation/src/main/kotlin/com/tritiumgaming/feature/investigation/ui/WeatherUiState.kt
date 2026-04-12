package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

internal data class WeatherUiState(
    val weather: Weather = Weather.RANDOM,
    val enabled: Boolean = true
)
