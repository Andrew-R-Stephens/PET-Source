package com.tritiumgaming.feature.marketplace.ui.store

import androidx.compose.runtime.Immutable
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette

data class MarketCatalogScreenUiState(
    val items: List<ShopScreenUiItem> = emptyList()
)

@Immutable
sealed interface ShopScreenUiItem {
    val key: String

    data class Header(val name: String) : ShopScreenUiItem {
        override val key: String = "header_$name"
    }

    data class Palette(
        val marketPalette: MarketPalette,
        val paletteResource: ExtendedPalette
    ) : ShopScreenUiItem {
        override val key: String = marketPalette.uuid
    }

    data class PaletteBundle(
        override val key: String,
        val marketBundle: MarketBundle,
        val marketPalettes: List<MarketPalette>,
        val unlocked: Boolean
    ) : ShopScreenUiItem
}
