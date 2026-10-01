package com.tritiumgaming.data.journal.repository

import com.tritiumgaming.data.journal.model.GhostEvidence

interface JournalRepository {
    fun fetchGhostEvidence(): Result<List<GhostEvidence>>

}