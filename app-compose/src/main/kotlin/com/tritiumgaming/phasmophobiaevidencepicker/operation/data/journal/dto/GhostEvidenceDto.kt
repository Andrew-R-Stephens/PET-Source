package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostEvidence

data class GhostEvidenceDto(
    val ghostDto: GhostDto,
    val normalEvidences: List<EvidenceTypeDto>,
    val strictEvidences: List<EvidenceTypeDto>
)

fun GhostEvidenceDto.toDomain() = GhostEvidence(
    ghostId = ghostDto.id,
    normalEvidenceList = normalEvidences.toDomain(),
    strictEvidenceList = strictEvidences.toDomain()
)

fun List<GhostEvidenceDto>.toDomain() = map {
    it.toDomain()
}

