package com.tritiumgaming.shared.operation.domain.popup.model

import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostIcon
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostWeakness

data class GhostPopupRecord(
    val id: GhostResources.GhostIdentifier,
    val name: GhostTitle,
    val icon: GhostIcon,
    val info: GhostDescription,
    val strengthData: GhostStrength,
    val weaknessData: GhostWeakness,
    val huntData: GhostHuntInfo,
)
