package com.tritiumstudios.data.operation.model

import com.tritiumstudios.data.phase.mappers.PhaseResources.PhaseIdentifier

data class PhaseData(
    val type: PhaseIdentifier = PhaseIdentifier.SETUP,
    val canAlertAudio: Boolean = false,
    val canFlash: Boolean = true,
    val startFlashTime: Long = DEFAULT,
    val elapsedFlashTime: Long = DEFAULT,
    val maxFlashTime: Long = DURATION_30_SECONDS,
) {
    companion object {
        const val DURATION_30_SECONDS = 30000L
        const val DEFAULT = 0L
    }
}