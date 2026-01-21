package com.tritiumgaming.feature.investigation.ui.common.phase

import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiState.Companion.DEFAULT
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiState.Companion.DURATION_30_SECONDS
import com.tritiumgaming.shared.data.phase.model.Phase

data class PhaseUiState(
    internal val currentPhase: Phase = Phase.SETUP,
    internal val canAlertAudio: Boolean = false,
    internal val canFlash: Boolean = true,
    internal val startFlashTime: Long = DEFAULT,
    internal val elapsedFlashTime: Long = DEFAULT,
    internal val maxFlashTime: Long = DURATION_30_SECONDS,
)
