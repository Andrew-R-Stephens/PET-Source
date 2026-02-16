package com.tritiumgaming.feature.investigation.ui.common.analysis

import com.tritiumgaming.feature.investigation.ui.DifficultyUiState
import com.tritiumgaming.feature.investigation.ui.MapUiState
import com.tritiumgaming.feature.investigation.ui.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostState
import com.tritiumgaming.shared.data.evidence.model.EvidenceState
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources

class OperationDetailsUiState(
    val mapUiState: MapUiState,
    val phaseUiState: PhaseUiState,
    val difficultyUiState: DifficultyUiState,
    val timerUiState: TimerUiState,
    val sanityUiState: PlayerSanityUiState,
    val ghostStates: List<GhostState> = emptyList(),
    val ghostOrder: List<GhostResources.GhostIdentifier>,
    val evidenceState: List<EvidenceState>,
)