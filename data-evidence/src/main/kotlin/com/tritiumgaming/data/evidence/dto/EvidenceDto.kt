package com.tritiumgaming.data.evidence.dto

import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceCost
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierDescription
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierRequiredLevel
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTitle
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceTier
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.popup.model.EvidencePopupRecord

data class EvidenceDto(
    val id: String,
    val name: EvidenceTitle,
    val icon: EvidenceIcon,
    val buyCost: EvidenceCost,
    val tiers: List<EvidenceTierDto> = emptyList()
)

data class EvidenceTierDto(
    val description: EvidenceTierDescription,
    val animation: EvidenceTierAnimation,
    val levelRequirement: EvidenceTierRequiredLevel
)

fun List<EvidenceTierDto>.toDomain() = map { it.toDomain() }

fun EvidenceTierDto.toDomain() = EvidenceTier(
    description = description,
    animation = animation,
    levelRequirement = levelRequirement
)

fun List<EvidenceDto>.toEvidenceTypeDto() = map {
    it.toEvidenceType()
}

fun EvidenceDto.toEvidenceTypeDto() = EvidenceTypeDto(
    id = id,
    name = name,
    icon = icon
)

fun List<EvidenceDto>.toEvidenceType() = map {
    it.toEvidenceType()
}

fun EvidenceDto.toEvidenceType() = EvidenceType(
    id = id,
    name = name,
    icon = icon
)

fun EvidenceDto.toLocalPopup() = EvidencePopupRecord(
    id = id,
    cost = buyCost,
    tiers = tiers.toDomain()
)
