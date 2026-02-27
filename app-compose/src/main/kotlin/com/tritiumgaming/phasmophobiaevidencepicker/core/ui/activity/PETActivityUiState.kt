package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

internal data class PETActivityUiState(
    /** Represents current initialization states for the Google Mobile Ads SDK. */
    val isMobileAdsInitialized: Boolean = false,
    /** Indicates whether the app has completed the steps for gathering updated user consent. */
    val canRequestAds: Boolean = false,
    /** Indicates whether a privacy options form is required. */
    val isPrivacyOptionsRequired: Boolean = false,
    val paletteUiState: PaletteUiState = PaletteUiState(),
    val typographyUiState: TypographyUiState = TypographyUiState(),
    val uiConfiguration: UiConfigurationState = UiConfigurationState()
)
