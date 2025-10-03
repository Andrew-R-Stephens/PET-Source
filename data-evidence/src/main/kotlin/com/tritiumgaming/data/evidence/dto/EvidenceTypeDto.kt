package com.tritiumgaming.data.evidence.dto

import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources.EvidenceTitle
import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType

data class EvidenceTypeDto(
    val id: String,
    val name: EvidenceTitle,
    val icon: EvidenceIcon,
)

fun EvidenceTypeDto.toEvidenceType() = EvidenceType(
    id = id,
    name = name,
    icon = icon
)

fun List<EvidenceTypeDto>.toEvidenceType() = map { it.toEvidenceType() }
