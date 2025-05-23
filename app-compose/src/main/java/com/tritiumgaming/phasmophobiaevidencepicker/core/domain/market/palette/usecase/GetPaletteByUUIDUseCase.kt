package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

class GetPaletteByUUIDUseCase {

    operator fun invoke(
        palettes: Map<String, MarketPalette>,
        uuid: String, defaultPalette: ExtendedPalette
    ): ExtendedPalette =
        palettes[uuid]?.palette ?: defaultPalette

}