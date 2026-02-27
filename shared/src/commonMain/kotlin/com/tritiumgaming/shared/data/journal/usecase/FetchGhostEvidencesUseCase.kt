package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository
import com.tritiumgaming.shared.data.ghost.repository.GhostRepository
import com.tritiumgaming.shared.data.journal.model.GhostEvidence

class FetchGhostEvidencesUseCase(
    private val ghostRepository: GhostRepository,
    private val evidenceRepository: EvidenceRepository
) {

    operator fun invoke(): Result<List<GhostEvidence>> {

        val ghostsResult = ghostRepository.fetchGhosts()
        ghostsResult.exceptionOrNull()?.let { return Result.failure(it) }
        val ghostList = ghostsResult.getOrDefault(emptyList())

        val evidenceResult = evidenceRepository.fetchEvidenceTypes()
        evidenceResult.exceptionOrNull()?.let { return Result.failure(it) }
        val evidenceList = evidenceResult.getOrDefault(emptyList())

        val ghostEvidenceDtoList: List<GhostEvidence> = ghostList.map { ghost ->

            val normalEvidence = ghost.normalEvidence.map { evidence ->
                evidenceList.first { it.id == evidence }
            }

            val strictEvidence = ghost.strictEvidence.map { evidence ->
                evidenceList.first { it.id == evidence }
            }

            GhostEvidence(
                ghost = ghost,
                normalEvidenceList = normalEvidence,
                strictEvidenceList = strictEvidence
            )
        }

        return Result.success(ghostEvidenceDtoList)

    }

}
