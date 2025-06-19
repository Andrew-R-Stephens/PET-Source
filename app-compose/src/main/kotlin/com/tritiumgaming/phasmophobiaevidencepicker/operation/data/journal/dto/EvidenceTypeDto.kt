package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceIcon
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.mapper.EvidenceResources.EvidenceTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType

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
