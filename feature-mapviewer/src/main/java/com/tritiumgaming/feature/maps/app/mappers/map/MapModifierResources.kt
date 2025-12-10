package com.tritiumgaming.feature.maps.app.mappers.map

import androidx.annotation.FractionRes
import androidx.annotation.StringRes
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.PhaseModifierTitle
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.SizePhaseModifier

@StringRes
fun MapSize.toStringResource(): Int {
    return when (this) {
        MapSize.SMALL -> R.string.maps_size_title_small
        MapSize.MEDIUM -> R.string.maps_size_title_medium
        MapSize.LARGE -> R.string.maps_size_title_large
    }
}

@StringRes
fun PhaseModifierTitle.toStringResource(): Int {
    return when (this) {
        PhaseModifierTitle.SETUP -> R.string.investigation_timer_setuplabel
        PhaseModifierTitle.ACTION -> R.string.investigation_timer_actionlabel
    }
}

@FractionRes
fun SizePhaseModifier.toFractionResource(): Int {
    return when (this) {
        SizePhaseModifier.SETUP_SMALL -> R.fraction.maps_size_modifier_small_setup
        SizePhaseModifier.SETUP_MEDIUM -> R.fraction.maps_size_modifier_medium_setup
        SizePhaseModifier.SETUP_LARGE -> R.fraction.maps_size_modifier_large_setup
        SizePhaseModifier.ACTION_SMALL -> R.fraction.maps_size_modifier_small_normal
        SizePhaseModifier.ACTION_MEDIUM -> R.fraction.maps_size_modifier_medium_normal
        SizePhaseModifier.ACTION_LARGE -> R.fraction.maps_size_modifier_large_normal
    }
}