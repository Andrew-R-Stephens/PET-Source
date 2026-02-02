package com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.item

import com.tritiumgaming.shared.data.evidence.model.RuledEvidence

data class EvidenceListItemUiAction(
    val onToggle: (ruling: RuledEvidence.Ruling) -> Unit = {},
    val onNameClick: () -> Unit = {}
)
