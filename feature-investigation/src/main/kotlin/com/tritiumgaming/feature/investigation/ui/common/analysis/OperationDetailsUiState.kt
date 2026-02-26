package com.tritiumgaming.feature.investigation.ui.common.analysis

import com.tritiumgaming.feature.investigation.ui.section.configs.DifficultyConfigUiState
import com.tritiumgaming.feature.investigation.ui.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostState
import com.tritiumgaming.feature.investigation.ui.section.configs.MapConfigUiState
import com.tritiumgaming.feature.investigation.ui.OperationDetailsUiState
import com.tritiumgaming.shared.data.evidence.model.EvidenceState
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources

internal class OperationDetailsUiState(
    val mapConfigUiState: MapConfigUiState,
    val operationDetailsUiState: OperationDetailsUiState,
    val phaseUiState: PhaseUiState,
    val difficultyConfigUiState: DifficultyConfigUiState,
    val timerUiState: TimerUiState,
    val sanityUiState: PlayerSanityUiState,
    val ghostStates: List<GhostState> = emptyList(),
    val ghostOrder: List<GhostResources.GhostIdentifier>,
    val evidenceState: List<EvidenceState>,
)