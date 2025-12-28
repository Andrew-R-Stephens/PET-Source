package com.tritiumgaming.feature.marketplace.ui.store

import com.tritiumgaming.shared.data.market.palette.model.MarketPalette

data class MarketCatalogPalettesUiState(
    val palettes: List<MarketPalette> = emptyList()
)
