package com.tritiumgaming.feature.investigation.ui.components.journal.evidence.item

import com.tritiumgaming.data.journal.model.EvidenceValidationType

internal data class EvidenceListItemUiAction(
    val onToggle: (evidenceValidationType: EvidenceValidationType) -> Unit = {},
    val onNameClick: () -> Unit = {}
)
