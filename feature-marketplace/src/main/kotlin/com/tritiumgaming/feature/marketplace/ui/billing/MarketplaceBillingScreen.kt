package com.tritiumgaming.feature.marketplace.ui.billing

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.marketplace.ui.MarketplaceViewModel
import com.tritiumgaming.feature.marketplace.ui.store.MarketCatalogPalettesUiState
import com.tritiumgaming.feature.marketplace.ui.store.MarketCatalogTypographiesUiState
import com.tritiumgaming.shared.core.navigation.NavRoute

@Composable
@Preview
private fun MarketplaceBillingScreenPreview() {
    MarketplaceBillingContent(
        navController = rememberNavController(),
        paletteUnlocks = MarketCatalogPalettesUiState(),
        typographyUnlocks = MarketCatalogTypographiesUiState(),
        earnedCredits = 1200
    )
}

@Composable
fun MarketplaceBillingScreen(
    navController: NavHostController = rememberNavController(),
    marketplaceViewModel: MarketplaceViewModel = viewModel(factory = MarketplaceViewModel.Factory)
) {
    val paletteUnlocks by marketplaceViewModel.marketCatalogPalettesUiState.collectAsStateWithLifecycle()
    val typographyUnlocks by marketplaceViewModel.marketCatalogTypographiesUiState.collectAsStateWithLifecycle()
    val accountCredits by marketplaceViewModel.accountCreditsUiState.collectAsStateWithLifecycle()

    MarketplaceBillingContent(
        navController = navController,
        paletteUnlocks = paletteUnlocks,
        typographyUnlocks = typographyUnlocks,
        earnedCredits = accountCredits.earnedCredits
    )
}

@Composable
private fun MarketplaceBillingContent(
    navController: NavHostController,
    paletteUnlocks: MarketCatalogPalettesUiState,
    typographyUnlocks: MarketCatalogTypographiesUiState,
    earnedCredits: Int
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0C121E)) // Overwatch-style deep charcoal
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // "SHOP" Title Header with Slant
            item(span = { GridItemSpan(2) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = "SHOP",
                        style = LocalTypography.current.primary.bold,
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier.graphicsLayer { rotationZ = -3f }
                    )
                }
            }

            // Featured Palette Section (Large Wide Card)
            item(span = { GridItemSpan(2) }) {
                OverwatchStoreCard(
                    title = stringResource(R.string.settings_colortheme_title),
                    subtitle = paletteUnlocks.palettes.firstOrNull()?.name ?: "FEATURED THEME",
                    tag = "NEW THEMES",
                    backgroundImage = R.drawable.ic_shop_cost, // Placeholder resource
                    isFeatured = true,
                    onClick = { navController.navigate(NavRoute.SCREEN_MARKETPLACE_UNLOCKS.route) }
                )
            }

            // Font Styles Section (Small Card)
            item {
                OverwatchStoreCard(
                    title = stringResource(R.string.settings_fontstylesettings),
                    subtitle = typographyUnlocks.typographies.firstOrNull()?.name ?: "CUSTOM FONTS",
                    tag = "TYPOGRAPHY",
                    backgroundImage = R.drawable.ic_font_family,
                    onClick = { navController.navigate(NavRoute.SCREEN_MARKETPLACE_TYPOGRAPHY.route) }
                )
            }

            // Purchase Credits Section (Small Card)
            item {
                OverwatchStoreCard(
                    title = "CURRENCY",
                    subtitle = "PURCHASE CREDITS",
                    tag = "STORE",
                    backgroundImage = R.drawable.ic_shop_cost,
                    onClick = { navController.navigate(NavRoute.SCREEN_MARKETPLACE_HOME.route) }
                )
            }

            // Additional Footer space
            item(span = { GridItemSpan(2) }) {
                Box(modifier = Modifier.height(64.dp))
            }
        }

        // Top Right Currency Display (Slanted Box)
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 24.dp, end = 16.dp)
                .clip(CutCornerShape(topStart = 12.dp, bottomEnd = 12.dp))
                .background(Color(0xCC111827)) // Semi-transparent dark
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_shop_cost),
                contentDescription = "Credits Icon",
                modifier = Modifier.size(20.dp),
                colorFilter = ColorFilter.tint(Color(0xFFFACC15)) // Gold color
            )
            Text(
                text = "$earnedCredits",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = LocalTypography.current.quaternary.bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
private fun OverwatchStoreCard(
    title: String,
    subtitle: String,
    tag: String,
    backgroundImage: Int,
    isFeatured: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()
    val isActive = isHovered || isPressed

    val scale by animateFloatAsState(
        targetValue = if (isActive) 1.05f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "scale"
    )
    
    val brightness by animateFloatAsState(
        targetValue = if (isActive) 0.8f else 0.4f,
        animationSpec = tween(durationMillis = 200),
        label = "brightness"
    )

    val borderColor by animateColorAsState(
        targetValue = if (isActive) Color.White.copy(alpha = 0.8f) else Color.Transparent,
        animationSpec = tween(durationMillis = 200),
        label = "borderColor"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (isFeatured) Modifier.height(220.dp) else Modifier.aspectRatio(0.85f))
            .zIndex(if (isActive) 1f else 0f),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .border(
                    width = 2.dp,
                    color = borderColor,
                    shape = CutCornerShape(topEnd = 16.dp)
                )
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick
                ),
            shape = CutCornerShape(topEnd = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1F2937))
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                // Background Image/Illustration
                Image(
                    painter = painterResource(backgroundImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer { alpha = brightness },
                    contentScale = ContentScale.Crop
                )

                // Hover Flash Overlay
                val overlayAlpha by animateFloatAsState(
                    targetValue = if (isActive) 0.1f else 0f,
                    animationSpec = tween(durationMillis = 200),
                    label = "overlayAlpha"
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White.copy(alpha = overlayAlpha))
                )

                // Heavy Bottom Gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0xCC000000)),
                                startY = 200f
                            )
                        )
                )

                // Content Overlay
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .clip(CutCornerShape(bottomEnd = 6.dp))
                            .background(Color(0xFF2563EB))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = tag.uppercase(),
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp
                        )
                    }

                    Column {
                        Text(
                            text = title.uppercase(),
                            color = Color.White,
                            fontSize = if (isFeatured) 28.sp else 18.sp,
                            fontWeight = FontWeight.Black,
                            style = LocalTypography.current.primary.bold,
                            lineHeight = if (isFeatured) 30.sp else 20.sp
                        )
                        Text(
                            text = subtitle.uppercase(),
                            color = Color(0xFF60A5FA),
                            fontSize = if (isFeatured) 14.sp else 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                        
                        if (isFeatured) {
                            val buttonColor by animateColorAsState(
                                targetValue = if (isActive) Color.White else Color(0xFFFACC15),
                                animationSpec = tween(durationMillis = 200),
                                label = "buttonColor"
                            )
                            Box(
                                modifier = Modifier
                                    .padding(top = 12.dp)
                                    .clip(CutCornerShape(topStart = 4.dp, bottomEnd = 4.dp))
                                    .background(buttonColor)
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "VIEW COLLECTION",
                                    color = Color.Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
