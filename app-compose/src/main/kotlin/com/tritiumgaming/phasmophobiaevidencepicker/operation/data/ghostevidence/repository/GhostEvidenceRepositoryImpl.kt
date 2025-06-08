package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostevidence.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.repository.GhostEvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.source.GhostEvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostEvidence

class GhostEvidenceRepositoryImpl(
    override val localSource: GhostEvidenceDataSource
): GhostEvidenceRepository {

    override fun getGhostEvidences(): Result<ArrayList<GhostEvidence.GhostEvidenceDto>> {
        return localSource.fetchGhostEvidences()
    }

}