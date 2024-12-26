package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.marketplace.billing

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.mainmenus.MainMenuFirebaseScreen


@Composable
@Preview
private fun MarketplaceBillingScreenPreview() {
    MarketplaceBillingScreen()
}

@Composable
fun MarketplaceBillingScreen(
    //content: @Composable () -> Unit
) {

    MainMenuFirebaseScreen (
        content = { MarketplaceBillingContent() }
    )

}


@Composable
private fun MarketplaceBillingContent() {}