package com.tritiumgaming.shared.data.investigation.model


data class PhaseData(
    val canAlertAudio: Boolean = false,
    val startFlashTime: Long = DEFAULT,
    val elapsedFlashTime: Long = DEFAULT
) {
    companion object {
        const val DURATION_30_SECONDS = 300000L
        const val DEFAULT = 300000L
    }
}
