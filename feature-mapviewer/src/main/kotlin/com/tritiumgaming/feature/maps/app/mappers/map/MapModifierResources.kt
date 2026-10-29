package com.tritiumgaming.feature.maps.app.mappers.map

import androidx.annotation.StringRes
import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSize
import com.tritiumgaming.feature.maps.R

@StringRes
fun MapSize.toStringResource(): Int {
    return when (this) {
        MapSize.SMALL -> R.string.map_setting_state_small
        MapSize.MEDIUM -> R.string.map_setting_state_medium
        MapSize.LARGE -> R.string.map_setting_state_large
    }
}
