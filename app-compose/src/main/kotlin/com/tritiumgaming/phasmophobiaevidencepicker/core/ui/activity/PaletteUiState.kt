package com.tritiumgaming.phasmophobiaevidencepicker.core.ui.activity

import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalDefaultPalette

internal data class PaletteUiState(
    val uuid: String = LocalDefaultPalette.uuid,
    val palette: ExtendedPalette = LocalDefaultPalette.palette
)
