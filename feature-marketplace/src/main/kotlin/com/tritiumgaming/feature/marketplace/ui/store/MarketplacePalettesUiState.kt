package com.tritiumgaming.feature.marketplace.ui.store

import androidx.compose.runtime.Immutable
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette

data class MarketCatalogPalettesUiState(
    val items: List<PaletteShopUiItem> = emptyList()
)

@Immutable
sealed interface PaletteShopUiItem {
    val key: String

    data class Header(val name: String) : PaletteShopUiItem {
        override val key: String = "header_$name"
    }

    data class Palette(
        val marketPalette: MarketPalette,
        val paletteResource: ExtendedPalette
    ) : PaletteShopUiItem {
        override val key: String = marketPalette.uuid
    }

    data class Bundle(
        override val key: String,
        val marketPalettes: List<MarketPalette>
    ) : PaletteShopUiItem
}
