package com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.item

import com.tritiumgaming.shared.data.evidence.model.EvidenceValidationType

data class EvidenceListItemUiAction(
    val onToggle: (evidenceValidationType: EvidenceValidationType) -> Unit = {},
    val onNameClick: () -> Unit = {}
)
