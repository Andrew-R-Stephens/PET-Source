package com.tritiumgaming.shared.data.operation.model

import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

data class WeatherData(
    val weather: Weather = Weather.RANDOM
)