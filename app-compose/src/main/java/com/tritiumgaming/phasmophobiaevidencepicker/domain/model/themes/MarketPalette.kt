package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes

import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.theme.palettes.ExtendedPalette

data class MarketPalette (
    val marketThemeEntity: MarketPaletteEntity? = null,
    private val uuid: String = marketThemeEntity?.uuid ?: "",
    val palette: ExtendedPalette
) {
    fun getAsPair(): Pair<String, MarketPalette> {
        return Pair(uuid, this)
    }
}