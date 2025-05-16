package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostevidence.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.ghostevidence.source.local.GhostEvidenceLocalDataSource

class GhostEvidenceRepository(
    context: Context,
    localSource: GhostEvidenceLocalDataSource
) {

    val ghostEvidences = localSource.fetchGhostEvidences(context)

}