package com.tritiumgaming.data.ghost.dto

import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostIcon
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.operation.domain.ghost.mapper.GhostResources.GhostWeakness
import com.tritiumgaming.shared.operation.domain.ghost.model.Ghost
import com.tritiumgaming.shared.operation.domain.ghost.model.GhostType

data class GhostDto(
    val id: GhostIdentifier,
    val name: GhostTitle,
    val icon: GhostIcon,
    val normalEvidence: List<EvidenceIdentifier>,
    val strictEvidence: List<EvidenceIdentifier>,
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

fun List<GhostDto>.toDomain() = map {
    it.toDomain()
}
fun GhostDto.toDomain() = Ghost(
    id = id,
    name = name,
    icon = icon,
    info = info,
    strengthData = strengthData,
    weaknessData = weaknessData,
    huntData = huntData,
    normalEvidence = normalEvidence.map { it },
    strictEvidence = strictEvidence.map { it }
)

