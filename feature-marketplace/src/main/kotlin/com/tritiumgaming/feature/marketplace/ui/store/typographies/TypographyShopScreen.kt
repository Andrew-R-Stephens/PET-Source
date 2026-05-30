package com.tritiumgaming.feature.marketplace.ui.store.typographies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.ui.mapper.toTypographyResource
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel
import com.tritiumgaming.feature.marketplace.ui.store.palettes.PaletteCard
import com.tritiumgaming.feature.marketplace.ui.store.MarketCatalogPalettesUiState
import com.tritiumgaming.feature.marketplace.ui.store.MarketCatalogTypographiesUiState
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.typography.model.MarketTypography

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Small Phone", device = "id:small_phone")
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
@Preview(name = "Medium Phone Portrait", device = "spec:width=411dp,height=891dp")
@Preview(name = "Medium Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Medium Tablet Portrait", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private annotation class DevicePreviews

@DevicePreviews
@Composable
@Preview
private fun TypographyShopPreview() {
    TypographyShopContent(
        MarketCatalogTypographiesUiState(
            typographies = listOf(
                MarketTypography(
                    ""
                ),
                MarketTypography(
                    "1"
                ),
                MarketTypography(
                    "2"
                )
            )
        )
    )
}

@Composable
fun TypographyShopScreen(
    navController: NavHostController = rememberNavController(),
    marketplaceViewModel: MarketplaceViewModel = viewModel(factory = MarketplaceViewModel.Factory)
) {
    val unlocks by marketplaceViewModel.marketCatalogTypographiesUiState.collectAsStateWithLifecycle()

    TypographyShopContent(
        unlocks = unlocks
    )

}


@Composable
private fun TypographyShopContent(
    unlocks: MarketCatalogTypographiesUiState
) {

    CategoryList(
        modifier = Modifier,
        unlocks = unlocks.typographies
    )
}

@Composable
private fun CategoryList(
    modifier: Modifier = Modifier,
    unlocks: List<MarketTypography> = emptyList()
) {

    LazyColumn (
        modifier = modifier
            .widthIn(max = 480.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(items = unlocks, key = { it.uuid })  { marketCatalogEntry ->

            marketCatalogEntry.typography?.toTypographyResource()?.let { typography ->

                TypographyCard(
                    modifier = Modifier,
                    typography = typography,
                    title = marketCatalogEntry.name ?: "Error",
                    buyCredits = marketCatalogEntry.buyCredits,
                    onBuyClick = { }
                )

            }

        }

    }

}

