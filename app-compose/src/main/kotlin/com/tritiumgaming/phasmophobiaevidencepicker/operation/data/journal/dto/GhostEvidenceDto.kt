package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.dto

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.GhostEvidence

data class GhostEvidenceDto(
    val ghostDto: GhostDto,
    val normalEvidences: List<EvidenceTypeDto>,
    val strictEvidences: List<EvidenceTypeDto>
) {
    override fun toString(): String {
        return "GhostEvidenceDto(ghostDto=$ghostDto, " +
                "normalEvidences=$normalEvidences, " +
                "strictEvidences=$strictEvidences)"
    }
}

fun GhostEvidenceDto.toGhostEvidence() = GhostEvidence(
    ghost = ghostDto.toDomain(),
    normalEvidenceList = normalEvidences.toEvidenceType(),
    strictEvidenceList = strictEvidences.toEvidenceType()
)

fun List<GhostEvidenceDto>.toGhostEvidence() = map {
    it.toGhostEvidence()
}

