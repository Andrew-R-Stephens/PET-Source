package com.tritiumgaming.feature.marketplace.ui.store.bundles

import androidx.annotation.IntegerRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.composite.MarkCheckCircleIconComposite
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.theme.white_M100
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType
import com.tritiumgaming.shared.data.market.palette.mappers.asUuid
import com.tritiumgaming.shared.data.market.palette.model.MarketPalette

@Composable
fun PaletteBundleCard(
    modifier: Modifier = Modifier,
    title: String,
    items: List<MarketPalette>,
    buyCost: Long = 0L,
    originalCost: Long = 0L,
    discountRatio: Float = 0f,
    discount: Long = 0L,
    discountedCost: Long = 0L,
    canUnlock: Boolean = false,
    isOwned: Boolean = false,
    surfaceContainerHigh: Color,
    onSurfaceVariant: Color,
    onSurface: Color,
    scrim: Color,
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
                horizontalAlignment = CenterHorizontally
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

                val unlockedCount = items.count { it.unlocked }
                val totalCount = items.size
                val lockedCount = totalCount - unlockedCount

                val isQualified = lockedCount > 1
                val hasDiscount = unlockedCount > 0
                val showItemDiscount = !isOwned && hasDiscount/* && isQualified*/

                val discountPerItem = if (unlockedCount > 0) {
                    (unlockedCount.toFloat() / totalCount) / unlockedCount
                } else 0f

                LazyRow(
                    modifier = Modifier
                        .height(96.dp)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, CenterHorizontally),
                    verticalAlignment = CenterVertically
                ) {
                    items(items = items, key = { it.uuid }) { marketPalette ->
                        marketPalette.palette?.let { paletteType ->
                            val paletteRes = paletteType.toPaletteResource()

                            BundleIncludedPaletteImage(
                                modifier = Modifier.width(48.dp),
                                isSelected = selectedPalette?.asUuid() == marketPalette.uuid,
                                title = stringResource(paletteRes.extrasFamily.title),
                                isOwned = marketPalette.unlocked,
                                showDiscount = showItemDiscount,
                                discountRatio = discountPerItem,
                                primaryColor = paletteRes.primary,
                                surfaceColor = paletteRes.surface,
                                onSurfaceColor = LocalPalette.current.onSurface,
                                iconRes = paletteRes.extrasFamily.badge,
                                onClick = { selectedPalette =
                                    if (selectedPalette?.asUuid() == marketPalette.uuid) { null }
                                    else paletteType
                                }
                            )
                            
                        }
                    }
                }

                selectedPalette?.toPaletteResource()?.let { palette ->

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                            text = "Preview:",
                            color = Color.Black,
                            style = LocalTypography.current.quaternary.bold,
                            fontSize = 10.sp,
                            textAlign = TextAlign.Start,
                            maxLines = 1
                        )

                        PaletteDetailsCard(
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
                }

                if(!isOwned) {
                    val listPriceTotal = items.sumOf { it.buyCredits }
                    val calculatedBundleDiscountRatio = 1f - (buyCost / listPriceTotal.toFloat())
                    val calculatedProratedDiscountRatio = if (totalCount > 0) unlockedCount.toFloat() / totalCount else 0f
                    val calculatedProratedDiscount = (buyCost * calculatedProratedDiscountRatio).toLong()
                    val finalPrice = buyCost - calculatedProratedDiscount

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(vertical = 4.dp)
                            .background(scrim.copy(alpha = .3f))
                            .padding(4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp, CenterVertically),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        if (!isQualified) {
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 12.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp, CenterVertically),
                                horizontalAlignment = CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(R.string.marketplace_label_bundle_unqualified).uppercase(),
                                    color = surfaceContainerHigh,
                                    style = LocalTypography.current.quaternary.bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = stringResource(R.string.marketplace_label_bundle_unqualified_desc).uppercase(),
                                    color = surfaceContainerHigh,
                                    style = LocalTypography.current.quaternary.regular,
                                    fontSize = 10.sp
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(
                                        16.dp,
                                        CenterHorizontally
                                    ),
                                    verticalAlignment = CenterVertically
                                ) {
                                    Button(
                                        modifier = Modifier.height(48.dp),
                                        onClick = onBuyClick,
                                        enabled = canUnlock,
                                        shape = RoundedCornerShape(2.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = surfaceContainerHigh
                                        ),
                                    ) {
                                        Text(
                                            text = stringResource(R.string.marketplace_button_item_get).uppercase(),
                                            style = LocalTypography.current.quaternary.bold,
                                            color = onSurface,
                                            fontSize = 18.sp
                                        )
                                    }

                                    Row(
                                        verticalAlignment = CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Image(
                                            modifier = Modifier.size(24.dp),
                                            painter = painterResource(R.drawable.ic_shop_cost),
                                            contentDescription = "Cost",
                                            colorFilter = ColorFilter.tint(surfaceContainerHigh)
                                        )

                                        val displayPrice = if (hasDiscount) "$finalPrice" else "$buyCost"
                                        Text(
                                            text = displayPrice,
                                            fontSize = 24.sp,
                                            color = surfaceContainerHigh,
                                            style = LocalTypography.current.quaternary.bold
                                        )
                                    }

                                }

                            }

                            val labelStyle = LocalTypography.current.quaternary.bold.copy(
                                fontSize = 14.sp
                            )
                            val rowStyle = LocalTypography.current.quaternary.regular.copy(
                                fontSize = 14.sp
                            )

                            val calculatedBundleDiscountPercent = calculatedBundleDiscountRatio.toPercentageString(false)
                            val calculatedBundleDiscount = (calculatedBundleDiscountRatio * listPriceTotal).toLong()
                            val proratedDiscountPercent = calculatedProratedDiscountRatio.toPercentageString(false)
                            val proratedDiscountValue = "$calculatedProratedDiscount"

                            data class BreakdownData(
                                val data: String,
                                val color: Color = onSurface,
                                val style: TextStyle = rowStyle
                            )
                            data class BreakdownItem(
                                val first: BreakdownData,
                                val middle: BreakdownData,
                                val last: BreakdownData
                            )

                            val breakdownRows = listOf(
                                BreakdownItem(
                                    BreakdownData(
                                        stringResource(R.string.marketplace_label_bundle_item_total),
                                        color = onSurface,
                                        style = labelStyle
                                    ),
                                    BreakdownData(
                                        "$listPriceTotal",
                                        color = onSurface,
                                        style = rowStyle.copy(fontWeight = FontWeight.Bold)
                                    ),
                                    BreakdownData(
                                        "",
                                        color = onSurface,
                                    )
                                ),
                                BreakdownItem(
                                    BreakdownData(
                                        data = stringResource(R.string.marketplace_label_bundle_price_discount),
                                        color = LocalPalette.current.onSurfaceVariant,
                                        style = labelStyle
                                    ),
                                    BreakdownData(
                                        "-$calculatedBundleDiscount",
                                        style = rowStyle.copy(fontWeight = FontWeight.Bold)
                                    ),
                                    BreakdownData(
                                        "(-$calculatedBundleDiscountPercent)",
                                        color = LocalPalette.current.onSurfaceVariant
                                    )
                                ),
                                BreakdownItem(
                                    BreakdownData(
                                        data = stringResource(R.string.marketplace_label_bundle_price),
                                        color = onSurface,
                                        style = labelStyle
                                    ),
                                    BreakdownData(
                                        "$buyCost",
                                        color = onSurface,
                                        style = rowStyle.copy(fontWeight = FontWeight.Bold)
                                    ),
                                    BreakdownData(
                                        "",
                                        color = onSurface,
                                    )
                                ),
                                BreakdownItem(
                                    BreakdownData(
                                        stringResource(R.string.marketplace_label_bundle_unlocked_discount),
                                        color = LocalPalette.current.onSurfaceVariant,
                                        style = labelStyle
                                    ),
                                    BreakdownData(
                                        "-$proratedDiscountValue",
                                        color = LocalPalette.current.onSurfaceVariant,
                                        style = rowStyle.copy(fontWeight = FontWeight.Bold)
                                    ),
                                    BreakdownData(
                                        "(-$proratedDiscountPercent)",
                                        color = LocalPalette.current.onSurfaceVariant
                                    )
                                ),
                                BreakdownItem(
                                    BreakdownData(
                                        stringResource(R.string.marketplace_label_bundle_final_price),
                                        color = onSurface,
                                        style = labelStyle
                                    ),
                                    BreakdownData(
                                        "$finalPrice",
                                        color = onSurface,
                                        style = rowStyle.copy(fontWeight = FontWeight.Bold)
                                    ),
                                    BreakdownData(
                                        "",
                                        color = onSurface,
                                    )
                                )
                            )

                            Surface(
                                modifier = Modifier,
                                shape = RoundedCornerShape(8.dp),
                                color = LocalPalette.current.surfaceContainer.copy(alpha = .9f),
                            ) {
                                var isExpanded by remember { mutableStateOf(false) }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .clickable { isExpanded = !isExpanded },
                                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                                    horizontalAlignment = CenterHorizontally
                                ) {
                                    if (listPriceTotal > buyCost || hasDiscount) {
                                        val title = if (isExpanded) "Hide Details" else "Show Details"

                                        Text(
                                            text = (if (isExpanded) "$title ▲" else "$title ▼").uppercase(),
                                            modifier = Modifier,
                                            color = LocalPalette.current.onSurface,
                                            style = LocalTypography.current.quaternary.bold,
                                            fontSize = 14.sp,
                                            maxLines = 1
                                        )
                                    }

                                    if (isExpanded && (listPriceTotal > buyCost || hasDiscount)) {

                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.spacedBy(
                                                4.dp,
                                                CenterHorizontally
                                            )
                                        ) {
                                            Column(
                                                modifier = Modifier.width(IntrinsicSize.Max),
                                                horizontalAlignment = Alignment.End
                                            ) {
                                                breakdownRows.forEach { (first, _, _) ->
                                                    Text(
                                                        text = first.data.uppercase(),
                                                        modifier = Modifier.fillMaxWidth(),
                                                        textAlign = TextAlign.Start,
                                                        color = first.color,
                                                        style = first.style,
                                                        softWrap = true
                                                    )
                                                }
                                            }

                                            Column(
                                                modifier = Modifier.width(IntrinsicSize.Max),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                breakdownRows.forEach { (_, _, last) ->
                                                    Text(
                                                        text = last.data.uppercase(),
                                                        modifier = Modifier.fillMaxWidth(),
                                                        textAlign = TextAlign.End,
                                                        color = last.color,
                                                        style = last.style
                                                    )
                                                }
                                            }

                                            Column(
                                                modifier = Modifier.width(IntrinsicSize.Max),
                                                horizontalAlignment = Alignment.End
                                            ) {
                                                breakdownRows.forEach { (first) ->
                                                    Text(
                                                        text = ":",
                                                        modifier = Modifier.fillMaxWidth(),
                                                        textAlign = TextAlign.End,
                                                        color = first.color,
                                                        style = first.style,
                                                        softWrap = true
                                                    )
                                                }
                                            }

                                            Column(
                                                modifier = Modifier
                                                    .width(IntrinsicSize.Max),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                breakdownRows.forEach { (_, middle, _) ->
                                                    Text(
                                                        text = middle.data.uppercase(),
                                                        modifier = Modifier.fillMaxWidth(),
                                                        textAlign = TextAlign.End,
                                                        color = middle.color,
                                                        style = middle.style
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            }

        }

    }

}

@Composable
private fun BundleIncludedPaletteImage(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    isOwned: Boolean = false,
    showDiscount: Boolean = false,
    discountRatio: Float = 0f,
    primaryColor: Color,
    surfaceColor: Color,
    onSurfaceColor: Color,
    @IntegerRes iconRes: Int,
    onClick: () -> Unit,
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
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(iconRes),
                    contentDescription = "Icon"
                )

                if (isOwned) {
                    if (showDiscount) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.TopStart
                        ) {
                            val text = "-${discountRatio.toPercentageString(false)}"
                            val style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Center
                            )
                            val fontSize = 10.sp


                            Box(
                                modifier = Modifier
                                    .graphicsLayer(rotationZ = -15f)
                                    .padding(2.dp)
                            ) {
                                Surface(
                                    shape = RoundedCornerShape(size = 8.dp),
                                    color = surfaceColor.copy(alpha = .6f)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .padding(2.dp)
                                    ) {
                                        Text(
                                            text = text,
                                            color = surfaceColor,
                                            style = style.copy(
                                                drawStyle = Stroke(
                                                    miter = 10f,
                                                    width = 4f,
                                                    join = StrokeJoin.Round
                                                )
                                            ),
                                            fontSize = fontSize,
                                            maxLines = 1
                                        )
                                        Text(
                                            text = text,
                                            color = onSurfaceColor,
                                            style = style,
                                            fontSize = fontSize,
                                            maxLines = 1
                                        )
                                    }
                                }
                            }
                        }
                    }

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
private fun BundleIncludedTypographyFont(
    modifier: Modifier = Modifier,
    title: String,
    isSelected: Boolean = false,
    isOwned: Boolean = false,
    showDiscount: Boolean = false,
    discountRatio: Float = 0f,
    primaryColor: Color,
    surfaceColor: Color,
    onSurfaceColor: Color,
    onClick: () -> Unit,
    preview: @Composable (Modifier) -> Unit = {},
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
                preview(
                    Modifier
                        .fillMaxSize()
                )

                if (isOwned) {
                    if (showDiscount) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            val text = "-${discountRatio.toPercentageString(false)}"
                            val style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Center
                            )
                            val fontSize = 10.sp

                            Box(
                                modifier = Modifier
                                    .graphicsLayer(rotationZ = -15f)
                                    .padding(4.dp)
                            ) {
                                Text(
                                    text = text,
                                    color = surfaceColor,
                                    style = style.copy(
                                        drawStyle = Stroke(
                                            miter = 10f,
                                            width = 2f,
                                            join = StrokeJoin.Round
                                        )
                                    ),
                                    fontSize = fontSize,
                                    maxLines = 1
                                )
                                Text(
                                    text = text,
                                    color = primaryColor,
                                    style = style,
                                    fontSize = fontSize,
                                    maxLines = 1
                                )
                            }
                        }
                    }

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
private fun PaletteDetailsCard(
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

            }

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
private fun PreviewBundleCard() {
    LocalThemeProvider {
        PaletteBundleCard(
            modifier = Modifier
                .widthIn(400.dp)
                .fillMaxWidth(),
            title = "Test",
            buyCost = 600,
            discount = 100L,
            discountedCost = 500L,
            items = listOf(
                MarketPalette(
                    uuid = "0",
                    name = "",
                    group = "",
                    buyCredits = 60,
                    priority = 0,
                    unlocked = true,
                    palette = PaletteType.AGENT
                ),
                MarketPalette(
                    uuid = "1",
                    name = "",
                    group = "",
                    buyCredits = 60,
                    priority = 0,
                    unlocked = true,
                    palette = PaletteType.DETECTIVE
                ),
                MarketPalette(
                    uuid = "2",
                    name = "",
                    group = "",
                    buyCredits = 60,
                    priority = 0,
                    unlocked = true,
                    palette = PaletteType.COMMISSIONER
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
private fun BundleIncludedPaletteImagePreview() {
    LocalThemeProvider {
        BundleIncludedPaletteImage(
            modifier = Modifier
                .size(48.dp),
            title = "Item 1",
            isSelected = true,
            isOwned = true,
            showDiscount = true,
            discountRatio = 0.15f,
            primaryColor = LocalPalette.current.primary,
            surfaceColor = LocalPalette.current.surface,
            onSurfaceColor = LocalPalette.current.onSurface,
            iconRes = LocalPalette.current.extrasFamily.badge,
            onClick = {}
        )
    }
}

@Composable
@Preview(name = "Qualified Bundle")
private fun PaletteBundleCardQualifiedPreview() {
    LocalThemeProvider {
        PaletteBundleCard(
            modifier = Modifier
                .widthIn(400.dp)
                .fillMaxWidth(),
            title = "Special Bundle",
            buyCost = 1000L,
            discount = 250L,
            discountedCost = 750L,
            items = listOf(
                MarketPalette(
                    uuid = "0", unlocked = true, buyCredits = 250, palette = PaletteType.AGENT,
                    name = "", group = "", priority = 0
                ),
                MarketPalette(
                    uuid = "1", unlocked = false, buyCredits = 250, palette = PaletteType.DETECTIVE,
                    name = "", group = "", priority = 0
                ),
                MarketPalette(
                    uuid = "2", unlocked = false, buyCredits = 250, palette = PaletteType.COMMISSIONER,
                    name = "", group = "", priority = 0
                ),
                MarketPalette(
                    uuid = "3", unlocked = false, buyCredits = 250, palette = PaletteType.SPECIALIST,
                    name = "", group = "", priority = 0
                ),
            ),
            surfaceContainerHigh = LocalPalette.current.surfaceContainerHigh,
            onSurfaceVariant = LocalPalette.current.onSurfaceVariant,
            onSurface = LocalPalette.current.onSurface,
            scrim = LocalPalette.current.scrim,
            canUnlock = true
        )
    }
}

@Composable
@Preview(name = "Owned Bundle")
private fun PaletteBundleCardOwnedPreview() {
    LocalThemeProvider {
        PaletteBundleCard(
            modifier = Modifier
                .widthIn(400.dp)
                .fillMaxWidth(),
            title = "Owned Bundle",
            isOwned = true,
            items = listOf(
                MarketPalette(
                    uuid = "0", unlocked = true, buyCredits = 250, palette = PaletteType.AGENT,
                    name = "", group = "", priority = 0
                ),
                MarketPalette(
                    uuid = "1", unlocked = true, buyCredits = 250, palette = PaletteType.DETECTIVE,
                    name = "", group = "", priority = 0
                ),
            ),
            surfaceContainerHigh = LocalPalette.current.surfaceContainerHigh,
            onSurfaceVariant = LocalPalette.current.onSurfaceVariant,
            onSurface = LocalPalette.current.onSurface,
            scrim = LocalPalette.current.scrim
        )
    }
}
