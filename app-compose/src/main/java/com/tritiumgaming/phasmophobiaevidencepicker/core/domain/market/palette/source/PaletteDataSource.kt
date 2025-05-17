package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

interface PaletteDataSource {
    val palettes: Map<String, ExtendedPalette>
}