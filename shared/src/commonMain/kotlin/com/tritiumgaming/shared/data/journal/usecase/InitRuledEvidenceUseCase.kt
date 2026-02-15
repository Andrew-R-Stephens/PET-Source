package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.evidence.model.RuledEvidence

class InitRuledEvidenceUseCase(
    private val fetchEvidencesUseCase: FetchEvidenceTypesUseCase
) {
    operator fun invoke(): Result<List<RuledEvidence>> {
        val result = fetchEvidencesUseCase()

        val evidences = result.getOrThrow().map {
            RuledEvidence(it)
                .copy( ruling = RuledEvidence.Ruling.NEUTRAL )
        }

        return Result.success(evidences)
    }
}
