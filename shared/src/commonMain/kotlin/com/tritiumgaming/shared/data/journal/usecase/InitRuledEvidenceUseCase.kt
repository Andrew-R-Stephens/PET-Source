package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.evidence.model.EvidenceState
import com.tritiumgaming.shared.data.evidence.model.EvidenceValidationType
import com.tritiumgaming.shared.data.evidence.model.EvidenceValidationType.NEUTRAL

class InitRuledEvidenceUseCase(
    private val fetchEvidencesUseCase: FetchEvidenceTypesUseCase
) {
    operator fun invoke(): Result<List<EvidenceState>> {
        val result = fetchEvidencesUseCase()

        val evidences = result.getOrThrow().map {
            EvidenceState(it)
                .copy( state = NEUTRAL)
        }

        return Result.success(evidences)
    }
}
