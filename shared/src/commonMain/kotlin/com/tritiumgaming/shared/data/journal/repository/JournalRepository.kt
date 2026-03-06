package com.tritiumgaming.shared.data.journal.repository

import com.tritiumgaming.shared.data.journal.model.GhostEvidence

interface JournalRepository {
    fun fetchGhostEvidence(): Result<List<GhostEvidence>>

}