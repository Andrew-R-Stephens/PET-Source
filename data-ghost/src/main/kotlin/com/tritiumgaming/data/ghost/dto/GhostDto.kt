package com.tritiumgaming.data.ghost.dto

import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostWeakness
import com.tritiumgaming.shared.operation.domain.ghost.model.Ghost
import com.tritiumgaming.shared.operation.domain.ghost.model.GhostType
import com.tritiumgaming.shared.operation.domain.popup.model.GhostPopupRecord

data class GhostDto(
    val id: String,
    val name: GhostTitle,
    val normalEvidence: List<String>,
    val strictEvidence: List<String>,
    val info: GhostDescription,
    val strengthData: GhostStrength,
    val weaknessData: GhostWeakness,
    val huntData: GhostHuntInfo,
)

fun List<GhostDto>.toGhostType() = map {
    it.toGhostType()
}
fun GhostDto.toGhostType() = GhostType(
    id = id,
    name = name
)

fun List<GhostDto>.toGhost() = map {
    it.toGhost()
}
fun GhostDto.toGhost() = Ghost(
    id = id,
    name = name,
    normalEvidence = normalEvidence.map { it },
    strictEvidence = strictEvidence.map { it }
)

fun GhostDto.toLocalPopup() = GhostPopupRecord(
    id = id,
    name = name,
    info = info,
    strengthData = strengthData,
    weaknessData = weaknessData,
    huntData = huntData,
)
