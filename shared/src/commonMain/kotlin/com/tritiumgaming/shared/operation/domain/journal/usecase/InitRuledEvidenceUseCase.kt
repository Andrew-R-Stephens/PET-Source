package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.evidence.model.RuledEvidence
import com.tritiumgaming.shared.operation.domain.evidence.model.RuledEvidence.Ruling

class InitRuledEvidenceUseCase(
    private val fetchEvidencesUseCase: FetchEvidencesUseCase
) {
    operator fun invoke(): List<RuledEvidence> {
        return fetchEvidencesUseCase().map {
            RuledEvidence(it).copy( ruling = Ruling.NEUTRAL )
        }
    }
}
