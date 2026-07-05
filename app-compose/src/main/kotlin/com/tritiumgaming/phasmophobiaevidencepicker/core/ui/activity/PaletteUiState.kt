package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import com.tritiumgaming.shared.data.market.palette.mappers.LocalDefaultPalette
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources

internal data class PaletteUiState(
    val palette: PaletteResources.PaletteType = LocalDefaultPalette
)
