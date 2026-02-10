package com.tritiumgaming.feature.investigation.ui.journal

import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostListUiState

data class JournalStateBundle(
    val journalUiState: JournalUiState,
    val evidenceListUiState: EvidenceListUiState,
    val ghostListUiState: GhostListUiState
)
