package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.marketplace

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.mainmenus.MainMenuFirebaseScreen


@Composable
@Preview
private fun MarketplaceScreenPreview() {
    MarketplaceScreen()
}

@Composable
fun MarketplaceScreen(
    //content: @Composable () -> Unit
) {

    MainMenuFirebaseScreen (
        content = { MarketplaceContent() }
    )

}


@Composable
private fun MarketplaceContent() {



}