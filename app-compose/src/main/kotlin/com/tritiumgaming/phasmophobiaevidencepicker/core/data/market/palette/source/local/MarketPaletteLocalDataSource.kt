package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.palette.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.source.LocalPaletteDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.LocalPalettesMap

class MarketPaletteLocalDataSource : LocalPaletteDataSource<Map<String, ExtendedPalette>> {

    override fun getPalettes(): Result<Map<String, ExtendedPalette>> =
        Result.success(LocalPalettesMap)

}