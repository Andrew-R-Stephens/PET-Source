package com.tritiumgaming.feature.investigation.ui.journal

import com.tritiumgaming.feature.investigation.ui.journal.evidence.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.ghost.GhostListUiState

data class JournalStateBundle(
    val journalUiState: JournalUiState,
    val evidenceListUiState: EvidenceListUiState,
    val ghostListUiState: GhostListUiState
)
