package com.tritiumgaming.data.ghost.dto

import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.HuntCooldown
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.HuntSanityBounds
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIcon
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostWeakness
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostSpeed
import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.ghost.model.GhostType

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
    val speed: GhostSpeed,
    val sanityBounds: HuntSanityBounds,
    val huntCooldown: HuntCooldown
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
    strictEvidence = strictEvidence.map { it },
    speed = speed,
    huntSanityBounds = sanityBounds,
    huntCooldown = huntCooldown
)

