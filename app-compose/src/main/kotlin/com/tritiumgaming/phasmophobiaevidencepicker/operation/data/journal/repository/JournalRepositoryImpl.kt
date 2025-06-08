package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.repository.GhostEvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.repository.JournalRepository

//TODO "Not yet implemented"
class JournalRepositoryImpl(
    override val evidenceRepository: EvidenceRepository,
    override val ghostRepository: GhostRepository,
    override val ghostEvidenceRepository: GhostEvidenceRepository
): JournalRepository {

    /*override fun mapGhostEvidence() {
        ghostEvidenceRepository.ghostEvidences.forEach { ghostEvidences ->
            ghostRepository.ghosts.find { ghost ->
                ghost.id == ghostEvidences.ghostId
            }?.evidence?.let { evidence ->
                ghostEvidences.normalEvidences.forEach {
                    evidenceRepository.evidences.find { evidenceType ->
                        evidenceType.id == it
                    }?.let { evidenceType ->
                        evidence.addNormalEvidence(evidenceType)
                    }
                }
                ghostEvidences.strictEvidences.forEach {
                    evidenceRepository.evidences.find { evidenceType ->
                        evidenceType.id == it
                    }?.let { evidenceType ->
                        evidence.addStrictEvidence(evidenceType)
                    }
                }
            }
        }
    }*/

    override fun mapGhostEvidence() {
        //TODO "Not yet implemented"
    }

    init {
        mapGhostEvidence()
    }

}