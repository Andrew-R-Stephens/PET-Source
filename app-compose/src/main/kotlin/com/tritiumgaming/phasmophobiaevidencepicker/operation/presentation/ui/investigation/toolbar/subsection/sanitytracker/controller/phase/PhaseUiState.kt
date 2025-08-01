package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.sanitytracker.controller.phase

data class PhaseUiState(
    internal val currentPhase: Phase = Phase.SETUP,
    internal val canFlash: Boolean = true,
    internal val canAlertAudio: Boolean = false
)
