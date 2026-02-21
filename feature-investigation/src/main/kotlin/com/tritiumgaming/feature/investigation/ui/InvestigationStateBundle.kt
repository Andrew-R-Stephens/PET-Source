package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.feature.investigation.ui.common.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiActions
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.CarouselUiActions
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.JournalStateBundle
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostListUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.ToolbarSectionBpmVisualizerUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState

data class InvestigationStateBundle(
    val toolbarUiState: ToolbarUiState,
    val journalStateBundle: JournalStateBundle,
    val timerUiState: TimerUiState,
    val timerUiActions: TimerUiActions,
    val phaseUiState: PhaseUiState,
    val mapUiState: MapUiState,
    val mapUiActions: CarouselUiActions,
    val difficultyUiState: DifficultyUiState,
    val difficultyUiActions: CarouselUiActions,
    val sanityUiState: PlayerSanityUiState,
    val ghostListUiState: GhostListUiState,
    val evidenceListUiState: EvidenceListUiState,
    val operationDetailsUiState: OperationDetailsUiState,
    val bpmToolUiState: BpmToolUiState,
    val toolbarSectionBpmVisualizerUiActions: ToolbarSectionBpmVisualizerUiActions
)
