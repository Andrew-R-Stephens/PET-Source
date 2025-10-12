package com.tritiumgaming.feature.operation.ui.investigation.toolbar.subsection.sanitytracker.controller.phase

import com.tritiumgaming.feature.operation.ui.investigation.toolbar.subsection.sanitytracker.controller.timer.TimerUiState.Companion.DEFAULT
import com.tritiumgaming.feature.operation.ui.investigation.toolbar.subsection.sanitytracker.controller.timer.TimerUiState.Companion.FOREVER

data class PhaseUiState(
    internal val currentPhase: Phase = Phase.SETUP,
    internal val canAlertAudio: Boolean = false,
    internal val canFlash: Boolean = true,
    internal val startFlashTime: Long = DEFAULT,
    internal val elapsedFlashTime: Long = DEFAULT,
    internal val maxFlashTime: Long = FOREVER,
)
