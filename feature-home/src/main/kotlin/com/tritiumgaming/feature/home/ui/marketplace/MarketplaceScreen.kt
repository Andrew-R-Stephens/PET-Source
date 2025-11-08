package com.tritiumgaming.feature.home.ui.marketplace

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.feature.home.ui.HomeScreen


@Composable
@Preview
private fun MarketplaceScreenPreview() {
    MarketplaceScreen()
}

@Composable
fun MarketplaceScreen(
    //content: @Composable () -> Unit
) {

    HomeScreen(
        content = { MarketplaceContent() }
    )

}


@Composable
private fun MarketplaceContent() {



}