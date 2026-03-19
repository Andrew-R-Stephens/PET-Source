package com.tritiumgaming.feature.investigation.app.mappers.map

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier

@StringRes
fun MapSize.toStringResource(): Int {
    return when (this) {
        MapSize.SMALL -> R.string.map_setting_state_small
        MapSize.MEDIUM -> R.string.map_setting_state_medium
        MapSize.LARGE -> R.string.map_setting_state_large
    }
}

@StringRes fun MapSizePhaseModifier.toStringResource(): Int {
    return when (this) {
        MapSizePhaseModifier.SETUP_SMALL -> MapSize.SMALL.toStringResource()
        MapSizePhaseModifier.SETUP_MEDIUM -> MapSize.MEDIUM.toStringResource()
        MapSizePhaseModifier.SETUP_LARGE -> MapSize.LARGE.toStringResource()
        MapSizePhaseModifier.ACTION_SMALL -> MapSize.SMALL.toStringResource()
        MapSizePhaseModifier.ACTION_MEDIUM -> MapSize.MEDIUM.toStringResource()
        MapSizePhaseModifier.ACTION_LARGE -> MapSize.LARGE.toStringResource()
    }
}