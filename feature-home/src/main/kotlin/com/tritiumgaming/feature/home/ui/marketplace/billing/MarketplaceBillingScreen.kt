package com.tritiumgaming.feature.home.ui.marketplace.billing

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.feature.home.ui.MainMenuScreen


@Composable
@Preview
private fun MarketplaceBillingScreenPreview() {
    MarketplaceBillingScreen()
}

@Composable
fun MarketplaceBillingScreen() {

    MainMenuScreen {
        MarketplaceBillingContent()
    }

}


@Composable
private fun MarketplaceBillingContent() {}