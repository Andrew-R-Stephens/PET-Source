package com.tritiumgaming.shared.data.operation.model

import com.tritiumgaming.shared.data.phase.mappers.PhaseResources

data class PhaseData(
    val type: PhaseResources.PhaseIdentifier = PhaseResources.PhaseIdentifier.SETUP,
    val canAlertAudio: Boolean = false,
    val canFlash: Boolean = true,
    val startFlashTime: Long = DEFAULT,
    val elapsedFlashTime: Long = DEFAULT,
    val maxFlashTime: Long = DURATION_30_SECONDS,
) {
    companion object {
        const val DURATION_30_SECONDS = 300000L
        const val DEFAULT = 300000L
    }
}