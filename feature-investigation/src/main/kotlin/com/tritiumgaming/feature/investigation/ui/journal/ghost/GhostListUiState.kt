package com.tritiumgaming.feature.investigation.ui.journal.ghost

import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostState
import com.tritiumgaming.shared.data.evidence.model.EvidenceState
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources

class GhostListUiState(
    val ghostStates: List<GhostState>,
    val ghostOrder: List<GhostResources.GhostIdentifier>,
    val evidenceState: List<EvidenceState>
)