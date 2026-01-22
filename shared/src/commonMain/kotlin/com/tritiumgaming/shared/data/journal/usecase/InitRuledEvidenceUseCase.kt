package com.tritiumgaming.shared.data.journal.usecase

class InitRuledEvidenceUseCase(
    private val fetchEvidencesUseCase: FetchEvidenceTypesUseCase
) {
    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.evidence.model.RuledEvidence>> {
        val result = fetchEvidencesUseCase()

        val evidences = result.getOrThrow().map {
            com.tritiumgaming.shared.data.evidence.model.RuledEvidence(it)
                .copy( ruling = com.tritiumgaming.shared.data.evidence.model.RuledEvidence.Ruling.NEUTRAL )
        }

        return Result.success(evidences)
    }
}
