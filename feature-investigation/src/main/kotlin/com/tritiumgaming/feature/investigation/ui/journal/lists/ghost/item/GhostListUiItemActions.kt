package com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item

import com.tritiumgaming.shared.data.evidence.model.EvidenceState
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.model.GhostType

data class GhostListUiItemActions(
    val onToggleNegateGhost: (GhostType) -> Unit,
    val onGetEvidenceState: (EvidenceType) -> EvidenceState?,
    val onNameClick: () -> Unit = {}
)