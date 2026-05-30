package com.tritiumgaming.feature.marketplace.ui.store.palettes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel
import com.tritiumgaming.feature.marketplace.ui.store.MarketCatalogPalettesUiState
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette

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
private fun PaletteShopPreview() {
    PaletteShopContent(
        MarketCatalogPalettesUiState(
            listOf(
                MarketPalette(
                    ""
                ),
                MarketPalette(
                    "1"
                ),
                MarketPalette(
                    "2"
                )
            )
        )
    )
}

@Composable
fun PaletteShopScreen(
    navController: NavHostController = rememberNavController(),
    marketplaceViewModel: MarketplaceViewModel = viewModel(factory = MarketplaceViewModel.Factory)
) {
    val paletteUnlocks by marketplaceViewModel.marketCatalogPalettesUiState.collectAsStateWithLifecycle()

    PaletteShopContent(
        unlocks = paletteUnlocks
    )

}


@Composable
private fun PaletteShopContent(
    unlocks: MarketCatalogPalettesUiState
) {

    Column(

    ) {
        //Bundles
        CategoryList(
            unlocks = unlocks
        )
        //Cat 1
        CategoryList(
            unlocks = unlocks
        )
        //Cat 2
        CategoryList(
            unlocks = unlocks
        )
        //Cat 3
        CategoryList(
            unlocks = unlocks
        )
    }

}

@Composable
private fun CategoryList(
    modifier: Modifier = Modifier,
    unlocks: MarketCatalogPalettesUiState
) {

    LazyColumn (
        modifier = modifier
            .widthIn(max = 480.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(items = unlocks.palettes, key = { it.uuid }) { marketCatalogEntry ->

            marketCatalogEntry.palette?.toPaletteResource()?.let { palette ->
                PaletteCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    buyCredits = marketCatalogEntry.buyCredits,
                    badgeRes = palette.extrasFamily.badge,
                    title = stringResource(palette.extrasFamily.title),
                    surfaceContainerHigh = palette.surfaceContainerHigh,
                    onSurfaceVariant = palette.onSurfaceVariant,
                    onSurface = palette.onSurface,
                    primary = palette.primary,
                    secondary = palette.secondary,
                    tertiary = palette.tertiary,
                    surfaceContainer = palette.surfaceContainer,
                    primaryContainer = palette.primaryContainer,
                    secondaryContainer = palette.secondaryContainer,
                    tertiaryContainer = palette.tertiaryContainer,
                    onBuyClick = {}
                )
            }



        }

    }

}

