package com.tritiumgaming.feature.maps.app.mappers.map

import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.PhaseTitle
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier

@StringRes
fun MapSize.toStringResource(): Int {
    return when (this) {
        MapSize.SMALL -> R.string.map_setting_state_small
        MapSize.MEDIUM -> R.string.map_setting_state_medium
        MapSize.LARGE -> R.string.map_setting_state_large
    }
}

@StringRes
fun PhaseTitle.toStringResource(): Int {
    return when (this) {
        PhaseTitle.SETUP -> R.string.investigation_timer_setuplabel
        PhaseTitle.ACTION -> R.string.investigation_timer_actionlabel
    }
}
