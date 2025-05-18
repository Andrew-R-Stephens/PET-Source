package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.PaletteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalettesMap

class PaletteLocalDataSource : PaletteDataSource<Map<String, ExtendedPalette>> {

    override suspend fun fetchAll(): Map<String, ExtendedPalette> = LocalPalettesMap

}