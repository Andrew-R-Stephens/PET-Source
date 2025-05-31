package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.ghostevidence.source

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostEvidence

interface GhostEvidenceDataSource {

    fun fetchGhostEvidences(): ArrayList<GhostEvidence.GhostEvidenceDto>

}