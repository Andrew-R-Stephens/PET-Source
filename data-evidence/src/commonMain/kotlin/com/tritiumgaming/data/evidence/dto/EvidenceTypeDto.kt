package com.tritiumgaming.data.evidence.dto

import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceTitle
import com.tritiumgaming.shared.data.evidence.model.EvidenceType

data class EvidenceTypeDto(
    val id: EvidenceIdentifier,
    val name: EvidenceTitle,
    val icon: EvidenceIcon,
)

fun EvidenceTypeDto.toEvidenceType() = EvidenceType(
    id = id,
    name = name,
    icon = icon
)

fun List<EvidenceTypeDto>.toEvidenceType() = map { it.toEvidenceType() }
