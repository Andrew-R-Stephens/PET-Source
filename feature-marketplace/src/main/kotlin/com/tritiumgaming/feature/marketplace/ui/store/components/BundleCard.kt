package com.tritiumgaming.feature.marketplace.ui.store.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle


@Composable
fun BundleCard(
    marketBundle: MarketBundle,
    onBuyClick: () -> Unit
) {
    SelectiveTheme {

    }
}

@Composable
private fun BundleIncludedThemeImage(

) {

}

@Composable
@Preview
private fun PreviewBundleCard() {
    BundleCard(
        marketBundle = MarketBundle(
            uuid = "4324132",
            name = "Test",
            items = emptyList()
        )
    ) {

    }
}

@Composable
@Preview
private fun PreviewBundleIncludedItemImage() {
    BundleIncludedThemeImage()
}
