package com.tritiumgaming.shared.data.journal.repository

interface JournalRepository {
    fun fetchGhostEvidence(): Result<List<com.tritiumgaming.shared.data.journal.model.GhostEvidence>>

}