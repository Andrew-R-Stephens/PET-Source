package com.tritiumgaming.feature.investigation.ui.journal

import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.ghost.GhostListUiState

internal data class JournalStateBundle(
    val journalUiState: JournalUiState,
    val evidenceListUiState: EvidenceListUiState,
    val ghostListUiState: GhostListUiState
)
