package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.feature.investigation.ui.common.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiActions
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigActionsBundle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigStateBundle
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.JournalStateBundle
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostListUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.BpmToolUiActions
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarBundle
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState

internal data class InvestigationUiState(
    val toolbarUiState: ToolbarUiState,
    val journalStateBundle: JournalStateBundle,
    val timerUiState: TimerUiState,
    val timerUiActions: TimerUiActions,
    val phaseUiState: PhaseUiState,
    val mapUiStateBundle: ConfigStateBundle,
    val mapUiActionBundle: ConfigActionsBundle,
    val difficultyUiStateBundle: ConfigStateBundle,
    val difficultyUiActionBundle: ConfigActionsBundle,
    val sanityUiState: PlayerSanityUiState,
    val ghostListUiState: GhostListUiState,
    val evidenceListUiState: EvidenceListUiState,
    val operationDetailsUiState: OperationDetailsUiState,
    val bpmToolUiState: BpmToolUiState,
    val bpmToolUiActions: BpmToolUiActions,
    val smudgeHuntPreventionBundle: NotchedProgressBarBundle,
    val smudgeBlindingBundle: NotchedProgressBarBundle,
    val huntDurationBundle: NotchedProgressBarBundle,
    val huntGapBundle: NotchedProgressBarBundle
)
