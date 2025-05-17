package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghost.repository.GhostRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.repository.GhostEvidenceRepository

interface JournalRepository {

    val evidenceRepository: EvidenceRepository
    val ghostRepository: GhostRepository
    val ghostEvidenceRepository: GhostEvidenceRepository

    fun mapGhostEvidence()

}