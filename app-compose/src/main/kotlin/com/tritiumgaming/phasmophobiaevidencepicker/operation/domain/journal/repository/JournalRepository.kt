package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.repository

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostEvidence
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.GhostType

interface JournalRepository {

    fun fetchGhosts(): Result<List<GhostType>>
    fun fetchEvidence(): Result<List<EvidenceType>>
    fun fetchGhostEvidence(): Result<List<GhostEvidence>>

}