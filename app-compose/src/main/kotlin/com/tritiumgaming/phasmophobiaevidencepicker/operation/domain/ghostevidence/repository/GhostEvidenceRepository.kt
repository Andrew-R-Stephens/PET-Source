package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.source.GhostEvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostEvidence

interface GhostEvidenceRepository {
    val localSource: GhostEvidenceDataSource

    fun getGhostEvidences(): ArrayList<GhostEvidence.GhostEvidenceDto>
}