package com.tritiumgaming.feature.settings.ui

import com.tritiumgaming.feature.settings.ui.components.TypographyUiState
import com.tritiumgaming.shared.data.preferences.model.properties.DensityType

data class SettingsScreenUiState(
    val screensaverPreference: Boolean = false,
    val networkPreference: Boolean = true,
    val huntWarningAudioPreference: Boolean = true,
    val ghostReorderPreference: Boolean = true,
    val introductionPermissionPreference: Boolean = true,
    val rTLPreference: Boolean = true,
    val uiDensityType: DensityType = DensityType.COMFORTABLE,
    val huntWarnDurationPreference: Long = SettingsScreenViewModel.FOREVER,
    val paletteUiState: PaletteUiState = PaletteUiState(),
    val typographyUiState: TypographyUiState = TypographyUiState(),
    val analyticsPreference: Boolean = false,
    val adPrivacyPreference: Boolean = false,
    val isPrivacyOptionsRequired: Boolean? = null,
)
