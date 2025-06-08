package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostEvidence.GhostEvidenceDto

interface GhostEvidenceDataSource {

    fun fetchGhostEvidences(): Result<ArrayList<GhostEvidenceDto>>

}