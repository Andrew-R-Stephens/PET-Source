package com.tritiumgaming.feature.investigation.ui.journal.ghost.item

import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.investigation.model.EvidenceState

data class GhostListUiItemActions(
    val onToggleNegateGhost: (Ghost) -> Unit = {},
    val onGetEvidenceState: (EvidenceType) -> EvidenceState? = { null },
    val onNameClick: () -> Unit = {},
    val onRequestToolTip: () -> Unit = {}
)