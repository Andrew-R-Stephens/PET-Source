package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.marketplace

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuScreen


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