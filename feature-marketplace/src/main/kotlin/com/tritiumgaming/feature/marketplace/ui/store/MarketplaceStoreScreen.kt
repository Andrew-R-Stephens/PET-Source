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

@Composable
@Preview
private fun MarketplaceStoreScreenPreview() {
    MarketplaceStoreScreen()
}

@Composable
fun MarketplaceStoreScreen(
    navController: NavHostController = rememberNavController(),
    marketplaceViewModel: MarketplaceViewModel = viewModel(factory = MarketplaceViewModel.Factory)
) {

    MarketplaceStoreContent(
        marketplaceViewModel = marketplaceViewModel
    )

}


@Composable
private fun MarketplaceStoreContent(
    marketplaceViewModel: MarketplaceViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        AccountDetails()

        Storefront(
            marketplaceViewModel = marketplaceViewModel
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
    marketplaceViewModel: MarketplaceViewModel

) {
    val unlocks = marketplaceViewModel.marketCatalogPalettesUiState.collectAsStateWithLifecycle()

    LazyColumn (
        modifier = modifier
            .widthIn(max = 480.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(items = unlocks.value.palettes, key = { it.uuid }) { marketCatalogEntry ->

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

