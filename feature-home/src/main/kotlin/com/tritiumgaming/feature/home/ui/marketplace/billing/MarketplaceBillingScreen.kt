package com.tritiumgaming.feature.home.ui.marketplace.billing

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.feature.home.ui.HomeScreen


@Composable
@Preview
private fun MarketplaceBillingScreenPreview() {
    MarketplaceBillingScreen()
}

@Composable
fun MarketplaceBillingScreen() {

    HomeScreen {
        MarketplaceBillingContent()
    }

}


@Composable
private fun MarketplaceBillingContent() {}