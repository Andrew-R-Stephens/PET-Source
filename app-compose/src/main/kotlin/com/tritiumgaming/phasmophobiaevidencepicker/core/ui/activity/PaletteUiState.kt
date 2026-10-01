package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import com.tritiumgaming.data.palette.mappers.LocalDefaultPalette
import com.tritiumgaming.data.palette.mappers.PaletteResources

internal data class PaletteUiState(
    val palette: PaletteResources.PaletteType = LocalDefaultPalette
)
