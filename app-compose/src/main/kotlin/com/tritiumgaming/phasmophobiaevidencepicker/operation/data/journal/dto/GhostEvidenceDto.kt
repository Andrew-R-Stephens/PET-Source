package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostEvidence

data class GhostEvidenceDto(
    val ghostDto: GhostDto,
    val normalEvidences: List<EvidenceTypeDto>,
    val strictEvidences: List<EvidenceTypeDto>
)

fun GhostEvidenceDto.toGhostEvidence() = GhostEvidence(
    ghostId = ghostDto.id,
    normalEvidenceList = normalEvidences.toEvidenceType(),
    strictEvidenceList = strictEvidences.toEvidenceType()
)

fun List<GhostEvidenceDto>.toGhostEvidence() = map {
    it.toGhostEvidence()
}

