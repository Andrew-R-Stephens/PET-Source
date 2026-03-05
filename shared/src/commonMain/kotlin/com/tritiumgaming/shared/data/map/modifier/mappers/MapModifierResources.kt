package com.tritiumgaming.shared.data.map.modifier.mappers

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier

class MapModifierResources {

    enum class MapSize {
        SMALL,
        MEDIUM,
        LARGE,
    }

    enum class PhaseTitle {
        SETUP,
        ACTION
    }

    enum class MapSizePhaseModifier {
        SETUP_SMALL,
        SETUP_MEDIUM,
        SETUP_LARGE,
        ACTION_SMALL,
        ACTION_MEDIUM,
        ACTION_LARGE,
    }

}

fun MapSizePhaseModifier.toFloat(): Float {
    return when (this) {
        MapSizePhaseModifier.SETUP_SMALL -> .09f
        MapSizePhaseModifier.SETUP_MEDIUM -> .05f
        MapSizePhaseModifier.SETUP_LARGE -> .03f
        MapSizePhaseModifier.ACTION_SMALL ->.12f
        MapSizePhaseModifier.ACTION_MEDIUM -> .08f
        MapSizePhaseModifier.ACTION_LARGE -> .05f
    }
}