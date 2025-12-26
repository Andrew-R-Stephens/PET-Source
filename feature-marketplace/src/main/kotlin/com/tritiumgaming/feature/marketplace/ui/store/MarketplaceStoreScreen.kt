package com.tritiumgaming.feature.marketplace.ui.store

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel
import com.tritiumgaming.feature.marketplace.ui.store.components.PaletteCard
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.model.PaletteResources

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

    LazyColumn (
        modifier = Modifier
            .widthIn(max = 480.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = PaletteResources.PaletteType.entries) { type ->

            PaletteCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                marketPalette = MarketPalette(
                    uuid = "4324132",
                    name = "Test",
                    palette = type,
                    buyCredits = 69
                )
            )

        }

    }

}

