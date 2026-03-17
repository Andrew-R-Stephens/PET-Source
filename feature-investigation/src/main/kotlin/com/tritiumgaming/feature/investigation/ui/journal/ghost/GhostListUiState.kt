package com.tritiumgaming.feature.investigation.ui.journal.ghost

import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostState
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.investigation.model.EvidenceState

class GhostListUiState(
    val ghostStates: List<GhostState>,
    val ghostOrder: List<GhostResources.GhostIdentifier>,
    val evidenceState: List<EvidenceState>
)