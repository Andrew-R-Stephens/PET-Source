package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.marketplace.billing

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.presentation.ui.mainmenus.MainMenuFirebaseScreen


@Composable
@Preview
private fun MarketplaceBillingScreenPreview() {
    MarketplaceBillingScreen()
}

@Composable
fun MarketplaceBillingScreen(
    //content: @Composable () -> Unit
) {

    MainMenuFirebaseScreen(
        content = { MarketplaceBillingContent() }
    )

}


@Composable
private fun MarketplaceBillingContent() {}