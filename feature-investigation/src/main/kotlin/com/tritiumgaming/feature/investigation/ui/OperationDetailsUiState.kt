package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.feature.investigation.ui.journal.lists.item.GhostScore
import com.tritiumgaming.feature.investigation.ui.toolbar.common.digitaltimer.TimerUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.common.operationconfig.difficulty.DifficultyUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.common.operationconfig.map.MapUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.common.phase.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.common.sanitymeter.PlayerSanityUiState
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