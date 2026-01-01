package com.tritiumgaming.phasmophobiaevidencepicker

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.feature.marketplace.ui.store.components.PaletteCard
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.model.PaletteResources

/*@Composable
@Preview
private fun Test() {

    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(PaletteResources.PaletteType.entries) { type ->

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
}*/


@Composable
@Preview
private fun Test1() {

    PaletteCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        marketPalette = MarketPalette(
            uuid = "4324132",
            name = "Test",
            palette = PaletteResources.PaletteType.CONTENT_CREATOR,
            buyCredits = 69
        )
    )
}


@Composable
@Preview
private fun Test2() {

    PaletteCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        marketPalette = MarketPalette(
            uuid = "4324132",
            name = "Test",
            palette = PaletteResources.PaletteType.COMMISSIONER,
            buyCredits = 69
        )
    )
}
