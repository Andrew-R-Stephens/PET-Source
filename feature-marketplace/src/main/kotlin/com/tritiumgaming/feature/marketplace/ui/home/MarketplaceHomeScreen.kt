package com.tritiumgaming.feature.marketplace.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel
import com.tritiumgaming.feature.marketplace.ui.common.MarketplaceScreen
import com.tritiumgaming.shared.core.navigation.NavRoute
import kotlin.math.tan

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
    marketplaceViewModel: MarketplaceViewModel,
) {
    MarketplaceScreen(
        modifier = modifier,
        navController = navController,
        marketplaceViewModel = marketplaceViewModel
    ) { contentModifier ->
        MarketplaceHomeContent(
            modifier = contentModifier
        ) { route ->
            navController.navigate(route)
        }
    }
}

@Composable
fun SlantedSplitBackground(
    modifier: Modifier = Modifier,
    vararg contents: @Composable () -> Unit
) {
    Layout(
        modifier = modifier.fillMaxSize(),
        content = {
            contents.forEachIndexed { index, content ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .then(
                            if (contents.size > 1 && index > 0) {
                                Modifier.clip(SlantedLeftShape(45f))
                            } else Modifier
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    ) { measurables, constraints ->
        val count = measurables.size
        val h = constraints.maxHeight
        val skew = (h * tan(Math.toRadians(45.0 / 2.0).toFloat())).toInt()

        val baseWidth = constraints.maxWidth / count

        val placeables = measurables.mapIndexed { index, measurable ->
            val itemWidth = if (count > 1 && index < count - 1) {
                baseWidth + skew
            } else {
                baseWidth
            }
            measurable.measure(constraints.copy(minWidth = itemWidth, maxWidth = itemWidth))
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var xPosition = 0
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(xPosition, 0)
                xPosition += baseWidth
            }
        }
    }
}

class SlantedLeftShape(private val angleDegrees: Float = 45f) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val skew = size.height * tan(Math.toRadians(angleDegrees.toDouble() / 2.0).toFloat())

            moveTo(skew, 0f)
            lineTo(size.width, 0f)
            lineTo(size.width, size.height)
            lineTo(0f, size.height)
            close()
        }
        return Outline.Generic(path)
    }
}

@Composable
fun MarketplaceHomeContent(
    modifier: Modifier,
    onNavigate: (String) -> Unit = {},
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            MarketplaceHomeContentLandscape(modifier, onNavigate)
        }
        DeviceConfiguration.TABLET_LANDSCAPE, DeviceConfiguration.DESKTOP -> {
            MarketplaceHomeContentExpanded(modifier, onNavigate)
        }
        else -> {
            MarketplaceHomeContentPortrait(modifier, onNavigate)
        }
    }
}

@Composable
private fun MarketplaceHomeContentPortrait(
    modifier: Modifier,
    onNavigate: (String) -> Unit = {},
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item(span = { GridItemSpan(2) }) { PaletteCard(isLarge = true, onNavigate = onNavigate) }
        item(span = { GridItemSpan(2) }) { BundleCard(isLarge = true, onNavigate = onNavigate) }
        item { TypographyCard(onNavigate = onNavigate) }
        item { BillingCard(onNavigate = onNavigate) }
    }
}

@Composable
private fun MarketplaceHomeContentLandscape(
    modifier: Modifier,
    onNavigate: (String) -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PaletteCard(
                modifier = Modifier.weight(1f),
                isLarge = true,
                useDefaultConstraints = false,
                onNavigate = onNavigate
            )
            TypographyCard(
                modifier = Modifier.weight(1f),
                useDefaultConstraints = false,
                onNavigate = onNavigate
            )
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BundleCard(
                modifier = Modifier.weight(1f),
                isLarge = true,
                useDefaultConstraints = false,
                onNavigate = onNavigate
            )
            BillingCard(
                modifier = Modifier.weight(1f),
                useDefaultConstraints = false,
                onNavigate = onNavigate
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun MarketplaceHomeContentExpanded(
    modifier: Modifier,
    onNavigate: (String) -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            PaletteCard(modifier = Modifier.widthIn(max = 400.dp), isLarge = true, onNavigate = onNavigate)
            BundleCard(modifier = Modifier.widthIn(max = 400.dp), isLarge = true, onNavigate = onNavigate)
            TypographyCard(modifier = Modifier.widthIn(max = 200.dp), onNavigate = onNavigate)
            BillingCard(modifier = Modifier.widthIn(max = 200.dp), onNavigate = onNavigate)
        }
    }
}

@Composable
private fun PaletteCard(
    modifier: Modifier = Modifier,
    isLarge: Boolean = false,
    useDefaultConstraints: Boolean = true,
    onNavigate: (String) -> Unit
) {
    StorefrontCard(
        modifier = modifier,
        title = stringResource(R.string.marketplace_home_storefront_palettes_title),
        description = stringResource(R.string.marketplace_home_storefront_palettes_description),
        background = { cardModifier ->
            SlantedSplitBackground(
                modifier = cardModifier
                    .alpha(.35f),
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
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
                                        RoundedCornerShape(8.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.primary,
                                        RoundedCornerShape(8.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.secondary,
                                        RoundedCornerShape(8.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.tertiary,
                                        RoundedCornerShape(8.dp))
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
                                        RoundedCornerShape(8.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.secondaryContainer,
                                        RoundedCornerShape(8.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.tertiaryContainer,
                                        RoundedCornerShape(8.dp))
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(4.dp)
                                    .background(
                                        LocalPalette.current.surfaceContainer,
                                        RoundedCornerShape(8.dp))
                            )
                        }

                    }
                }
            )
        },
        containerColor = LocalPalette.current.surfaceContainerHigh,
        isLarge = isLarge,
        useDefaultConstraints = useDefaultConstraints,
        onClick = { onNavigate(NavRoute.SCREEN_MARKETPLACE_PALETTE.route) }
    )
}

@Composable
private fun BundleCard(
    modifier: Modifier = Modifier,
    isLarge: Boolean = false,
    useDefaultConstraints: Boolean = true,
    onNavigate: (String) -> Unit
) {
    StorefrontCard(
        modifier = modifier,
        title = stringResource(R.string.marketplace_home_storefront_bundles_title),
        description = stringResource(R.string.marketplace_home_storefront_bundles_description),
        background = { cardModifier ->
            SlantedSplitBackground(
                modifier = cardModifier
                    .alpha(.35f),
                {
                    Image(
                        painter = painterResource(id = R.drawable.theme_badge_1_recruit),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2.5f),
                        contentScale = ContentScale.Crop
                    )
                },
                {
                    Image(
                        painter = painterResource(id = R.drawable.theme_badge_4_detective),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2.5f),
                        contentScale = ContentScale.Crop
                    )
                },
                {
                    Image(
                        painter = painterResource(id = R.drawable.theme_badge_halloween23),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2.5f),
                        contentScale = ContentScale.Crop
                    )
                },
                {
                    Image(
                        painter = painterResource(id = R.drawable.theme_badge_5_technician),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2.5f),
                        contentScale = ContentScale.Crop
                    )
                },
                {
                    Image(
                        painter = painterResource(id = R.drawable.theme_badge_easter),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .scale(2.5f),
                        contentScale = ContentScale.Crop
                    )
                },
            )
        },
        containerColor = LocalPalette.current.surfaceContainerHigh,
        isLarge = isLarge,
        useDefaultConstraints = useDefaultConstraints,
        onClick = { onNavigate(NavRoute.SCREEN_MARKETPLACE_BUNDLES.route) }
    )
}

@Composable
private fun TypographyCard(
    modifier: Modifier = Modifier,
    useDefaultConstraints: Boolean = true,
    onNavigate: (String) -> Unit
) {
    StorefrontCard(
        modifier = modifier,
        title = stringResource(R.string.marketplace_home_storefront_typography_title),
        description = stringResource(R.string.marketplace_home_storefront_typography_description),
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
        contentColor = LocalPalette.current.onSurface,
        useDefaultConstraints = useDefaultConstraints,
        onClick = { onNavigate(NavRoute.SCREEN_MARKETPLACE_TYPOGRAPHY.route) }
    )
}

@Composable
private fun BillingCard(
    modifier: Modifier = Modifier,
    useDefaultConstraints: Boolean = true,
    onNavigate: (String) -> Unit
) {
    StorefrontCard(
        modifier = modifier,
        title = stringResource(R.string.marketplace_home_storefront_billing_title),
        description = stringResource(R.string.marketplace_home_storefront_billing_description),
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
        contentColor = LocalPalette.current.onSurface,
        useDefaultConstraints = useDefaultConstraints,
        onClick = { onNavigate(NavRoute.SCREEN_MARKETPLACE_BILLABLE.route) }
    )
}

@Composable
fun StorefrontCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    background: @Composable (Modifier) -> Unit,
    containerColor: Color,
    contentColor: Color = Color.Transparent,
    isLarge: Boolean = false,
    useDefaultConstraints: Boolean = true,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (useDefaultConstraints) {
                    if (isLarge) Modifier.height(160.dp) else Modifier.aspectRatio(1f)
                } else Modifier
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        color = containerColor,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            background(Modifier.alpha(1f))

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
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.BottomStart),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Surface(
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            text = title.uppercase(),
                            style = LocalTypography.current.primary.bold.copy(
                                fontSize = if (isLarge) 24.sp else 16.sp,
                                fontWeight = FontWeight.Black
                            ),
                            color = LocalPalette.current.onSurface
                        )
                    }

                    Surface(
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            text = description,
                            style = LocalTypography.current.quaternary.regular.copy(
                                fontSize = if (isLarge) 14.sp else 12.sp
                            ),
                            color = LocalPalette.current.onSurface
                        )
                    }
                }
            }
        }
    }
}

@DevicePreviews
@Composable
private fun Preview() {
    LocalThemeProvider {
        MarketplaceHomeContent(
            modifier = Modifier.background(LocalPalette.current.surface)
        )
    }
}
