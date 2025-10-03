package com.tritiumgaming.shared.operation.domain.journal.repository

import com.tritiumgaming.shared.operation.domain.evidence.model.EvidenceType
import com.tritiumgaming.shared.operation.domain.journal.model.GhostEvidence
import com.tritiumgaming.shared.operation.domain.ghost.model.GhostType

interface JournalRepository {
    fun fetchGhostEvidence(): Result<List<GhostEvidence>>

}