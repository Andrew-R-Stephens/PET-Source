package com.tritiumgaming.feature.investigation.ui.common.analysis

import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.feature.investigation.ui.DifficultyUiState
import com.tritiumgaming.feature.investigation.ui.MapUiState
import com.tritiumgaming.feature.investigation.ui.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostScore
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources

class OperationDetailsUiState(
    val mapUiState: MapUiState,
    val phaseUiState: PhaseUiState,
    val difficultyUiState: DifficultyUiState,
    val timerUiState: TimerUiState,
    val sanityUiState: PlayerSanityUiState,
    val ghostScores: List<GhostScore> = emptyList(),
    val ghostOrder: List<GhostResources.GhostIdentifier>,
    val ruledEvidence: List<RuledEvidence>,
)