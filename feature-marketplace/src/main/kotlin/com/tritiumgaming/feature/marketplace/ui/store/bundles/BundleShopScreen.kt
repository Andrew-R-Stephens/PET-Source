package com.tritiumgaming.feature.marketplace.ui.store.bundles

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.feature.marketplace.ui.common.MarketplaceScreen
import com.tritiumgaming.feature.marketplace.ui.common.PaletteBundleCard
import com.tritiumgaming.feature.marketplace.ui.store.MarketCatalogScreenUiState
import com.tritiumgaming.feature.marketplace.ui.store.ShopScreenUiItem
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType
import com.tritiumgaming.shared.data.market.palette.mappers.asUuid
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Small Phone", device = "id:small_phone")
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
@Preview(name = "Medium Phone Portrait", device = "spec:width=411dp,height=891dp")
@Preview(name = "Medium Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Medium Tablet Portrait", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private annotation class DevicePreviews

@Composable
fun BundleShopScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewmodel: MarketplaceBundlesScreenViewModel
) {
    val context = LocalContext.current

    val bundleUnlocks by viewmodel.marketCatalogScreenUiState.collectAsStateWithLifecycle()
    var isLoading by remember { mutableStateOf(false) }

    val user = if(!LocalInspectionMode.current) Firebase.auth.currentUser else null

    val credits by viewmodel.accountCreditsUiState.collectAsStateWithLifecycle()

    MarketplaceScreen(
        modifier = Modifier,
        navController = navController,
        credits = credits.earnedCredits,
        onEarnCredits = {
            viewmodel.addCredits(
                credits = 100,
                onSuccess = {
                    Toast.makeText(context, "Credits Earned",
                        Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    Toast.makeText(context, "Error! $it",
                        Toast.LENGTH_SHORT).show()
                }
            )
        }
    ) { modifier ->
        BundleShopContent(
            modifier = modifier,
            unlocks = bundleUnlocks,
            authenticated = user != null,
            onBuyBundle = { marketPalette ->
                isLoading = true
                viewmodel.obtainItemWithCredits(
                    marketPalette.uuid, "bundle",
                    onSuccess = { _ ->
                        Toast.makeText(context, "Bundle Unlocked!", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = { message ->
                        Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
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

@Composable
private fun BundleShopContent(
    modifier: Modifier,
    authenticated: Boolean = false,
    unlocks: MarketCatalogScreenUiState,
    onBuyBundle: (marketBundle: MarketBundle) -> Unit = { }
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        else -> {
            PortraitContent(
                modifier = modifier,
                authenticated = authenticated,
                unlocks = unlocks,
                onBuyBundle = onBuyBundle
            )
        }
    }

}

@Composable
private fun PortraitContent(
    modifier: Modifier,
    authenticated: Boolean = false,
    unlocks: MarketCatalogScreenUiState,
    onBuyBundle: (marketBundle: MarketBundle) -> Unit = { }
) {
    Box(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
        ) {
            items(
                items = unlocks.items,
                key = { it.key }
            ) { item ->
                when (item) {
                    is ShopScreenUiItem.Header -> {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            color = LocalPalette.current.surfaceContainerHigh,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                text = item.name,
                                style = LocalTypography.current.primary.bold.copy(
                                    textAlign = TextAlign.Center
                                ),
                                color = LocalPalette.current.onSurface,
                                maxLines = 1,
                                fontSize = 36.sp
                            )
                        }
                    }

                    is ShopScreenUiItem.PaletteBundle -> {
                        PaletteBundleCard(
                            modifier = Modifier,
                            uuid = item.key,
                            buyCredits = item.marketBundle.buyCredits,
                            title = item.marketBundle.name,
                            surfaceContainerHigh = LocalPalette.current.surfaceContainerHigh,
                            onSurfaceVariant = LocalPalette.current.onSurfaceVariant,
                            onSurface = LocalPalette.current.onSurface,
                            scrim = LocalPalette.current.scrim,
                            items = item.marketPalettes,
                            canUnlock = authenticated,
                            isOwned = item.unlocked,
                            onBuyClick = {
                                onBuyBundle(item.marketBundle)
                            }
                        )
                    }
                    else -> {}
                }
            }
        }
    }

}

@Composable
private fun LandscapeContent(
    modifier: Modifier,
    authenticated: Boolean = false,
    unlocks: MarketCatalogScreenUiState,
    onBuyBundle: (marketBundle: MarketBundle) -> Unit = { }
) {
    Box(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top)
        ) {
            items(
                items = unlocks.items,
                key = { it.key }
            ) { item ->
                when (item) {
                    is ShopScreenUiItem.Header -> {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            color = LocalPalette.current.surfaceContainerHigh,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                text = item.name,
                                style = LocalTypography.current.primary.bold.copy(
                                    textAlign = TextAlign.Center
                                ),
                                color = LocalPalette.current.onSurface,
                                maxLines = 1,
                                fontSize = 36.sp
                            )
                        }
                    }

                    is ShopScreenUiItem.PaletteBundle -> {
                        PaletteBundleCard(
                            modifier = Modifier,
                            uuid = item.key,
                            buyCredits = item.marketBundle.buyCredits,
                            title = item.marketBundle.name,
                            surfaceContainerHigh = LocalPalette.current.surfaceContainerHigh,
                            onSurfaceVariant = LocalPalette.current.onSurfaceVariant,
                            onSurface = LocalPalette.current.onSurface,
                            scrim = LocalPalette.current.scrim,
                            items = item.marketPalettes,
                            canUnlock = authenticated,
                            isOwned = item.unlocked,
                            onBuyClick = {
                                onBuyBundle(item.marketBundle)
                            }
                        )
                    }
                    else -> {}
                }
            }
        }
    }

}

@DevicePreviews
@Composable
@Preview
private fun BundleShopPreview() {
    val palette1 = PaletteType.HALLOWEEN_23
    val marketPalette1 = MarketPalette(
        uuid = palette1.asUuid(),
        group = "Specialist",
        palette = palette1
    )

    val palette2 = PaletteType.COMMISSIONER
    val marketPalette2 = MarketPalette(
        uuid = palette2.asUuid(),
        group = "Bundle",
        palette = palette2
    )

    LocalThemeProvider {
        BundleShopContent(
            modifier = Modifier
                .widthIn(max = 450.dp)
                .fillMaxSize(),
            unlocks = MarketCatalogScreenUiState(
                items = listOf(
                    ShopScreenUiItem.Header("Bundle"),
                    ShopScreenUiItem.PaletteBundle(
                        "",
                        MarketBundle("", ""),
                        listOf(marketPalette2),
                        false
                    )
                )
            )
        )
    }
}
