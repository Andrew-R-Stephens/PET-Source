package com.tritiumgaming.shared.data.journal.usecase

class FetchGhostEvidencesUseCase(
    private val ghostRepository: com.tritiumgaming.shared.data.ghost.repository.GhostRepository,
    private val evidenceRepository: com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository
) {

    operator fun invoke(): Result<List<com.tritiumgaming.shared.data.journal.model.GhostEvidence>> {

        val ghostsResult = ghostRepository.fetchGhosts()
        ghostsResult.exceptionOrNull()?.let { return Result.failure(it) }
        val ghostList = ghostsResult.getOrDefault(emptyList())

        val evidenceResult = evidenceRepository.fetchEvidenceType()
        evidenceResult.exceptionOrNull()?.let { return Result.failure(it) }
        val evidenceList = evidenceResult.getOrDefault(emptyList())

        val ghostEvidenceDtoList: List<com.tritiumgaming.shared.data.journal.model.GhostEvidence> = ghostList.map { ghost ->

            val normalEvidence = ghost.normalEvidence.map { evidence ->
                evidenceList.first { it.id == evidence }
            }

            val strictEvidence = ghost.strictEvidence.map { evidence ->
                evidenceList.first { it.id == evidence }
            }

            com.tritiumgaming.shared.data.journal.model.GhostEvidence(
                ghost = com.tritiumgaming.shared.data.ghost.model.GhostType(ghost.id, ghost.name),
                normalEvidenceList = normalEvidence,
                strictEvidenceList = strictEvidence
            )
        }

        return Result.success(ghostEvidenceDtoList)

    }

}
