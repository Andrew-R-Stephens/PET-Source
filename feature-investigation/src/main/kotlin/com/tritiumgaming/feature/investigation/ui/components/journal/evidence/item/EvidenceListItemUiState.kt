package com.tritiumgaming.feature.investigation.ui.components.journal.evidence.item

import com.tritiumgaming.data.journal.model.EvidenceValidationType

internal data class EvidenceListItemUiState(
    val state: EvidenceValidationType,
    val label: String,
    val enabled: Boolean
)
