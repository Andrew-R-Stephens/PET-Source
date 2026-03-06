package com.tritiumgaming.shared.data.popup.model

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.*

data class GhostPopupRecord(
    val id: GhostIdentifier,
    val name: GhostTitle,
    val icon: GhostIcon,
    val info: GhostDescription,
    val strengthData: GhostStrength,
    val weaknessData: GhostWeakness,
    val huntData: GhostHuntInfo,
    val sanityBounds: HuntSanityBounds,
    val huntCooldown: HuntCooldown
)
