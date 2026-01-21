package com.tritiumgaming.feature.investigation.ui.journal.lists.ghost

import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostScore
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources

class GhostListUiState(
    val ghostScores: List<GhostScore>,
    val ghostOrder: List<GhostResources.GhostIdentifier>,
    val ruledEvidence: List<RuledEvidence>
)