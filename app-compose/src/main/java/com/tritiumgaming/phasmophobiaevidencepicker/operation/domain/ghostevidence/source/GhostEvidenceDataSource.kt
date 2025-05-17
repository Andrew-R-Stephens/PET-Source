package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.source

import android.content.Context
import android.content.res.Resources
import androidx.annotation.ArrayRes
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostEvidence

interface GhostEvidenceDataSource {

    fun fetchGhostEvidences(context: Context): ArrayList<GhostEvidence.GhostEvidenceDto>

    fun readGhost(
        resources: Resources, @ArrayRes ghostsArrayID: Int
    ): GhostEvidence.GhostEvidenceDto

}