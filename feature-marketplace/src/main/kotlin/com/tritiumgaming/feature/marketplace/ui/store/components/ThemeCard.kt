package com.tritiumgaming.feature.marketplace.ui.store.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.mappers.toPaletteResource
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.theme.white_M100
import com.tritiumgaming.shared.data.market.bundle.model.MarketBundle
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette
import com.tritiumgaming.shared.data.market.palette.model.PaletteResources

@Composable
fun PaletteCard(
    marketPalette: MarketPalette,
    onBuyClick: () -> Unit
) {
    var rememberHeight by remember { mutableStateOf(0.dp) }
    val density = LocalConfiguration.current.densityDpi

    SelectiveTheme(
        palette = marketPalette.palette?.toPaletteResource() ?: LocalPalette.current
    ) {
        Card(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = white_M100
            )
        ) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                propagateMinConstraints = true
            ) {

                Image(
                    modifier = Modifier
                        .zIndex(-1f)
                        .scale(3f)
                        .height(rememberHeight * density)
                        .align(Alignment.CenterEnd),
                    painter = painterResource(LocalPalette.current.extrasFamily.badge),
                    contentDescription = "Background",
                    alpha = .1f,
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .onSizeChanged { size -> rememberHeight = size.height.dp },
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(LocalPalette.current.surfaceContainerHigh)
                            .padding(4.dp),
                        text = stringResource(LocalPalette.current.extrasFamily.title),
                        color = LocalPalette.current.textFamily.emphasis,
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
                            .heightIn(max = 96.dp)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth(.2f)
                                .aspectRatio(1f),
                            painter = painterResource(LocalPalette.current.extrasFamily.badge),
                            contentDescription = "Badge"
                        )

                        val colors = LocalPalette.current.coreFamily.list

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
                                colors.forEachIndexed { index, color ->
                                    if(index < (colors.size-1)/2f) {
                                        Card(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(4.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = color
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        ) { Box(modifier = Modifier.fillMaxSize()) { } }
                                    }
                                }
                            }

                            Row(
                                modifier = Modifier
                                    .weight(1f),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                colors.forEachIndexed { index, color ->
                                    if(index > (colors.size-1)/2f) {
                                        Card(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(4.dp),
                                            colors = CardDefaults.cardColors(
                                                containerColor = color
                                            ),
                                            shape = RoundedCornerShape(8.dp)
                                        ) { Box(modifier = Modifier.fillMaxSize()) { } }
                                    }
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 4.dp)
                            .background(LocalPalette.current.surfaceContainerHigh.copy(alpha = .3f))
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier
                                .height(36.dp)
                                .weight(1f, false),
                            onClick = onBuyClick,
                            shape = RoundedCornerShape(2.dp),
                            contentPadding = PaddingValues(4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = LocalPalette.current.surfaceContainerHigh
                            ),
                        ) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically),
                                text = stringResource(R.string.marketplace_button_item_get).uppercase(),
                                style = LocalTypography.current.quaternary.bold.copy(
                                    textAlign = TextAlign.Center
                                ),
                                color = LocalPalette.current.textFamily.body,
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
                            colorFilter = ColorFilter.tint(LocalPalette.current.surfaceContainerHigh)
                        )

                        Text(
                            modifier = Modifier
                                .weight(1f, false),
                            text = "${marketPalette.buyCredits}",
                            fontSize = 24.sp,
                            color = LocalPalette.current.surfaceContainerHigh,
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
fun BundleCard(
    marketBundle: MarketBundle,
    onBuyClick: () -> Unit
) {
    SelectiveTheme(

    ) {

    }
}

/*@Composable
private fun MarketCard(
    content: @Composable () -> Unit
) {
    Card(

    ) {
        content()
    }
}*/

@Composable
private fun BundleIncludedThemeImage(

) {

}

@Composable
@Preview
private fun PreviewThemeCard() {
    PaletteCard(
        marketPalette = MarketPalette(
            uuid = "4324132",
            name = "Test",
            palette = PaletteResources.PaletteType.COMMISSIONER,
            buyCredits = 69
        )
    ) {

    }
}

@Composable
@Preview
private fun PreviewBundleCard() {
    BundleCard(
        marketBundle = MarketBundle(
            uuid = "4324132",
            name = "Test",
            items = emptyList()
        )
    ) {

    }
}

@Composable
@Preview
private fun PreviewBundleIncludedItemImage() {
    BundleIncludedThemeImage()
}
