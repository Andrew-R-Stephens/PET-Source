package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.shared.data.phase.model.Phase

data class PhaseUiState(
    internal val currentPhase: Phase = Phase.SETUP,
    internal val canAlertAudio: Boolean = false,
    internal val canFlash: Boolean = true,
    internal val startFlashTime: Long = TimerUiState.Companion.DEFAULT,
    internal val elapsedFlashTime: Long = TimerUiState.Companion.DEFAULT,
    internal val maxFlashTime: Long = TimerUiState.Companion.DURATION_30_SECONDS,
)