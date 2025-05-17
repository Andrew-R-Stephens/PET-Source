package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalettesMap

class PaletteLocalDataSource(
    override val palettes: Map<String, ExtendedPalette> = LocalPalettesMap
) : PaletteDataSource
