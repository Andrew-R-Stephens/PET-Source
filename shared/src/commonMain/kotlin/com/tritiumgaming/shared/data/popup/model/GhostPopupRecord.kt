package com.tritiumgaming.shared.data.popup.model

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources

data class GhostPopupRecord(
    val id: GhostResources.GhostIdentifier,
    val name: GhostResources.GhostTitle,
    val icon: GhostResources.GhostIcon,
    val info: GhostResources.GhostDescription,
    val strengthData: GhostResources.GhostStrength,
    val weaknessData: GhostResources.GhostWeakness,
    val huntData: GhostResources.GhostHuntInfo,
    val sanityBounds: GhostResources.HuntSanityBounds,
    val huntCooldown: GhostResources.HuntCooldown
)
