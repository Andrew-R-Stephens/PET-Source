package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.RuledEvidence.Ruling

class InitRuledEvidenceUseCase(
    private val fetchEvidencesUseCase: FetchEvidencesUseCase
) {
    operator fun invoke(): List<RuledEvidence> {
        return fetchEvidencesUseCase().map {
            RuledEvidence(it).copy( ruling = Ruling.NEUTRAL )
        }
    }
}
