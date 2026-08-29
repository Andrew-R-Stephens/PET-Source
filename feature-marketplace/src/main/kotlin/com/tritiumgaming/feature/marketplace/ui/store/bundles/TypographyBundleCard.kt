package com.tritiumgaming.feature.marketplace.ui.store.bundles

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.composite.MarkCheckCircleIconComposite
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.mapper.toTypographyResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.theme.type.JetBrainsMonoTypography
import com.tritiumgaming.core.ui.theme.type.common.CustomFontFamily
import com.tritiumgaming.core.ui.theme.white_M100
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType.*
import com.tritiumgaming.shared.data.market.palette.mappers.asUuid
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.CLASSIC
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.JETBRAINS_MONO
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType.NEUCHA
import com.tritiumgaming.shared.data.market.typography.model.MarketTypography

@Composable
fun TypographyBundleCard(
    modifier: Modifier = Modifier,
    uuid: String,
    buyCredits: Long = 0L,
    title: String,
    surfaceContainerHigh: Color,
    onSurfaceVariant: Color,
    onSurface: Color,
    scrim: Color,
    items: List<MarketTypography>,
    canUnlock: Boolean = false,
    isOwned: Boolean = false,
    onBuyClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = white_M100
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            propagateMinConstraints = true
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .animateContentSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .background(surfaceContainerHigh)
                        .padding(4.dp),
                    text = title,
                    color = onSurfaceVariant,
                    style = LocalTypography.current.primary.bold.copy(
                        textAlign = TextAlign.Center
                    ),
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = 1.sp,
                        maxFontSize = 50.sp,
                        stepSize = 2.sp
                    )
                )

                var selectedPalette: PaletteType? by remember { mutableStateOf(null) }

                /*LazyRow(
                    modifier = Modifier
                        .height(96.dp)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(items = items, key = { it.uuid }) { marketTypography ->
                        marketTypography.typography?.let { type ->
                            val paletteRes = type.toTypographyResource()

                            BundleIncludedTypographyPreview(
                                modifier = Modifier.width(48.dp),
                                isSelected = selectedPalette?.asUuid() == marketTypography.uuid,
                                title = stringResource(paletteRes.extrasFamily.title),
                                isOwned = marketTypography.unlocked,
                                surfaceColor = paletteRes.surface,
                                onSurfaceColor = LocalPalette.current.onSurface,
                                onClick = {
                                    selectedPalette =
                                        if (selectedPalette?.asUuid() == marketTypography.uuid) {
                                            null
                                        } else type
                                }
                            )
                        }
                    }
                }*/

                selectedPalette?.toPaletteResource()?.let { palette ->
                    TypographyDetailsCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp),
                        badgeRes = palette.extrasFamily.badge,
                        title = stringResource(palette.extrasFamily.title),
                        surfaceContainerHigh = palette.surfaceContainerHigh,
                        scrim = palette.scrim,
                        onSurfaceVariant = palette.onSurfaceVariant,
                        onSurface = palette.onSurface,
                        primary = palette.primary,
                        secondary = palette.secondary,
                        tertiary = palette.tertiary,
                        surfaceContainer = palette.surfaceContainer,
                        primaryContainer = palette.primaryContainer,
                        secondaryContainer = palette.secondaryContainer,
                        tertiaryContainer = palette.tertiaryContainer,
                    )
                }

                if(!isOwned) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 4.dp)
                            .background(scrim.copy(alpha = .3f))
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(
                            8.dp,
                            Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Button(
                            modifier = Modifier
                                .height(48.dp)
                                .padding(8.dp)
                                .weight(1f, false),
                            onClick = onBuyClick,
                            enabled = canUnlock,
                            shape = RoundedCornerShape(2.dp),
                            contentPadding = PaddingValues(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = surfaceContainerHigh
                            ),
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                text = stringResource(R.string.marketplace_button_item_get).uppercase(),
                                style = LocalTypography.current.quaternary.bold.copy(
                                    textAlign = TextAlign.Center
                                ),
                                color = onSurface,
                                autoSize = TextAutoSize.StepBased(1.sp, 18.sp, 1.sp)
                            )
                        }

                        Image(
                            modifier = Modifier
                                .height(48.dp)
                                .aspectRatio(1f)
                                .padding(8.dp),
                            painter = painterResource(R.drawable.ic_shop_cost),
                            contentDescription = "Cost",
                            colorFilter = ColorFilter.tint(surfaceContainerHigh)
                        )

                        Text(
                            modifier = Modifier
                                .weight(1f, false),
                            text = "$buyCredits",
                            fontSize = 24.sp,
                            color = surfaceContainerHigh,
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Start
                            )
                        )
                    }

                }
            }

        }

    }

}

@Composable
private fun BundleIncludedTypography(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    isOwned: Boolean = false,
    surfaceColor: Color,
    onSurfaceColor: Color,
    onClick: () -> Unit,
    icon: @Composable (Modifier) -> Unit = {}
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) surfaceColor else Color.Transparent
        )
    ) {
        Column(
            modifier = modifier
                .padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    /*.background(
                        color = if (isSelected) onSurfaceColor.copy(alpha = 0.2f) else Color.Transparent,
                        shape = RoundedCornerShape(8.dp)
                    )*/
                    .clickable(onClick = onClick),
                contentAlignment = Alignment.BottomEnd
            ) {
                icon(
                    Modifier.fillMaxSize()
                )

                if (isOwned) {
                    MarkCheckCircleIconComposite(
                        modifier = Modifier
                            .fillMaxSize(.45f),
                        filled = false,
                        color = surfaceColor,
                        onColor = onSurfaceColor
                    )
                }
            }

            Text(
                modifier = Modifier
                    .basicMarquee(iterations = Int.MAX_VALUE),
                text = title,
                color = Color.Black,
                style = LocalTypography.current.quaternary.bold,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun TypographyDetailsCard(
    modifier: Modifier = Modifier,
    badgeRes: Int,
    title: String,
    surfaceContainerHigh: Color,
    scrim: Color,
    onSurfaceVariant: Color,
    onSurface: Color,
    primary: Color,
    secondary: Color,
    tertiary: Color,
    surfaceContainer: Color,
    primaryContainer: Color,
    secondaryContainer: Color,
    tertiaryContainer: Color
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = white_M100
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            propagateMinConstraints = true
        ) {

            Row(
                modifier = Modifier
                    .matchParentSize(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(.75f)
                        .aspectRatio(1f, false)
                        .scale(3f)
                        .graphicsLayer {
                            translationX = size.width * .1f
                        }
                        .alpha(.1f),
                    painter = painterResource(badgeRes),
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.CenterEnd
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .background(surfaceContainerHigh)
                        .padding(4.dp),
                    text = title,
                    color = onSurfaceVariant,
                    style = LocalTypography.current.primary.bold.copy(
                        textAlign = TextAlign.Center
                    ),
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = 1.sp,
                        maxFontSize = 50.sp,
                        stepSize = 2.sp
                    )
                )

                Row(
                    modifier = Modifier
                        .height(96.dp)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f),
                        painter = painterResource(badgeRes),
                        contentDescription = "Badge"
                    )

                    val typographyType = LocalTypography.current

                    var rememberFontFamily by remember { mutableIntStateOf(0) }
                    var rememberTextStyle by remember { mutableIntStateOf(0) }

                    val fontFamily: CustomFontFamily =
                        when (rememberFontFamily) {
                            0 -> typographyType.primary
                            1 -> typographyType.secondary
                            2 -> typographyType.tertiary
                            else -> typographyType.quaternary
                        }

                    val textStyle: TextStyle =
                        when (rememberTextStyle) {
                            0 -> fontFamily.regular
                            1 -> fontFamily.bold
                            2 -> fontFamily.narrow
                            else -> fontFamily.boldNarrow
                        }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = { rememberFontFamily = (rememberFontFamily++) % 4 }
                            ) {
                                Text(text = "Font Family")
                            }
                            Button(
                                onClick = { rememberTextStyle = (rememberTextStyle++) % 4 }
                            ) {
                                Text(text = "Text Style")
                            }
                        }

                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            text = stringResource(R.string.typography_hamburgevon_latin),
                            color = Color.Black,
                            style = textStyle,
                            fontSize = 10.sp
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            text = stringResource(R.string.typography_hamburgevon_cyrillic),
                            color = Color.Black,
                            style = textStyle,
                            fontSize = 10.sp
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            text = stringResource(R.string.typography_hamburgevon_japanese),
                            color = Color.Black,
                            style = textStyle,
                            fontSize = 10.sp
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            text = stringResource(R.string.typography_hamburgevon_simplified_chinese),
                            color = Color.Black,
                            style = textStyle,
                            fontSize = 10.sp
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp),
                            text = stringResource(R.string.typography_hamburgevon_numbers_symbols),
                            color = Color.Black,
                            style = textStyle,
                            fontSize = 10.sp
                        )
                    }
                }

            }

        }

    }

}

@Composable
@Preview
private fun PreviewBundleCard() {
    LocalThemeProvider {
        TypographyBundleCard(
            modifier = Modifier
                .widthIn(400.dp)
                .fillMaxWidth(),
            uuid = "4324132",
            title = "Test",
            buyCredits = 600,
            items = listOf(
                MarketTypography(
                    uuid = "0",
                    name = "",
                    group = "",
                    buyCredits = 60,
                    priority = 0,
                    unlocked = true,
                    typography = NEUCHA
                ),
                MarketTypography(
                    uuid = "1",
                    name = "",
                    group = "",
                    buyCredits = 60,
                    priority = 0,
                    unlocked = true,
                    typography = JETBRAINS_MONO
                ),
                MarketTypography(
                    uuid = "2",
                    name = "",
                    group = "",
                    buyCredits = 60,
                    priority = 0,
                    unlocked = false,
                    typography = CLASSIC
                ),
            ),
            surfaceContainerHigh = LocalPalette.current.surfaceContainerHigh,
            onSurfaceVariant = LocalPalette.current.onSurfaceVariant,
            onSurface = LocalPalette.current.onSurface,
            scrim = LocalPalette.current.scrim
        ) {

        }
    }
}

@Composable
@Preview
private fun BundleIncludedItemImagePreview() {
    LocalThemeProvider {
        BundleIncludedTypography(
            modifier = Modifier
                .size(48.dp),
            title = "Item 1",
            isSelected = true,
            isOwned = false,
            surfaceColor = LocalPalette.current.surface,
            onSurfaceColor = LocalPalette.current.onSurface,
            onClick = {}
        )
    }
}
