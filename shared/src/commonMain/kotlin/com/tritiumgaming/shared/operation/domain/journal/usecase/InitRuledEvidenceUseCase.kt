package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.evidence.model.RuledEvidence
import com.tritiumgaming.shared.operation.domain.evidence.model.RuledEvidence.Ruling

class InitRuledEvidenceUseCase(
    private val fetchEvidencesUseCase: FetchEvidenceTypesUseCase
) {
    operator fun invoke(): Result<List<RuledEvidence>> {
        val result = fetchEvidencesUseCase()

        val evidences = result.getOrThrow().map {
            RuledEvidence(it).copy( ruling = Ruling.NEUTRAL )
        }

        return Result.success(evidences)
    }
}
