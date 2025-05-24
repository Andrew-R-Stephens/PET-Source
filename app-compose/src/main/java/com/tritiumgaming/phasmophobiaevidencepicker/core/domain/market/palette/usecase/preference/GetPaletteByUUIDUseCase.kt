package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.model.MarketPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

class GetPaletteByUUIDUseCase(
    private val repository: PaletteRepository
) {

    operator fun invoke(
        palettes: Map<String, MarketPalette>,
        uuid: String, defaultPalette: ExtendedPalette
    ): ExtendedPalette =
        palettes[uuid]?.palette ?: defaultPalette

}