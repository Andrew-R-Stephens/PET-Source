package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.local.PaletteLocalDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalettesMap

class PaletteLocalDataSource(
    override val palettes: Map<String, ExtendedPalette> = LocalPalettesMap
) : PaletteLocalDataSource
