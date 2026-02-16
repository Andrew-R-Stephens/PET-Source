package com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.item

import com.tritiumgaming.shared.data.evidence.model.EvidenceValidationType

data class EvidenceListItemUiState(
    val state: EvidenceValidationType,
    val label: String
)