package com.tritiumstudios.data.operation.model

import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.Weather

data class WeatherData(
    val weather: Weather = Weather.RANDOM
)