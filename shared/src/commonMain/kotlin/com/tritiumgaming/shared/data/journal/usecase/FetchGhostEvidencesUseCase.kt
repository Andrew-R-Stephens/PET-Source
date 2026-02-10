package com.tritiumgaming.shared.data.journal.usecase

import com.tritiumgaming.shared.data.evidence.repository.EvidenceRepository
import com.tritiumgaming.shared.data.ghost.model.GhostType
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

        val evidenceResult = evidenceRepository.fetchEvidenceType()
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
                strictEvidenceList = strictEvidence,
                speed = ghost.speed
            )
        }

        return Result.success(ghostEvidenceDtoList)

    }

}
