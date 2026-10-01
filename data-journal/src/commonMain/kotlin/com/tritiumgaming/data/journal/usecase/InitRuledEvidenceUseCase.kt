package com.tritiumgaming.data.journal.usecase

import com.tritiumgaming.data.journal.model.EvidenceState
import com.tritiumgaming.data.journal.model.EvidenceValidationType

class InitRuledEvidenceUseCase(
    private val fetchEvidencesUseCase: FetchEvidenceTypesUseCase
) {
    operator fun invoke(): Result<List<EvidenceState>> {
        val result = fetchEvidencesUseCase()

        val evidences = result.getOrThrow().map {
            EvidenceState(it)
                .copy( state = EvidenceValidationType.NEUTRAL)
        }

        return Result.success(evidences)
    }
}
