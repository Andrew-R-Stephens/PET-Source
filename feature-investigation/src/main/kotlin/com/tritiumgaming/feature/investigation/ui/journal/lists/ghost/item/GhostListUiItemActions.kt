package com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item

import com.tritiumgaming.shared.data.evidence.model.EvidenceState
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.ghost.model.GhostType

data class GhostListUiItemActions(
    val onToggleNegateGhost: (Ghost) -> Unit = {},
    val onGetEvidenceState: (EvidenceType) -> EvidenceState? = { null },
    val onNameClick: () -> Unit = {}
)