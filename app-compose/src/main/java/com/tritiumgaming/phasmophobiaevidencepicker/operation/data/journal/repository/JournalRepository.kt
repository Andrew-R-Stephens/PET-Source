package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.journal.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghost.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostevidence.repository.GhostEvidenceRepository

class JournalRepository(
    val evidenceRepository: EvidenceRepository,
    val ghostRepository: GhostRepository,
    val ghostEvidenceRepository: GhostEvidenceRepository
) {

    private fun mapGhostEvidence() {
        ghostEvidenceRepository.ghostEvidences.forEach { ghostEvidences ->
            ghostRepository.ghosts.find { ghost -> ghost.id == ghostEvidences.ghostId }
                ?.evidence?.let { evidence ->
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
    }

    init {
        mapGhostEvidence()
    }


}