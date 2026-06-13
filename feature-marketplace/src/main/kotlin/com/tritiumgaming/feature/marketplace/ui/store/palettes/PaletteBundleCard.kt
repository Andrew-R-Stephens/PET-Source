package com.tritiumgaming.feature.marketplace.ui.store.palettes

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.composite.MarkCheckCircleIconComposite
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.white_M100
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources

@Composable
fun PaletteBundleCard(
    modifier: Modifier = Modifier,
    uuid: String,
    buyCredits: Long = 0L,
    title: String,
    surfaceContainerHigh: Color,
    onSurfaceVariant: Color,
    onSurface: Color,
    items: List<MarketPalette>,
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

                LazyRow(
                    modifier = Modifier
                        .height(96.dp)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(items = items, key = {it.uuid}) { marketPalette ->
                        marketPalette.palette?.let { palette ->
                            val palette = palette.toPaletteResource()

                            BundleIncludedThemeImage(
                                modifier = Modifier
                                    .size(48.dp),
                                title = stringResource(palette.extrasFamily.title),
                                icon = palette.extrasFamily.badge,
                                isOwned = marketPalette.unlocked,
                                surfaceColor = palette.surface,
                                onSurfaceColor = palette.onSurface,
                                onBuyClick = {}
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
private fun BundleIncludedThemeImage(
    modifier: Modifier,
    title: String,
    isOwned: Boolean = false,
    @DrawableRes icon: Int,
    surfaceColor: Color,
    onSurfaceColor: Color,
    onBuyClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clickable(onClick = onBuyClick),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(),
                painter = painterResource(icon),
                contentDescription = "",
            )

            if(isOwned) {
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
            text = title,
            color = onSurfaceColor
        )
    }
}

@Composable
@Preview
private fun PreviewBundleCard() {
    SelectiveTheme {
        PaletteBundleCard(
            modifier = Modifier,
            uuid = "4324132",
            title = "Test",
            buyCredits = 600,
            items = listOf(
                MarketPalette(
                    uuid = "0",
                    name = "",
                    group = "",
                    buyCredits = 60,
                    priority = 0,
                    unlocked = true,
                    palette = PaletteResources.PaletteType.ANALYST
                ),
                MarketPalette(
                    uuid = "1",
                    name = "",
                    group = "",
                    buyCredits = 60,
                    priority = 0,
                    unlocked = true,
                    palette = PaletteResources.PaletteType.ARTIST
                ),
                MarketPalette(
                    uuid = "2",
                    name = "",
                    group = "",
                    buyCredits = 60,
                    priority = 0,
                    unlocked = false,
                    palette = PaletteResources.PaletteType.COMMISSIONER
                ),
            ),
            surfaceContainerHigh = LocalPalette.current.surfaceContainerHigh,
            onSurfaceVariant = LocalPalette.current.onSurfaceVariant,
            onSurface = LocalPalette.current.onSurface
        ) {

        }
    }
}

@Composable
@Preview
private fun BundleIncludedItemImagePreview() {
    SelectiveTheme {
        BundleIncludedThemeImage(
            modifier = Modifier
                .size(48.dp),
            title = "Item 1",
            isOwned = false,
            icon = com.tritiumgaming.core.ui.R.drawable.theme_badge_artist,
            surfaceColor = LocalPalette.current.surface,
            onSurfaceColor = LocalPalette.current.onSurface,
            onBuyClick = {}
        )
    }
}
