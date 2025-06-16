package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers

import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.modifier.mappers.MapModifierResources

@StringRes fun MapModifierResources.MapSize.toStringResource(): Int {
    return when (this) {
        MapModifierResources.MapSize.SMALL -> R.string.maps_size_title_small
        MapModifierResources.MapSize.MEDIUM -> R.string.maps_size_title_medium
        MapModifierResources.MapSize.LARGE -> R.string.maps_size_title_large
    }
}

@StringRes fun MapModifierResources.PhaseModifierTitle.toStringResource(): Int {
    return when (this) {
        MapModifierResources.PhaseModifierTitle.SETUP -> R.string.investigation_timer_setuplabel
        MapModifierResources.PhaseModifierTitle.ACTION -> R.string.investigation_timer_actionlabel
    }
}

@StringRes fun MapModifierResources.SizePhaseModifier.toFractionResource(): Int {
    return when (this) {
        MapModifierResources.SizePhaseModifier.SETUP_SMALL -> R.fraction.maps_size_modifier_small_setup
        MapModifierResources.SizePhaseModifier.SETUP_MEDIUM -> R.fraction.maps_size_modifier_medium_setup
        MapModifierResources.SizePhaseModifier.SETUP_LARGE -> R.fraction.maps_size_modifier_large_setup
        MapModifierResources.SizePhaseModifier.ACTION_SMALL -> R.fraction.maps_size_modifier_small_normal
        MapModifierResources.SizePhaseModifier.ACTION_MEDIUM -> R.fraction.maps_size_modifier_medium_normal
        MapModifierResources.SizePhaseModifier.ACTION_LARGE -> R.fraction.maps_size_modifier_large_normal
    }
}