package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.repository.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

interface PaletteLocalDataSource {
    val palettes: Map<String, ExtendedPalette>
}