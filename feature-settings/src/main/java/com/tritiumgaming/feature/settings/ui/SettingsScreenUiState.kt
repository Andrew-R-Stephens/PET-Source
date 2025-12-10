package com.tritiumgaming.feature.settings.ui

data class SettingsScreenUiState(
    val screensaverPreference: Boolean = false,
    val networkPreference: Boolean = true,
    val huntWarningAudioPreference: Boolean = true,
    val ghostReorderPreference: Boolean = true,
    val introductionPermissionPreference: Boolean = true,
    val rTLPreference: Boolean = true,
    val huntWarnDurationPreference: Long = SettingsScreenViewModel.Companion.FOREVER
)