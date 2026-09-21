package com.tritiumgaming.feature.marketplace.ui.store.typographies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.ui.mapper.toTypographyResource
import com.tritiumgaming.core.ui.preview.DevicePreviews
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogTypographiesUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketplaceScreen
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources
import com.tritiumgaming.shared.data.market.typography.model.MarketTypography

@DevicePreviews
@Composable
@Preview
private fun TypographyShopPreview() {
    TypographyShopContent(
        MarketCatalogTypographiesUiState(
            typographies = listOf(
                MarketTypography(
                    ""
                ),
                MarketTypography(
                    "1"
                ),
                MarketTypography(
                    "2"
                )
            )
        )
    )
}

@Composable
fun TypographyShopScreen(
    navController: NavHostController = rememberNavController(),
    viewmodel: MarketplaceTypographiesScreenViewModel
) {
    val user = if(!LocalInspectionMode.current) Firebase.auth.currentUser else null

    val isAgreementShown by viewmodel.marketplaceAgreementUiState.collectAsStateWithLifecycle()
    val showAgreementDialog by viewmodel.showAgreementDialog.collectAsStateWithLifecycle()

    val accountCredits by viewmodel.accountCreditsUiState.collectAsStateWithLifecycle()

    //val unlocks by viewmodel.marketCatalogTypographiesUiState.collectAsStateWithLifecycle()

    MarketplaceScreen(
        modifier = Modifier,
        navController = navController,
        earnedCredits = accountCredits.earnedCredits,
        showRewardButton = user != null,
        showAgreementDialog = showAgreementDialog,
        onConfirmAgreement = {
            viewmodel.setMarketplaceAgreementAccepted()
        },
        onClickRewardButton = {
            // handle reward button click if needed in this screen
        },
        accountContent = { },
    ) { modifier ->

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {

            TypographyShopContent(
                //unlocks = unlocks
                unlocks = MarketCatalogTypographiesUiState(
                    typographies = listOf(
                        MarketTypography(
                            "0",
                            name = "Test Typography 0",
                            buyCredits = 100,
                            typography = TypographyResources.TypographyType.NEUCHA
                        ),
                        MarketTypography(
                            "1",
                            name = "Test Typography 1",
                            buyCredits = 100,
                            typography = TypographyResources.TypographyType.JETBRAINS_MONO
                        ),
                        MarketTypography(
                            "2",
                            name = "Test Typography 2",
                            buyCredits = 100,
                            typography = TypographyResources.TypographyType.JOURNAL
                        ),
                    )
                ),
                onBuyClick = {
                    viewmodel.obtainItemWithCredits(
                        it.uuid, "typography",
                        onSuccess = { _ ->
                            // Toast
                        }
                    )
                }
            )
        }
    }
}


@Composable
private fun TypographyShopContent(
    unlocks: MarketCatalogTypographiesUiState,
    onBuyClick: (MarketTypography) -> Unit = {}
) {

    CategoryList(
        modifier = Modifier,
        unlocks = unlocks.typographies,
        onBuyClick = onBuyClick
    )
}

@Composable
private fun CategoryList(
    modifier: Modifier = Modifier,
    unlocks: List<MarketTypography> = emptyList(),
    onBuyClick: (MarketTypography) -> Unit = {}
) {

    LazyColumn (
        modifier = modifier
            .widthIn(max = 480.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(items = unlocks, key = { it.uuid })  { marketCatalogEntry ->

            marketCatalogEntry.typography?.toTypographyResource()?.let { typography ->

                TypographyCard(
                    modifier = Modifier,
                    typography = typography,
                    title = marketCatalogEntry.name ?: "Error",
                    buyCredits = marketCatalogEntry.buyCredits,
                    onBuyClick = { onBuyClick(marketCatalogEntry) }
                )

            }

        }

    }

}

