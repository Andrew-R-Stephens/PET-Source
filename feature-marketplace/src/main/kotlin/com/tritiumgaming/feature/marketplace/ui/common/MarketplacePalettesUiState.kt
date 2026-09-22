package com.tritiumgaming.feature.marketplace.ui.common

import androidx.compose.runtime.Immutable
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.data.market.metadata.mappers.MarketplaceResources
import com.tritiumgaming.shared.data.market.metadata.mappers.MarketplaceResources.MarketplaceCategoryTitles
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.typography.model.MarketTypography

data class MarketCatalogScreenUiState(
    val items: List<ShopScreenUiItem> = emptyList()
)

@Immutable
data class BundlePricingUiState(
    val listPriceTotal: Long = 0L,
    val bundlePrice: Long = 0L,
    val bundleDiscount: Long = 0L,
    val bundleDiscountRatio: Float = 0f,
    val proratedDiscount: Long = 0L,
    val proratedDiscountRatio: Float = 0f,
    val finalPrice: Long = 0L,
    val isQualified: Boolean = false,
    val hasDiscount: Boolean = false,
    val discountPerItem: Float = 0f
)

@Immutable
sealed interface ShopScreenUiItem {
    val key: String

    data class Header(
        val name: MarketplaceCategoryTitles
    ) : ShopScreenUiItem {
        override val key: String = "header_${name.value}"
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
        val unlocked: Boolean,
        val pricing: BundlePricingUiState
    ) : ShopScreenUiItem

    data class TypographyBundle(
        override val key: String,
        val marketBundle: MarketBundle,
        val marketTypographies: List<MarketTypography>,
        val unlocked: Boolean,
        val pricing: BundlePricingUiState
    ) : ShopScreenUiItem

}
