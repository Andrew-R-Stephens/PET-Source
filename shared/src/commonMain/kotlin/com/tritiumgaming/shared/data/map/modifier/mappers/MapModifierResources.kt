package com.tritiumgaming.shared.data.map.modifier.mappers

import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier.ACTION_LARGE
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier.ACTION_MEDIUM
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier.ACTION_SMALL
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier.SETUP_LARGE
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier.SETUP_MEDIUM
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier.SETUP_SMALL

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
        SETUP_SMALL -> .09f
        SETUP_MEDIUM -> .05f
        SETUP_LARGE -> .03f
        ACTION_SMALL ->.12f
        ACTION_MEDIUM -> .08f
        ACTION_LARGE -> .05f
    }
}