package com.tritiumgaming.feature.investigation.app.mappers.map

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.data.map.mappers.MapModifierResources.MapSize

@StringRes
fun MapSize.toStringResource(): Int {
    return when (this) {
        MapSize.SMALL -> R.string.map_setting_state_small
        MapSize.MEDIUM -> R.string.map_setting_state_medium
        MapSize.LARGE -> R.string.map_setting_state_large
    }
}
