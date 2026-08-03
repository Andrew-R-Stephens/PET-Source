package com.tritiumgaming.feature.marketplace.ui.store.palettes

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel
import com.tritiumgaming.feature.marketplace.ui.common.components.EquipConfirmationDialog
import com.tritiumgaming.feature.marketplace.ui.store.MarketCatalogPalettesUiState
import com.tritiumgaming.feature.marketplace.ui.store.PaletteShopUiItem
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
fun PaletteShopScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    marketplaceViewModel: MarketplaceViewModel
) {
    val context = LocalContext.current

    val paletteUnlocks by marketplaceViewModel.marketCatalogPalettesUiState.collectAsStateWithLifecycle()
    var pendingEquipPalette by remember { mutableStateOf<PaletteType?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val user = if(!LocalInspectionMode.current) Firebase.auth.currentUser else null

    Box(modifier) {
        PaletteShopContent(
            modifier = Modifier,
            unlocks = paletteUnlocks,
            authenticated = user != null,
            onBuyItem = { marketPalette ->
                isLoading = true
                marketplaceViewModel.obtainItemWithCredits(
                    marketPalette.uuid, "theme",
                    onSuccess = { _ ->
                        pendingEquipPalette = marketPalette.palette
                    },
                    onFailure = { message ->
                        Toast.makeText(context, "Error: $message", Toast.LENGTH_SHORT).show()
                    },
                    onComplete = {
                        isLoading = false
                    }
                )
            },
            onBuyBundle = { marketPalette ->
                isLoading = true
                marketplaceViewModel.obtainItemWithCredits(
                    marketPalette.uuid, "bundle",
                    onSuccess = { _ ->
                        pendingEquipPalette = null
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

        pendingEquipPalette?.let { palette ->
            val paletteResource = palette.toPaletteResource()
            EquipConfirmationDialog(
                targetTitle = String.format(
                    stringResource(R.string.marketplace_purchase_equip),
                    stringResource(paletteResource.extrasFamily.title)
                ),
                onConfirm = {
                    marketplaceViewModel.updatePalette(palette)
                    pendingEquipPalette = null
                    Toast.makeText(context, "Theme Equipped!", Toast.LENGTH_SHORT).show()
                },
                onDismiss = {
                    pendingEquipPalette = null
                }
            )
        }

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
private fun PaletteShopContent(
    modifier: Modifier,
    authenticated: Boolean = false,
    unlocks: MarketCatalogPalettesUiState,
    onBuyItem: (marketPalette: MarketPalette) -> Unit = { },
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
                onBuyItem = onBuyItem,
                onBuyBundle = onBuyBundle
            )
        }
    }

}

@Composable
private fun PortraitContent(
    modifier: Modifier,
    authenticated: Boolean = false,
    unlocks: MarketCatalogPalettesUiState,
    onBuyItem: (marketPalette: MarketPalette) -> Unit = { },
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
                    is PaletteShopUiItem.Header -> {
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

                    is PaletteShopUiItem.Palette -> {
                        val marketCatalogEntry = item.marketPalette
                        val palette = item.paletteResource

                        PaletteCard(
                            modifier = Modifier,
                            buyCredits = marketCatalogEntry.buyCredits,
                            badgeRes = palette.extrasFamily.badge,
                            title = stringResource(palette.extrasFamily.title),
                            onSurface = palette.onSurface,
                            onSurfaceVariant = palette.onSurfaceVariant,
                            primary = palette.primary,
                            secondary = palette.secondary,
                            tertiary = palette.tertiary,
                            surfaceContainer = palette.surfaceContainer,
                            primaryContainer = palette.primaryContainer,
                            secondaryContainer = palette.secondaryContainer,
                            tertiaryContainer = palette.tertiaryContainer,
                            surfaceContainerHigh = palette.surfaceContainerHigh,
                            scrim = palette.scrim,
                            isUnlocked = marketCatalogEntry.unlocked,
                            canUnlock = authenticated,
                            onBuyClick = {
                                onBuyItem(marketCatalogEntry)
                            }
                        )
                    }

                    is PaletteShopUiItem.Bundle -> {
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
                }
            }
        }
    }

}

@Composable
private fun LandscapeContent(
    modifier: Modifier,
    authenticated: Boolean = false,
    unlocks: MarketCatalogPalettesUiState,
    onBuyItem: (marketPalette: MarketPalette) -> Unit = { },
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
                    is PaletteShopUiItem.Header -> {
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

                    is PaletteShopUiItem.Palette -> {
                        val marketCatalogEntry = item.marketPalette
                        val palette = item.paletteResource

                        PaletteCard(
                            modifier = Modifier,
                            buyCredits = marketCatalogEntry.buyCredits,
                            badgeRes = palette.extrasFamily.badge,
                            title = stringResource(palette.extrasFamily.title),
                            onSurface = palette.onSurface,
                            onSurfaceVariant = palette.onSurfaceVariant,
                            primary = palette.primary,
                            secondary = palette.secondary,
                            tertiary = palette.tertiary,
                            surfaceContainer = palette.surfaceContainer,
                            primaryContainer = palette.primaryContainer,
                            secondaryContainer = palette.secondaryContainer,
                            tertiaryContainer = palette.tertiaryContainer,
                            surfaceContainerHigh = palette.surfaceContainerHigh,
                            scrim = palette.scrim,
                            isUnlocked = marketCatalogEntry.unlocked,
                            canUnlock = authenticated,
                            onBuyClick = {
                                onBuyItem(marketCatalogEntry)
                            }
                        )
                    }

                    is PaletteShopUiItem.Bundle -> {
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
                }
            }
        }
    }

}

@DevicePreviews
@Composable
@Preview
private fun PaletteShopPreview() {
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
        PaletteShopContent(
            modifier = Modifier
                .widthIn(max = 450.dp)
                .fillMaxSize(),
            unlocks = MarketCatalogPalettesUiState(
                items = listOf(
                    PaletteShopUiItem.Header("Bundle"),
                    PaletteShopUiItem.Bundle(
                        "",
                        MarketBundle("", ""),
                        listOf(marketPalette2),
                        false
                    ),
                    PaletteShopUiItem.Header("Specialist"),
                    PaletteShopUiItem.Palette(marketPalette1, palette1.toPaletteResource()),
                )
            )
        )
    }
}
