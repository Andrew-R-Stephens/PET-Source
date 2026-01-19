package com.tritiumgaming.feature.investigation.ui.journal.lists.item

import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence
import com.tritiumgaming.shared.data.ghost.model.GhostType

data class GhostListUiItemActions(
    val onToggleNegateGhost: (GhostType) -> Unit,
    val onGetRuledEvidence: (EvidenceType) -> RuledEvidence?,
    val onNameClick: () -> Unit = {}
)