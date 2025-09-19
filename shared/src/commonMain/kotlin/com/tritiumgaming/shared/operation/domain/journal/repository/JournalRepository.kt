package com.tritiumgaming.shared.operation.domain.journal.repository

import com.tritiumgaming.shared.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.journal.model.GhostEvidence
import com.tritiumgaming.shared.operation.domain.journal.model.GhostType

interface JournalRepository {

    fun fetchGhosts(): Result<List<GhostType>>
    fun fetchEvidence(): Result<List<EvidenceType>>
    fun fetchGhostEvidence(): Result<List<GhostEvidence>>

}