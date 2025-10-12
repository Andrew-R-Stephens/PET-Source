package com.tritiumgaming.feature.home.ui.appsettings

import com.tritiumgaming.feature.home.ui.appsettings.SettingsScreenViewModel.Companion.FOREVER

data class SettingsScreenUiState(
    val screensaverPreference: Boolean = false,
    val networkPreference: Boolean = true,
    val huntWarningAudioPreference: Boolean = true,
    val ghostReorderPreference: Boolean = true,
    val introductionPermissionPreference: Boolean = true,
    val rTLPreference: Boolean = true,
    val huntWarnDurationPreference: Long = FOREVER
)