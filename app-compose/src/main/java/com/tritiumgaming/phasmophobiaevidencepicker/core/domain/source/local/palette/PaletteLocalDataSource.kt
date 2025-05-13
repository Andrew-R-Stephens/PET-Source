package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.local.palette

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

interface PaletteLocalDataSource {
    val palettes: Map<String, ExtendedPalette>
}