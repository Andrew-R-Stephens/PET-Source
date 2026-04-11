package com.tritiumgaming.feature.investigation.app.mappers.weather

import androidx.annotation.DrawableRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

@DrawableRes fun Weather.toDrawable(): Int = when(this) {
    Weather.RANDOM -> R.drawable.ic_weather_unknown
    Weather.LIGHT_RAIN -> R.drawable.ic_weather_rain_light
    Weather.HEAVY_RAIN -> R.drawable.ic_weather_rain_heavy
    Weather.SNOW -> R.drawable.ic_weather_snow
    Weather.WINDY -> R.drawable.ic_weather_wind
    Weather.CLEAR -> R.drawable.ic_weather_clear
    Weather.FOG -> R.drawable.ic_weather_fog
    Weather.SUNRISE -> R.drawable.ic_weather_sunrise
    Weather.BLOOD_MOON -> R.drawable.ic_weather_moon
}