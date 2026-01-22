package com.tritiumgaming.feature.settings.ui

class SettingsScreenUiActions(
    val onSetScreenSaverPreference: (state: Boolean) -> Unit,
    val onSetNetworkPreference: (state: Boolean) -> Unit,
    val onSetRTLPreference: (state: Boolean) -> Unit,
    val onSetHuntWarningAudioPreference: (state: Boolean) -> Unit,
    val onSetGhostReorderPreference: (state: Boolean) -> Unit,
    val onSetHuntTimeoutPreference: (state: Long) -> Unit,
    val paletteCarouselUiActions: PaletteCarouselUiActions,
    val typographyCarouselUiActions: TypographyCarouselUiActions
)