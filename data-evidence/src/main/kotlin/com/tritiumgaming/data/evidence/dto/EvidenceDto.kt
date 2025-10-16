package com.tritiumgaming.data.evidence.dto

import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceDescription
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTierAnimation
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTitle
import com.tritiumgaming.shared.operation.domain.evidence.model.Evidence
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceTier
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType

data class EvidenceDto(
    val id: EvidenceIdentifier,
    val name: EvidenceTitle,
    val description: EvidenceDescription,
    val icon: EvidenceIcon,
    val tiers: List<EvidenceTierDto> = emptyList()
)

data class EvidenceTierDto(
    val animation: EvidenceTierAnimation
)

fun List<EvidenceDto>.toDomain() = map { it.toDomain() }

fun EvidenceDto.toDomain() = Evidence(
    id = id,
    name = name,
    description = description,
    icon = icon,
    tiers = tiers.toDomain(),
)

@JvmName("toDomainEvidenceTierListDto")
fun List<EvidenceTierDto>.toDomain() = map { it.toDomain() }

@JvmName("toDomainEvidenceTierDto")
fun EvidenceTierDto.toDomain() = EvidenceTier(
    animation = animation
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
