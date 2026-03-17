package com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.item

import com.tritiumgaming.shared.data.investigation.model.EvidenceValidationType

data class EvidenceListItemUiState(
    val state: EvidenceValidationType,
    val label: String,
    val enabled: Boolean
)