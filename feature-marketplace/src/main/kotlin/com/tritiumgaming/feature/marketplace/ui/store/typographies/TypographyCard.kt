package com.tritiumgaming.feature.marketplace.ui.store.typographies


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.common.CustomFontFamily
import com.tritiumgaming.core.ui.theme.white_M100


@Composable
fun TypographyCard(
    modifier: Modifier = Modifier,
    typography: ExtendedTypography,
    title: String,
    buyCredits: Long,
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
                        .background(LocalPalette.current.surfaceContainerHigh)
                        .padding(4.dp),
                    text = title,
                    color = LocalPalette.current.onSurfaceVariant,
                    style = LocalTypography.current.primary.bold.copy(
                        textAlign = TextAlign.Center
                    ),
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = 1.sp,
                        maxFontSize = 50.sp,
                        stepSize = 2.sp
                    )
                )

                var fontFamilyIndex by remember { mutableIntStateOf(0) }
                var textStyleIndex by remember { mutableIntStateOf(0) }

                val rememberTextStyle by remember(fontFamilyIndex, textStyleIndex) {
                    val fontFamily: CustomFontFamily =
                        when (fontFamilyIndex) {
                            0 -> typography.primary
                            1 -> typography.secondary
                            2 -> typography.tertiary
                            else -> typography.quaternary
                        }

                    val textStyle: TextStyle =
                        when (textStyleIndex) {
                            0 -> fontFamily.regular
                            1 -> fontFamily.bold
                            2 -> fontFamily.narrow
                            else -> fontFamily.boldNarrow
                        }
                    mutableStateOf(textStyle)
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
                            onClick = { fontFamilyIndex = ((fontFamilyIndex + 1) % 4f).toInt() }
                        ) {
                            Text(text =
                                when(fontFamilyIndex) {
                                    0 -> "Primary"
                                    1 -> "Secondary"
                                    2 -> "Tertiary"
                                    else -> "Quaternary"
                                }
                            )
                        }
                        Button(
                            onClick = { textStyleIndex = ((textStyleIndex + 1) % 4f).toInt() }
                        ) {
                            Text(text =
                                when(textStyleIndex) {
                                    0 -> "Regular"
                                    1 -> "Bold"
                                    2 -> "Narrow"
                                    else -> "Bold Narrow"
                                }
                            )
                        }
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = stringResource(R.string.typography_hamburgevon_latin),
                        color = Color.Black,
                        style = rememberTextStyle,
                        fontSize = 10.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = stringResource(R.string.typography_hamburgevon_cyrillic),
                        color = Color.Black,
                        style = rememberTextStyle,
                        fontSize = 10.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = stringResource(R.string.typography_hamburgevon_japanese),
                        color = Color.Black,
                        style = rememberTextStyle,
                        fontSize = 10.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = stringResource(R.string.typography_hamburgevon_simplified_chinese),
                        color = Color.Black,
                        style = rememberTextStyle,
                        fontSize = 10.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp),
                        text = stringResource(R.string.typography_hamburgevon_numbers_symbols),
                        color = Color.Black,
                        style = rememberTextStyle,
                        fontSize = 10.sp
                    )
                }

            }

        }

    }

}

@Composable
fun TypographyCard2(
    modifier: Modifier = Modifier,
    typography: ExtendedTypography,
    title: String,
    buyCredits: Long,
    onBuyClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = white_M100)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = title,
                style = typography.primary.bold,
                color = LocalPalette.current.onSurface
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.ic_shop_cost),
                        contentDescription = null,
                        modifier = Modifier.height(20.dp).aspectRatio(1f),
                        colorFilter = ColorFilter.tint(LocalPalette.current.primary)
                    )
                    Text(
                        text = buyCredits.toString(),
                        modifier = Modifier.padding(start = 4.dp),
                        style = typography.quaternary.bold,
                        color = LocalPalette.current.primary
                    )
                }
                Button(
                    onClick = onBuyClick,
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LocalPalette.current.primary)
                ) {
                    Text(text = stringResource(R.string.marketplace_button_item_get).uppercase())
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
