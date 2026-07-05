package com.tritiumgaming.feature.marketplace.ui.store.palettes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.white_M100
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette

@Composable
fun PaletteCard(
    modifier: Modifier = Modifier,
    buyCredits: Long = 0L,
    badgeRes: Int,
    title: String,
    surfaceContainerHigh: Color,
    onSurfaceVariant: Color,
    onSurface: Color,
    primary: Color,
    secondary: Color,
    tertiary: Color,
    surfaceContainer: Color,
    primaryContainer: Color,
    secondaryContainer: Color,
    tertiaryContainer: Color,
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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
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

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            modifier = Modifier
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ColorSwatch(
                                backgroundColor = onSurface
                            )

                            ColorSwatch(
                                backgroundColor = primary
                            )

                            ColorSwatch(
                                backgroundColor = secondary
                            )

                            ColorSwatch(
                                backgroundColor = tertiary
                            )

                        }

                        Row(
                            modifier = Modifier
                                .weight(1f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            ColorSwatch(
                                backgroundColor = surfaceContainer
                            )

                            ColorSwatch(
                                backgroundColor = primaryContainer
                            )

                            ColorSwatch(
                                backgroundColor = secondaryContainer
                            )

                            ColorSwatch(
                                backgroundColor = tertiaryContainer
                            )

                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(vertical = 4.dp)
                        .background(surfaceContainerHigh.copy(alpha = .3f))
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

@Composable
private fun RowScope.ColorSwatch(
    backgroundColor: Color
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .padding(4.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp))
    )
}


@Composable
@Preview
private fun Test1() {

    val marketPalette = MarketPalette(
        uuid = "4324132",
        name = "Test",
        palette = PaletteResources.PaletteType.CONTENT_CREATOR,
        buyCredits = 69
    )
    val palette = marketPalette.palette?.toPaletteResource() ?: ClassicPalette

    PaletteCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        buyCredits = 0L,
        badgeRes = palette.extrasFamily.badge,
        title = stringResource(palette.extrasFamily.title),
        surfaceContainerHigh = palette.surfaceContainerHigh,
        onSurfaceVariant = palette.onSurfaceVariant,
        onSurface = palette.onSurface,
        primary = palette.primary,
        secondary = palette.secondary,
        tertiary = palette.tertiary,
        surfaceContainer = palette.surfaceContainer,
        primaryContainer = palette.primaryContainer,
        secondaryContainer = palette.secondaryContainer,
        tertiaryContainer = palette.tertiaryContainer,
        onBuyClick = {}
    )

}


@Composable
@Preview
private fun Test2() {

    val marketPalette = MarketPalette(
        uuid = "4324132",
        name = "Test",
        palette = PaletteResources.PaletteType.COMMISSIONER,
        buyCredits = 69
    )
    val palette = marketPalette.palette?.toPaletteResource() ?: ClassicPalette

    PaletteCard(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        buyCredits = 0L,
        badgeRes = palette.extrasFamily.badge,
        title = stringResource(palette.extrasFamily.title),
        surfaceContainerHigh = palette.surfaceContainerHigh,
        onSurfaceVariant = palette.onSurfaceVariant,
        onSurface = palette.onSurface,
        primary = palette.primary,
        secondary = palette.secondary,
        tertiary = palette.tertiary,
        surfaceContainer = palette.surfaceContainer,
        primaryContainer = palette.primaryContainer,
        secondaryContainer = palette.secondaryContainer,
        tertiaryContainer = palette.tertiaryContainer,
        onBuyClick = {}
    )

}

@Composable
@Preview(device = "spec:width=200dp,height=800dp,dpi=240")
private fun Test3() {

    val marketPalette1 = MarketPalette(
        uuid = "4324132",
        name = "Test",
        palette = PaletteResources.PaletteType.WHITEBOARD,
        buyCredits = 69
    )
    val palette1 = marketPalette1.palette?.toPaletteResource() ?: ClassicPalette

    val marketPalette2 = MarketPalette(
        uuid = "4324132",
        name = "Test",
        palette = PaletteResources.PaletteType.COMMISSIONER,
        buyCredits = 69
    )
    val palette2 = marketPalette2.palette?.toPaletteResource() ?: ClassicPalette


    Column {
        PaletteCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            buyCredits = 0L,
            badgeRes = palette1.extrasFamily.badge,
            title = stringResource(palette1.extrasFamily.title),
            surfaceContainerHigh = palette1.surfaceContainerHigh,
            onSurfaceVariant = palette1.onSurfaceVariant,
            onSurface = palette1.onSurface,
            primary = palette1.primary,
            secondary = palette1.secondary,
            tertiary = palette1.tertiary,
            surfaceContainer = palette1.surfaceContainer,
            primaryContainer = palette1.primaryContainer,
            secondaryContainer = palette1.secondaryContainer,
            tertiaryContainer = palette1.tertiaryContainer,
            onBuyClick = {}
        )

        PaletteCard(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            buyCredits = 0L,
            badgeRes = palette2.extrasFamily.badge,
            title = stringResource(palette2.extrasFamily.title),
            surfaceContainerHigh = palette2.surfaceContainerHigh,
            onSurfaceVariant = palette2.onSurfaceVariant,
            onSurface = palette2.onSurface,
            primary = palette2.primary,
            secondary = palette2.secondary,
            tertiary = palette2.tertiary,
            surfaceContainer = palette2.surfaceContainer,
            primaryContainer = palette2.primaryContainer,
            secondaryContainer = palette2.secondaryContainer,
            tertiaryContainer = palette2.tertiaryContainer,
            onBuyClick = {}
        )

    }
}
