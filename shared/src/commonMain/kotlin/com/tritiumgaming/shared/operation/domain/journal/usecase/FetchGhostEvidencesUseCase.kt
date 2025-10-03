package com.tritiumgaming.shared.operation.domain.journal.usecase

import com.tritiumgaming.shared.operation.domain.evidence.repository.EvidenceRepository
import com.tritiumgaming.shared.operation.domain.ghost.model.GhostType
import com.tritiumgaming.shared.operation.domain.ghost.repository.GhostRepository
import com.tritiumgaming.shared.operation.domain.journal.model.GhostEvidence
import com.tritiumgaming.shared.operation.domain.journal.repository.JournalRepository

class FetchGhostEvidencesUseCase(
    private val ghostRepository: GhostRepository,
    private val evidenceRepository: EvidenceRepository
) {

    operator fun invoke(): Result<List<GhostEvidence>> {

        val ghostsResult = ghostRepository.fetchGhosts()
        ghostsResult.exceptionOrNull()?.let { return Result.failure(it) }
        val ghostList = ghostsResult.getOrDefault(emptyList())

        val evidenceResult = evidenceRepository.fetchEvidence()
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
                ghost = GhostType(ghost.id, ghost.name),
                normalEvidenceList = normalEvidence,
                strictEvidenceList = strictEvidence
            )
        }

        return Result.success(ghostEvidenceDtoList)

    }

}
