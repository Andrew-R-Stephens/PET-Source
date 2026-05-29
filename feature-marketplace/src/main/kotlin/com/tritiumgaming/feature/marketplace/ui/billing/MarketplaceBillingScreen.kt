package com.tritiumgaming.feature.marketplace.ui.billing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.theme.white_M100
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel
import com.tritiumgaming.shared.data.market.billable.model.MarketBillable

@Composable
@Preview
private fun MarketplaceBillingScreenPreview() {
    MarketplaceBillingContent(
        listOf(
            MarketBillable(
                productId = "",
                type = "Billable",
                tier = 2,
                rewardAmount = 1000,
                rewardItem = "",
                activeStatus = true
            )
        )
    )
}

@Composable
fun MarketplaceBillingScreen(
    navController: NavHostController = rememberNavController(),
    marketplaceViewModel: MarketplaceViewModel = viewModel(factory = MarketplaceViewModel.Factory)
) {
    val billables by marketplaceViewModel.marketCatalogBillablesUiState.collectAsStateWithLifecycle()

    MarketplaceBillingContent(
        billables = billables.billables,
        onSelectBillable = {
            //navController.navigate()
        }
    )
}

@Composable
private fun MarketplaceBillingContent(
    billables: List<MarketBillable>,
    onSelectBillable: () -> Unit = {}
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(billables) { billable ->
            BillableCard(billable = billable)
        }
    }
}

@Composable
private fun BillableCard(
    billable: MarketBillable,
    onBuyClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = white_M100)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "${billable.rewardAmount} Credits",
                    style = LocalTypography.current.primary.bold,
                    color = LocalPalette.current.onSurface
                )
                Text(
                    text = billable.productId,
                    style = LocalTypography.current.quaternary.regular,
                    color = LocalPalette.current.onSurfaceVariant
                )
            }
            Button(
                onClick = onBuyClick,
                shape = RoundedCornerShape(4.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.secondary)
            ) {
                Text(text = "BUY")
            }
        }
    }
}
