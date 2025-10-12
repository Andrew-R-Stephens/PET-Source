package com.tritiumgaming.feature.home.ui.marketplace

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.feature.home.ui.MainMenuScreen


@Composable
@Preview
private fun MarketplaceScreenPreview() {
    MarketplaceScreen()
}

@Composable
fun MarketplaceScreen(
    //content: @Composable () -> Unit
) {

    MainMenuScreen(
        content = { MarketplaceContent() }
    )

}


@Composable
private fun MarketplaceContent() {



}