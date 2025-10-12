package com.tritiumgaming.shared.operation.domain.journal.repository

import com.tritiumgaming.shared.operation.domain.journal.model.GhostEvidence

interface JournalRepository {
    fun fetchGhostEvidence(): Result<List<GhostEvidence>>

}