package com.tritiumgaming.feature.marketplace.ui.home

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.widgets.image.SlantedSplitBackground
import com.tritiumgaming.feature.marketplace.ui.common.MarketplaceScreen
import com.tritiumgaming.shared.core.navigation.NavRoute

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
fun MarketplaceHomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewmodel: MarketplaceHomeScreenViewModel,
) {
    val context = LocalContext.current
    val activity = LocalActivity.current

    val user = if(!LocalInspectionMode.current) Firebase.auth.currentUser else null

    val showAgreementDialog by viewmodel.showAgreementDialog.collectAsStateWithLifecycle()

    val accountCredits by viewmodel.accountCreditsUiState.collectAsStateWithLifecycle()
    val rewardedAdState by viewmodel.rewardedAdUiState.collectAsStateWithLifecycle()

    val rewardCredits = rewardedAdState.reward?.amount ?: 0

    val onClickRewardedAd: () -> Unit = {
        activity?.let {
            viewmodel.onAttemptRewardedAd(
                activity,
                onSuccess = { quantity, type ->
                    viewmodel.addCredits(
                        credits = quantity,
                        onSuccess = {
                            Toast.makeText(
                                context, "Credits Earned",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onFailure = {
                            Toast.makeText(
                                context, "Error! $it",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                },
                onFailure = {
                    Toast.makeText(
                        context, "Error! $it",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    MarketplaceScreen(
        modifier = modifier,
        navController = navController,
        earnedCredits = accountCredits.earnedCredits,
        showRewardButton = user != null,
        showAgreementDialog = showAgreementDialog,
        onConfirmAgreement = {
            viewmodel.setMarketplaceAgreementAccepted()
        },
        onClickRewardButton = onClickRewardedAd,
        accountContent = {

        },
        storeContent = { contentModifier ->
            MarketplaceHomeContent(
                modifier = contentModifier,
                rewardCredits = rewardCredits,
                onClickRewardedAd = onClickRewardedAd
            ) { route ->
                viewmodel.onAttemptNavigate(route) { targetRoute ->
                    navController.navigate(targetRoute) {
                        launchSingleTop = true
                    }
                }
            }
        }
    )
}

@Composable
fun MarketplaceHomeContent(
    modifier: Modifier,
    rewardCredits: Int,
    onClickRewardedAd: () -> Unit = {},
    onNavigate: (String) -> Unit = {},
) {
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when (deviceConfiguration) {
        DeviceConfiguration.TABLET_LANDSCAPE, DeviceConfiguration.MOBILE_LANDSCAPE -> {
            MarketplaceHomeContentLandscape(modifier, rewardCredits, onNavigate, onClickRewardedAd)
        }
        DeviceConfiguration.TABLET_PORTRAIT, DeviceConfiguration.DESKTOP -> {
            MarketplaceHomeContentExpanded(modifier, rewardCredits, onNavigate, onClickRewardedAd)
        }
        else -> {
            MarketplaceHomeContentPortrait(modifier, rewardCredits, onNavigate, onClickRewardedAd)
        }
    }
}

@Composable
private fun MarketplaceHomeContentPortrait(
    modifier: Modifier,
    rewardCredits: Int,
    onNavigate: (String) -> Unit = {},
    onClickRewardedAd: () -> Unit = {},
) {
    val user = if(!LocalInspectionMode.current) Firebase.auth.currentUser else null

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
    ) {
        if(user != null) {
            item(
                key = 0,
                span = { GridItemSpan(2) }
            ) {
                RewardedAdsCard(
                    rewardCredits = rewardCredits,
                    isLarge = true,
                    onNavigate = onNavigate,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = onClickRewardedAd
                )
            }
        }
        /*item(
            key = 1,
            span = { GridItemSpan(1) }
        ) {
            BillingCard(
                isLarge = false,
                onNavigate = onNavigate,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }*/
        item(
            key = 2,
            span = { GridItemSpan(2) }
        ) {
            BundleCard(
                isLarge = true,
                onNavigate = onNavigate,
                modifier = Modifier
                    .fillMaxWidth()
            ) }
        item(
            key = 3,
            span = { GridItemSpan(2) }
        ) {
            PaletteCard(
                isLarge = true,
                onNavigate = onNavigate,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        //item { TypographyCard(onNavigate = onNavigate) }
    }
}

@Composable
private fun MarketplaceHomeContentLandscape(
    modifier: Modifier,
    rewardCredits: Int,
    onNavigate: (String) -> Unit = {},
    onClickRewardedAd: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        /*Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(.7f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            *//*BillingCard(
                isLarge = true,
                onNavigate = onNavigate
            )*//*
        }*/
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val user = if(!LocalInspectionMode.current) Firebase.auth.currentUser else null

            if(user != null) {
                RewardedAdsCard(
                    rewardCredits = rewardCredits,
                    isLarge = true,
                    onNavigate = onNavigate,
                    onClick = onClickRewardedAd
                )
            }

            BundleCard(
                isLarge = true,
                onNavigate = onNavigate
            )

            PaletteCard(
                isLarge = true,
                onNavigate = onNavigate
            )
            /*TypographyCard(
                modifier = Modifier.weight(1f),
                useDefaultConstraints = false,
                onNavigate = onNavigate
            )*/
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MarketplaceHomeContentExpanded(
    modifier: Modifier,
    rewardCredits: Int,
    onNavigate: (String) -> Unit = {},
    onClickRewardedAd: () -> Unit = {},
) {
    val user = if(!LocalInspectionMode.current) Firebase.auth.currentUser else null

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if(user != null) {
            item(
                key = 0,
                span = { GridItemSpan(2) }
            ) {
                RewardedAdsCard(
                    rewardCredits = rewardCredits,
                    isLarge = true,
                    onNavigate = onNavigate,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = onClickRewardedAd
                )
            }
        }
        /*item(
            key = 1,
            span = { GridItemSpan(1) }
        ) {
            BillingCard(
                isLarge = false,
                onNavigate = onNavigate,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }*/
        item(
            key = 2,
            span = { GridItemSpan(2) }
        ) {
            BundleCard(
                isLarge = true,
                onNavigate = onNavigate,
                modifier = Modifier
                    .fillMaxWidth()
            ) }
        item(
            key = 3,
            span = { GridItemSpan(2) }
        ) {
            PaletteCard(
                isLarge = true,
                onNavigate = onNavigate,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        //item { TypographyCard(onNavigate = onNavigate) }
    }
    /*Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
        ) {
            RewardedAdsCard(modifier = Modifier.sizeIn(maxHeight = 200.dp, maxWidth = 400.dp), isLarge = true, onNavigate = onNavigate)
            BundleCard(modifier = Modifier.sizeIn(maxHeight = 200.dp, maxWidth = 400.dp), isLarge = true, onNavigate = onNavigate)
            PaletteCard(modifier = Modifier.sizeIn(maxHeight = 200.dp, maxWidth = 400.dp), isLarge = true, onNavigate = onNavigate)
            //TypographyCard(modifier = Modifier.sizeIn(maxHeight = 200.dp, maxWidth = 200.dp), onNavigate = onNavigate)
            //BillingCard(modifier = Modifier.sizeIn(maxHeight = 200.dp, maxWidth = 200.dp), onNavigate = onNavigate)
        }
    }*/
}

@Composable
private fun PaletteCard(
    modifier: Modifier = Modifier,
    isLarge: Boolean = false,
    onNavigate: (String) -> Unit
) {
    StorefrontCard(
        modifier = modifier,
        title = stringResource(R.string.marketplace_home_storefront_palettes_title),
        description = stringResource(R.string.marketplace_home_storefront_palettes_description),
        icon = { modifier ->
            Surface(
                modifier = modifier
                    .aspectRatio(1f),
                color = LocalPalette.current.surfaceContainerHigh,
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp,
                tonalElevation = 8.dp
            ) {
                Icon(
                    modifier = Modifier
                        .padding(4.dp),
                    painter = painterResource(R.drawable.ic_palette_fill),
                    tint = LocalPalette.current.onSurface,
                    contentDescription = null
                )
            }
        },
        background = { cardModifier ->
            SlantedSplitBackground(
                modifier = cardModifier
                    .alpha(.5f),
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(1.1f)
                            .heightIn(min = 120.dp)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.onSurface,
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.primary,
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.secondary,
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.tertiary,
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                        }

                        Row(
                            modifier = Modifier
                                .weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.primaryContainer,
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.secondaryContainer,
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.tertiaryContainer,
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.surfaceContainer,
                                        RoundedCornerShape(8.dp)
                                    )
                            )
                        }

                    }
                }
            )
        },
        containerColor = LocalPalette.current.surfaceContainerHigh,
        contentColor = LocalPalette.current.onSurface.copy(alpha = .2f),
        isLarge = isLarge,
        onClick = { onNavigate(NavRoute.SCREEN_MARKETPLACE_PALETTE.route) }
    )
}

@Composable
private fun BundleCard(
    modifier: Modifier = Modifier,
    isLarge: Boolean = false,
    onNavigate: (String) -> Unit
) {
    StorefrontCard(
        modifier = modifier,
        title = stringResource(R.string.marketplace_home_storefront_bundles_title),
        description = stringResource(R.string.marketplace_home_storefront_bundles_description),
        icon = { modifier ->
            Surface(
                modifier = modifier,
                color = LocalPalette.current.surfaceContainerHigh,
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp,
                tonalElevation = 8.dp
            ) {
                StackedIcons(
                    modifier = Modifier
                        .padding(4.dp),
                    icon = R.drawable.ic_palette_fill,
                    color = LocalPalette.current.onSurface
                )
            }
        },
        background = { cardModifier ->
            SlantedSplitBackground(
                modifier = cardModifier
                    .alpha(.5f),
                {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2.5f),
                        painter = painterResource(id = R.drawable.theme_badge_1_recruit),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                },
                {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2.5f),
                        painter = painterResource(id = R.drawable.theme_badge_4_detective),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                },
                {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2.5f),
                        painter = painterResource(id = R.drawable.theme_badge_halloween23),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                },
                {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2.5f),
                        painter = painterResource(id = R.drawable.theme_badge_5_technician),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                },
                {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2.5f),
                        painter = painterResource(id = R.drawable.theme_badge_easter),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                },
            )
        },
        containerColor = LocalPalette.current.surfaceContainerHigh,
        contentColor = LocalPalette.current.onSurface.copy(alpha = .2f),
        isLarge = isLarge,
        onClick = { onNavigate(NavRoute.SCREEN_MARKETPLACE_BUNDLES.route) }
    )
}

@Composable
private fun TypographyCard(
    modifier: Modifier = Modifier,
    isLarge: Boolean = false,
    onNavigate: (String) -> Unit
) {
    StorefrontCard(
        modifier = modifier,
        title = stringResource(R.string.marketplace_home_storefront_typography_title),
        description = stringResource(R.string.marketplace_home_storefront_typography_description),
        icon = { modifier ->
            Surface(
                modifier = modifier
                    .aspectRatio(1f),
                color = LocalPalette.current.surfaceContainerHigh,
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp,
                tonalElevation = 8.dp
            ) {
                Icon(
                    modifier = Modifier
                        .padding(4.dp),
                    painter = painterResource(R.drawable.ic_font_family),
                    tint = LocalPalette.current.onSurface,
                    contentDescription = null
                )
            }
        },
        background = { cardModifier ->
            SlantedSplitBackground(
                modifier = cardModifier,
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_font_family),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(1.5f),
                        contentScale = ContentScale.Crop,
                        alpha = 0.1f
                    )
                }
            )
        },
        containerColor = LocalPalette.current.surfaceContainerHigh,
        contentColor = LocalPalette.current.onSurface.copy(alpha = .2f),
        isLarge = isLarge,
        onClick = { onNavigate(NavRoute.SCREEN_MARKETPLACE_TYPOGRAPHY.route) }
    )
}

@Composable
private fun BillingCard(
    modifier: Modifier = Modifier,
    isLarge: Boolean = false,
    onNavigate: (String) -> Unit
) {
    StorefrontCard(
        modifier = modifier,
        title = stringResource(R.string.marketplace_home_storefront_billing_title),
        description = stringResource(R.string.marketplace_home_storefront_billing_description),
        icon = { modifier ->
            Surface(
                modifier = modifier
                    .aspectRatio(1f),
                color = LocalPalette.current.surfaceContainerHigh,
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp,
                tonalElevation = 8.dp
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.ic_payment),
                    tint = LocalPalette.current.onSurface,
                    contentDescription = null
                )
            }
        },
        background = { cardModifier ->
            SlantedSplitBackground(
                modifier = cardModifier,
                {
                    Image(
                        painter = painterResource(id = R.drawable.ic_shop_cost),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(1.5f),
                        contentScale = ContentScale.Crop,
                        alpha = 0.1f
                    )
                }
            )
        },
        containerColor = LocalPalette.current.surfaceContainerHigh,
        contentColor = LocalPalette.current.onSurface.copy(alpha = .2f),
        isLarge = isLarge,
        onClick = { onNavigate(NavRoute.SCREEN_MARKETPLACE_BILLABLE.route) }
    )
}

@Composable
private fun RewardedAdsCard(
    modifier: Modifier = Modifier,
    rewardCredits: Int,
    isLarge: Boolean = false,
    onNavigate: (String) -> Unit,
    onClick: () -> Unit
) {
    val user = if(!LocalInspectionMode.current) Firebase.auth.currentUser else null

    val descriptionBase = stringResource(R.string.marketplace_home_storefront_rewarded_ad_description)
    val creditsDisclosure = pluralStringResource(
        R.plurals.marketplace_description_watch_ad,
        rewardCredits
    )
    val description = String.format(descriptionBase, rewardCredits, creditsDisclosure)

    StorefrontCard(
        modifier = modifier,
        title = stringResource(R.string.marketplace_home_storefront_rewarded_ad_title),
        description = description,
        background = { cardModifier ->
            SlantedSplitBackground(
                modifier = cardModifier,
                {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(1.5f),
                        painter = painterResource(id = R.drawable.ic_shop_cost),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        alpha = 0.1f
                    )
                }
            )

            if(user == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            LocalPalette.current.scrim.copy(
                                alpha = .5f
                            )
                        )
                ) {
                    Icon(
                        modifier = Modifier
                            .sizeIn(maxHeight = 96.dp, maxWidth = 96.dp)
                            .aspectRatio(1f)
                            .align(Alignment.Center)
                            .padding(8.dp),
                        painter = painterResource(R.drawable.ic_shop_lock),
                        contentDescription = "",
                        tint = LocalPalette.current.primary,
                    )
                }
            }

        },
        icon = { modifier ->
            Surface(
                modifier = modifier,
                color = LocalPalette.current.surfaceContainerHigh,
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp,
                tonalElevation = 8.dp
            ) {
                Icon(
                    modifier = Modifier,
                    painter = painterResource(R.drawable.ic_rewarded_ads),
                    tint = LocalPalette.current.onSurface,
                    contentDescription = null
                )
            }
        },
        containerColor = LocalPalette.current.surfaceContainerHigh,
        contentColor = LocalPalette.current.onSurface.copy(alpha = .2f),
        isLarge = isLarge,
        onClick = onClick
    )
}

@Composable
fun StorefrontCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    icon: @Composable (Modifier) -> Unit = {},
    background: @Composable (Modifier) -> Unit,
    containerColor: Color,
    contentColor: Color = Color.Transparent,
    isLarge: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .height(IntrinsicSize.Min),
        onClick = onClick,
        color = containerColor,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp,
        border = BorderStroke(2.dp, contentColor),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.4f)
                        )
                    )
                )
        ) {
            background(Modifier.alpha(1f))

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                val topWidth = remember { mutableIntStateOf(0) }
                val bottomWidth = remember { mutableIntStateOf(0) }

                Box(
                    modifier = Modifier
                        .zIndex(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ) {
                    icon(
                        Modifier
                            .fillMaxHeight(.25f)
                            .sizeIn(minHeight = 48.dp, maxHeight = 96.dp)
                    )
                }

                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(0.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Surface(
                        modifier = Modifier
                            .onGloballyPositioned {
                                topWidth.intValue = it.size.width
                            }
                            .zIndex(1f),
                        color = LocalPalette.current.surfaceContainerLowest,
                        shape = RoundedCornerShape(
                            topStart = 8.dp, topEnd = 8.dp,
                            bottomEnd = if (topWidth.intValue >= bottomWidth.intValue) 8.dp else 0.dp
                        ),
                        shadowElevation = 4.dp,
                        tonalElevation = 8.dp
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 12.dp
                            ),
                            text = title.uppercase(),
                            style = LocalTypography.current.primary.bold.copy(
                                fontSize = if (isLarge) 24.sp else 16.sp,
                                fontWeight = FontWeight.Black
                            ),
                            color = LocalPalette.current.onSurfaceVariant
                        )
                    }

                    Surface(
                        modifier = Modifier
                            .onGloballyPositioned {
                                bottomWidth.intValue = it.size.width
                            }
                            .zIndex(1f),
                        color = LocalPalette.current.surfaceContainerLow,
                        shape = RoundedCornerShape(
                            bottomStart = 8.dp, bottomEnd = 8.dp,
                            topEnd = if (bottomWidth.intValue >= topWidth.intValue) 8.dp else 0.dp
                        ),
                        shadowElevation = 4.dp,
                        tonalElevation = 8.dp
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                horizontal = 12.dp,
                                vertical = 8.dp
                            ),
                            text = description.uppercase(),
                            style = LocalTypography.current.quaternary.bold.copy(
                                fontSize = if (isLarge) 16.sp else 12.sp
                            ),
                            color = LocalPalette.current.onSurface
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun StackedIcons(
    modifier: Modifier = Modifier,
    color: Color = LocalPalette.current.primary,
    icon: Int
) {
    val painter: Painter = painterResource(id = icon)
    val standardHeight = 48.dp

    val measurePolicy = remember(painter, color) {
        object : MeasurePolicy {
            override fun MeasureScope.measure(
                measurables: List<Measurable>,
                constraints: Constraints
            ): MeasureResult {
                val heightPx = if (constraints.hasBoundedHeight) {
                    constraints.maxHeight
                } else {
                    standardHeight.roundToPx()
                }

                val scale = heightPx.toFloat() / standardHeight.toPx()
                val widthPx = (heightPx + (8.dp.toPx() * scale) * 2).toInt()

                return layout(widthPx, heightPx) {}
            }

            override fun IntrinsicMeasureScope.minIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int = standardHeight.roundToPx()

            override fun IntrinsicMeasureScope.maxIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int = standardHeight.roundToPx()

            override fun IntrinsicMeasureScope.minIntrinsicWidth(
                measurables: List<IntrinsicMeasurable>,
                height: Int
            ): Int {
                val h = if (height != Constraints.Infinity && height > 0) height else standardHeight.roundToPx()
                val scale = h.toFloat() / standardHeight.toPx()
                return (h + (8.dp.toPx() * scale) * 2).toInt()
            }

            override fun IntrinsicMeasureScope.maxIntrinsicWidth(
                measurables: List<IntrinsicMeasurable>,
                height: Int
            ): Int = minIntrinsicWidth(measurables, height)
        }
    }

    Layout(
        content = {},
        modifier = modifier.drawBehind {
            val iconSizePx = size.height
            val scale = iconSizePx / standardHeight.toPx()
            val stepPx = 8.dp.toPx() * scale

            // Step 0: Left Sliver (I0 at x=0, masked by I1 at x=stepPx)
            drawIntoCanvas { canvas ->
                canvas.saveLayer(Rect(0f, 0f, size.width, size.height), Paint())

                // Mask (I1 at x=stepPx)
                canvas.translate(stepPx, 0f)
                with(painter) {
                    draw(size = Size(iconSizePx, iconSizePx), colorFilter = ColorFilter.tint(Color.Black))
                }
                canvas.translate(-stepPx, 0f)

                // Source (I0 at x=0) with SrcOut
                val paint = Paint().apply {
                    this.blendMode = BlendMode.SrcOut
                    this.colorFilter = ColorFilter.tint(color)
                }
                canvas.saveLayer(Rect(0f, 0f, size.width, size.height), paint)
                with(painter) {
                    draw(size = Size(iconSizePx, iconSizePx))
                }
                canvas.restore()
                canvas.restore()
            }

            // Step 4: Full Vector (I1 at x=stepPx * 2)
            translate(left = stepPx * 2) {
                with(painter) {
                    draw(size = Size(iconSizePx, iconSizePx), colorFilter = ColorFilter.tint(color))
                }
            }
        },
        measurePolicy = measurePolicy
    )
}

@Preview
@Composable
private fun StackedPaletteIconsPreview() {
    LocalThemeProvider {
        Box(
            modifier = Modifier
                .background(LocalPalette.current.surface),
            contentAlignment = Alignment.Center
        ) {
            StackedIcons(
                modifier = Modifier
                    .height(96.dp),
                icon = R.drawable.ic_palette_fill,
                color = LocalPalette.current.primary
            )
        }
    }
}

@DevicePreviews
@Composable
private fun Preview() {
    LocalThemeProvider {
        MarketplaceHomeContent(
            modifier = Modifier.background(LocalPalette.current.surface),
            rewardCredits = 10
        )
    }
}

//@DevicePreviews
@Preview
@Composable
private fun BillingCardPreview() {
    LocalThemeProvider {
        Box(
            modifier = Modifier
                .background(LocalPalette.current.surface)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            BillingCard(
                onNavigate = {}
            )
        }
    }
}

//@DevicePreviews
@Preview
@Composable
private fun RewardedAdsCardPreview() {
    LocalThemeProvider {
        Column(
            modifier = Modifier
                .background(LocalPalette.current.surface)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RewardedAdsCard (
                rewardCredits = 10,
                onNavigate = {},
                onClick = {}
            )
        }
    }
}
