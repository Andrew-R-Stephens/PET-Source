package com.tritiumgaming.shared.operation.domain.popup.model

import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostWeakness

data class GhostPopupRecord(
    val id: String = "0",
    val name: GhostTitle,
    val info: GhostDescription,
    val strengthData: GhostStrength,
    val weaknessData: GhostWeakness,
    val huntData: GhostHuntInfo,
)
