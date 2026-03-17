package com.tritiumgaming.shared.data.popup.model

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIcon
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostWeakness
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.HuntCooldown
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.HuntSanityBounds

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
