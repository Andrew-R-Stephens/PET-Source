package com.tritiumstudios.data.operation.model

data class OperationUserPreferences(
    val enableGhostReorder: Boolean = false,
    val maxHuntWarnFlashTime: Long = 0L,
    val allowHuntWarnAudio: Boolean = false
)
