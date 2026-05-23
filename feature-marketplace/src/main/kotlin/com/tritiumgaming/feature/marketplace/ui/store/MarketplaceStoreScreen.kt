package com.tritiumgaming.feature.marketplace.ui.store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel
import com.tritiumgaming.feature.marketplace.ui.store.components.PaletteCard
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
private fun MarketplaceStoreScreenPreview() {
    MarketplaceStoreContent(
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
fun MarketplaceStoreScreen(
    navController: NavHostController = rememberNavController(),
    marketplaceViewModel: MarketplaceViewModel = viewModel(factory = MarketplaceViewModel.Factory)
) {
    val paletteUnlocks by marketplaceViewModel.marketCatalogPalettesUiState.collectAsStateWithLifecycle()

    MarketplaceStoreContent(
        unlocks = paletteUnlocks
    )

}


@Composable
private fun MarketplaceStoreContent(
    unlocks: MarketCatalogPalettesUiState
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        AccountDetails()

        Storefront(
            unlocks = unlocks
        )

    }

}


@Composable
private fun AccountDetails() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {



    }

}

@Composable
private fun Storefront(
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

            val uuid = marketCatalogEntry.uuid

            val palette = marketCatalogEntry.palette
            val cost = marketCatalogEntry.buyCredits
            val name = marketCatalogEntry.name

            PaletteCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                marketPalette = MarketPalette(
                    uuid = uuid,
                    name = name,
                    palette = palette,
                    buyCredits = cost
                )
            )

        }

    }

}

