package com.tritiumgaming.shared.data.investigation.model

data class InvestigationScreenUserPreferences(
    val enableGhostReorder: Boolean = false,
    val maxHuntWarnFlashTime: Long = 0L,
    val allowHuntWarnAudio: Boolean = false
)
