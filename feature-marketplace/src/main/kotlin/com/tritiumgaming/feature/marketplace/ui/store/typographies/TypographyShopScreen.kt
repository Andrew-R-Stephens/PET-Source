package com.tritiumgaming.feature.marketplace.ui.store.typographies

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.ui.common.network.toStringResource
import com.tritiumgaming.core.ui.mapper.toTypographyResource
import com.tritiumgaming.core.ui.preview.DevicePreviews
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.feature.marketplace.ui.common.MarketCatalogTypographiesUiState
import com.tritiumgaming.feature.marketplace.ui.common.MarketplaceScreen
import com.tritiumgaming.shared.core.common.network.FirebaseFunctionError
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
    val context = LocalContext.current
    val activity = LocalActivity.current

    val user = if(!LocalInspectionMode.current) Firebase.auth.currentUser else null

    val isAgreementShown by viewmodel.marketplaceAgreementUiState.collectAsStateWithLifecycle()
    val showAgreementDialog by viewmodel.showAgreementDialog.collectAsStateWithLifecycle()

    val accountCredits by viewmodel.accountCreditsUiState.collectAsStateWithLifecycle()

    val unlocks by viewmodel.marketCatalogTypographiesUiState.collectAsStateWithLifecycle()
    var isLoading by remember { mutableStateOf(false) }

    val onClickRewardedAd: () -> Unit = {
        activity?.let {
            viewmodel.onAttemptRewardedAd(
                activity,
                onSuccess = { quantity, _ ->
                    viewmodel.addCredits(
                        credits = quantity,
                        onSuccess = {
                            Toast.makeText(
                                context, "Credits Earned",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onFailure = { message ->
                            val error = FirebaseFunctionError.fromString(message)
                            Toast.makeText(
                                context,
                                error.toStringResource,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                },
                onFailure = { message ->
                    val error = FirebaseFunctionError.fromString(message)
                    Toast.makeText(
                        context,
                        error.toStringResource,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    MarketplaceScreen(
        modifier = Modifier,
        navController = navController,
        earnedCredits = accountCredits.earnedCredits,
        showRewardButton = user != null,
        showAgreementDialog = showAgreementDialog,
        onConfirmAgreement = {
            viewmodel.setMarketplaceAgreementAccepted()
        },
        onClickRewardButton = onClickRewardedAd,
        accountContent = { },
    ) { modifier ->

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {

            TypographyShopContent(
                unlocks = unlocks,
                onBuyClick = { marketTypography ->
                    isLoading = true
                    viewmodel.obtainItemWithCredits(
                        marketTypography.uuid, "typography",
                        onSuccess = { _ ->
                            Toast.makeText(context, "Typography Unlocked!", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { message ->
                            val error = FirebaseFunctionError.fromString(message)
                            Toast.makeText(
                                context,
                                error.toStringResource,
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onComplete = {
                            isLoading = false
                        }
                    )
                }
            )

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(enabled = false) {}
                        .background(LocalPalette.current.scrim.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = LocalPalette.current.primary
                    )
                }
            }
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
