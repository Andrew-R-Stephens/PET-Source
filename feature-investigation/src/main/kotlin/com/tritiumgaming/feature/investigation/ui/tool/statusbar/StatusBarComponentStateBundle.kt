package com.tritiumgaming.feature.investigation.ui.tool.statusbar

import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimerUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState


internal data class StatusBarComponentStateBundle(
    val sanityUiState: PlayerSanityUiState,
    val digitalTimerUiState: DigitalTimerUiState,
    val phaseUiState: OperationDetailsUiState.PhaseDetails
)
