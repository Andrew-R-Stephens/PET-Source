package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.usecase.preference

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.palette.repository.MarketPaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palettes.ExtendedPalette

class GetPaletteByUUIDUseCase(
    private val repository: MarketPaletteRepository
) {

    operator fun invoke(
        uuid: String, defaultPalette: ExtendedPalette
    ): ExtendedPalette =
        repository.getPalettes().find { it.uuid == uuid }?.palette ?: defaultPalette

}