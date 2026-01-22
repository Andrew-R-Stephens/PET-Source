package com.tritiumgaming.feature.settings.ui

import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalDefaultPalette

data class PaletteUiState(
    val uuid: String = LocalDefaultPalette.uuid,
    val palette: ExtendedPalette = LocalDefaultPalette.palette
)
