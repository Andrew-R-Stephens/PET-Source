package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostevidence.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.repository.GhostEvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.source.GhostEvidenceDataSource

class GhostEvidenceRepositoryImpl(
    context: Context,
    override val localSource: GhostEvidenceDataSource
): GhostEvidenceRepository {

    override val ghostEvidences = localSource.fetchGhostEvidences(context)

}