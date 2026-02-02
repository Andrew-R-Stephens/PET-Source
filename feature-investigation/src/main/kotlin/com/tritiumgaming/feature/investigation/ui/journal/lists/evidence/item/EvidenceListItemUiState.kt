package com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.item

import com.tritiumgaming.shared.data.evidence.model.RuledEvidence

data class EvidenceListItemUiState(
    val state: RuledEvidence.Ruling,
    val label: String
)