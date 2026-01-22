package com.tritiumgaming.feature.codex.ui.catalog.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.GridIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography

@Composable
fun CodexItemPopup(
    modifier: Modifier = Modifier,
    primaryTitleContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
    secondaryTitleContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit)? = null,
    primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    bodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    textFooterContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    onDismiss: () -> Unit = {}
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CodexItemPortraitPopup(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.7f)
                        .align(Alignment.BottomCenter),
                    primaryTitleContent = primaryTitleContent,
                    secondaryTitleContent = secondaryTitleContent,
                    primaryImageContent = primaryImageContent,
                    primaryDataContent = primaryDataContent,
                    bodyContent = bodyContent,
                    textFooterContent = textFooterContent,
                    onDismiss = onDismiss
                )
            }
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CodexItemLandscapePopup(
                    modifier = modifier
                        .fillMaxWidth(.8f)
                        .fillMaxHeight()
                        .align(Alignment.CenterStart),
                    primaryTitleContent = primaryTitleContent,
                    secondaryTitleContent = secondaryTitleContent,
                    primaryImageContent = primaryImageContent,
                    primaryDataContent = primaryDataContent,
                    bodyContent = bodyContent,
                    textFooterContent = textFooterContent,
                    onDismiss = onDismiss
                )
            }
        }
    }
}

@Composable
fun CodexItemPortraitPopup(
    modifier: Modifier = Modifier,
    primaryTitleContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
    secondaryTitleContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit)? = null,
    primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    bodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    textFooterContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    onDismiss: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .padding(top = 8.dp)
            .clip(RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 4.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp))
            .background(LocalPalette.current.codexFamily.codex4)
            .padding(top = 4.dp)
            .clip(RectangleShape)
            .background(LocalPalette.current.codexFamily.codex2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    color = LocalPalette.current.codexFamily.codex3
                )
                .background(LocalPalette.current.codexFamily.codex3),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            primaryTitleContent?.let { content ->
                content(
                    Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .background(LocalPalette.current.codexFamily.codex2)
                )
            }

            Image(
                modifier = Modifier
                    .width(48.dp)
                    .heightIn(min = 32.dp)
                    .height(IntrinsicSize.Max)
                    .background(LocalPalette.current.codexFamily.codex3)
                    .clickable { onDismiss() },
                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                contentDescription = "Close Button",
                colorFilter = ColorFilter.tint(
                    color = LocalPalette.current.codexFamily.codex2,
                    blendMode = BlendMode.SrcIn
                ),
                contentScale = ContentScale.Fit
            )
        }

        secondaryTitleContent?.let { content ->
            content(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(LocalPalette.current.codexFamily.codex2)
                    .border(
                        width = 1.dp,
                        shape = RectangleShape,
                        color = LocalPalette.current.codexFamily.codex3
                    )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(96.dp)
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    color = LocalPalette.current.codexFamily.codex3
                ),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {

            primaryImageContent?.let { content ->
                Box(
                    modifier = Modifier
                        .aspectRatio(1f, true)
                ) {
                    content(
                        Modifier
                                .fillMaxSize()
                    )
                }
            }

            primaryDataContent?.let { content ->
                Column(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .padding(2.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    content(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    color = LocalPalette.current.codexFamily.codex3
                )
                .padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            bodyContent?.let { content ->
                content(
                    Modifier
                        .weight(1f, true)
                        .fillMaxWidth()
                )
            }

            textFooterContent?.let { content ->
                content(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
    }
}

@Composable
fun CodexItemLandscapePopup(
    modifier: Modifier = Modifier,
    primaryTitleContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
    secondaryTitleContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit)? = null,
    primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    bodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    textFooterContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    onDismiss: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 4.dp,
                bottomStart = 0.dp,
                bottomEnd = 4.dp))
            .background(LocalPalette.current.codexFamily.codex4)
            .padding(end = 4.dp)
            .clip(RectangleShape)
            .background(LocalPalette.current.codexFamily.codex2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    color = LocalPalette.current.codexFamily.codex3
                )
                .background(LocalPalette.current.codexFamily.codex3),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            primaryTitleContent?.let { content ->
                content(
                    Modifier
                        .weight(1f)
                        .wrapContentHeight()
                        .background(LocalPalette.current.codexFamily.codex2)
                )
            }

            Image(
                modifier = Modifier
                    .width(48.dp)
                    .heightIn(min = 32.dp)
                    .height(IntrinsicSize.Max)
                    .background(LocalPalette.current.codexFamily.codex3)
                    .clickable { onDismiss() },
                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                contentDescription = "Close Button",
                colorFilter = ColorFilter.tint(
                    color = LocalPalette.current.codexFamily.codex2,
                    blendMode = BlendMode.SrcIn
                ),
                contentScale = ContentScale.Fit
            )
        }

        secondaryTitleContent?.let { content ->
            content(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(LocalPalette.current.codexFamily.codex2)
                    .border(
                        width = 1.dp,
                        shape = RectangleShape,
                        color = LocalPalette.current.codexFamily.codex3
                    )
            )
        }

        Row {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(96.dp)
                    .border(
                        width = 1.dp,
                        shape = RectangleShape,
                        color = LocalPalette.current.codexFamily.codex3
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {

                primaryImageContent?.let { content ->
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f, true)
                    ) {
                        content(
                            Modifier
                                .fillMaxSize()
                        )
                    }
                }

                primaryDataContent?.let { content ->
                    Column(
                        modifier = Modifier
                            .height(IntrinsicSize.Max)
                            .padding(2.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        content(
                            Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        shape = RectangleShape,
                        color = LocalPalette.current.codexFamily.codex3
                    )
                    .padding(2.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {

                bodyContent?.let { content ->
                    content(
                        Modifier
                            .weight(1f, true)
                            .fillMaxWidth()
                    )
                }

                textFooterContent?.let { content ->
                    content(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    )
                }


            }
        }
    }
}

@Composable
fun CodexItemPopupDataRow(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    data: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .padding(1.dp),
            painter = painterResource(id = icon),
            contentDescription = "Cost Icon",
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(
                color = LocalPalette.current.codexFamily.codex3,
            )
        )

        Text(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp, horizontal = 2.dp),
            text = data,
            style = LocalTypography.current.quaternary.regular.copy(
                textAlign = TextAlign.Start
            ),
            color = LocalPalette.current.codexFamily.codex3,
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(minFontSize = 1.sp)
        )

    }
}

@Composable
fun CodexGroup(
    modifier: Modifier = Modifier,
    @StringRes groupTitle: Int,
    content: @Composable () -> Unit = {}
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(min = 96.dp)
                .wrapContentHeight()
                .background(
                    LocalPalette.current.codexFamily.codex2
                )
                .border(
                    width = 2.dp,
                    color = LocalPalette.current.codexFamily.codex3
                )
                .padding(4.dp)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    initialDelayMillis = 1000,
                    repeatDelayMillis = 1000,
                )
                .zIndex(zIndex = 1f),
            text = stringResource( groupTitle).uppercase(),
            style = LocalTypography.current.quaternary.bold.copy(
                textAlign = TextAlign.Center
            ),
            fontSize = 18.sp,
            color = LocalPalette.current.codexFamily.codex3,
            maxLines = 1
        )

        content()
    }

}

@Composable
fun CodexGroupItemsPortrait(
    modifier: Modifier = Modifier,
    itemCount: Int = 1,
    content: @Composable RowScope.() -> Unit = {}
) {
    val arrangement = when {
        (itemCount == 1) -> Arrangement.Center
        (itemCount == 2) -> Arrangement.SpaceEvenly
        (itemCount > 3) -> Arrangement.spacedBy(4.dp)
        else -> Arrangement.SpaceBetween
    }

    Row(
        modifier = modifier
            .offset(y = (-1).dp)
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = arrangement,
    ) {
        content()
    }
}

@Composable
fun CodexGroupItemsLandscape(
    modifier: Modifier = Modifier,
    itemCount: Int = 1,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    val arrangement = Arrangement.spacedBy(4.dp)

    Column(
        modifier = modifier
            .offset(y = (-1).dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = arrangement,
    ) {
        content()
    }
}

@Composable
fun CodexGroupItem(
    modifier: Modifier = Modifier,
    @DrawableRes image: Int,
    tierLevel: Int? = null,
    isBackground: Boolean = false,
    isBordered: Boolean = false,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .then(
                when (isBordered) {
                    false -> Modifier
                    true -> Modifier
                        .then(
                            when (selected) {
                                true -> Modifier
                                    .border(
                                        2.dp,
                                        LocalPalette.current.codexFamily.codex4
                                    )

                                false -> Modifier
                                    .border(
                                        2.dp,
                                        LocalPalette.current.codexFamily.codex3
                                    )
                            }
                        )
                }
            )
            .clickable { onClick() }
    ) {
        if(isBackground) {
            GridPattern()
        }

        Image(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    when (isBordered) {
                        true -> Modifier
                            .padding(8.dp)

                        false -> Modifier
                    }
                ),
            painter = painterResource(id = image),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )

        tierLevel?.let { tier ->
            TierEmblem(
                modifier = Modifier
                    .padding(start = 4.dp, top = 4.dp)
                    .fillMaxSize(.25f)
                    .align(Alignment.TopStart),
                tierLevel = tier,
                isBordered = isBordered,
                selected = selected
            )
        }

    }
}

@Composable
fun GridPattern(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
    ) {
        /*Image(
            painter = painterResource(id = R.drawable.itemstore_grid),
            contentDescription = "Tier Image",
            modifier = Modifier
                .fillMaxSize()
        )*/
        GridIcon(
            modifier = Modifier
                .fillMaxSize(),
            colors = IconVectorColors(
                fillColor = LocalPalette.current.codexFamily.codex6,
                strokeColor = LocalPalette.current.codexFamily.codex7
            )
        )
    }
}

@Composable
fun TierEmblem(
    modifier: Modifier,
    tierLevel: Int,
    isBordered: Boolean = false,
    selected: Boolean = false
) {
    when(isBordered) {
        true -> {
            TierIcon(
                modifier = modifier
                    .fillMaxSize(),
                tierLevel = tierLevel,
                tintColor = LocalPalette.current.codexFamily.codex4
            )
        }
        false -> {
            TierIcon(
                modifier = modifier
                    .then(
                        Modifier
                            .background(
                                if (selected) {
                                    LocalPalette.current.codexFamily.codex4
                                } else {
                                    LocalPalette.current.codexFamily.codex3
                                }
                            )
                            .padding(8.dp)
                    )
                    .fillMaxSize(),
                tierLevel = tierLevel,
                tintColor = LocalPalette.current.codexFamily.codex2
            )
        }
    }



}

@Composable
fun TierIcon(
    modifier: Modifier = Modifier,
    tierLevel: Int,
    tintColor: Color = Color.White,
    tintMode: BlendMode = BlendMode.SrcIn
) {

    val image = when(tierLevel) {
        1 -> R.drawable.ic_tier_1
        2 -> R.drawable.ic_tier_2
        3 -> R.drawable.ic_tier_3
        else -> return
    }

    Image(
        modifier = modifier,
        painter = painterResource(id = image),
        contentDescription = "Tier Image",
        colorFilter = ColorFilter.tint(tintColor, tintMode)
    )
}

@Preview
@Composable
private fun CodexGroupPreview() {
    SelectiveTheme {
        CodexGroup(
            groupTitle = R.string.equipment_info_name_dots
        )
    }
}

@Preview
@Composable
fun CodexItemPopupPortraitPreview() {

    val primaryText = AnnotatedString.fromHtml(
        stringResource(R.string.shop_equipment_crucifix_data_info_1))
    val secondaryText = AnnotatedString.fromHtml(
        stringResource(R.string.shop_equipment_crucifix_data_flavortext_1))
    val footerText = listOf(
        AnnotatedString.fromHtml(
            stringResource(R.string.shop_equipment_crucifix_data_flavortext_1))
    )
    val primaryTitle = AnnotatedString.fromHtml(
        stringResource(R.string.equipment_info_name_crucifix))
    val secondaryTitle = AnnotatedString.fromHtml(
        stringResource(R.string.equipment_info_name_crucifix))
    val buyCost = 3000
    val unlockLevel = 30
    val upgradeCost = 30000

    SelectiveTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            CodexItemPortraitPopup(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.7f)
                    .align(Alignment.BottomCenter),
                primaryTitleContent = { modifier ->
                    Text(
                        modifier = modifier
                            .basicMarquee(
                                iterations = Int.MAX_VALUE,
                                initialDelayMillis = 1000,
                                repeatDelayMillis = 1000,
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        style = LocalTypography.current.quaternary.bold.copy(
                            textAlign = TextAlign.Start
                        ),
                        color = LocalPalette.current.codexFamily.codex3,
                        maxLines = 1,
                        fontSize = 20.sp,
                        text = primaryTitle
                    )
                },
                secondaryTitleContent = { modifier ->
                    Text(
                        modifier = modifier
                            .basicMarquee(
                                iterations = Int.MAX_VALUE,
                                initialDelayMillis = 1000,
                                repeatDelayMillis = 1000,
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        text = secondaryTitle,
                        style = LocalTypography.current.quaternary.bold.copy(
                            textAlign = TextAlign.Start
                        ),
                        color = LocalPalette.current.codexFamily.codex3,
                        maxLines = 1,
                        fontSize = 20.sp
                    )

                },
                primaryImageContent = { modifier ->
                    GridIcon(
                        modifier = modifier,
                        colors = IconVectorColors(
                            fillColor = LocalPalette.current.codexFamily.codex6,
                            strokeColor = LocalPalette.current.codexFamily.codex7
                        )
                    )
                    /*Image(
                        modifier = modifier,
                        painter = painterResource(id = R.drawable.itemstore_grid),
                        contentDescription = "Primary Icon",
                        contentScale = ContentScale.Fit
                    )*/
                    Image(
                        modifier = modifier
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.icon_shop_para_2),
                        contentDescription = "Primary Icon",
                        contentScale = ContentScale.Fit
                    )
                },
                primaryDataContent = { modifier ->

                    CodexItemPopupDataRow(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .heightIn(max = 32.dp),
                        icon = R.drawable.ic_shop_cost,
                        data = "$buyCost"
                    )

                    CodexItemPopupDataRow(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .heightIn(max = 32.dp),
                        icon = R.drawable.ic_shop_unlock,
                        data = "$unlockLevel"
                    )

                    CodexItemPopupDataRow(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .heightIn(max = 32.dp),
                        icon = R.drawable.ic_shop_upgrade_cost,
                        data = "$upgradeCost"
                    )

                },
                bodyContent = { modifier ->
                    Column(
                        modifier = modifier
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f, false)
                                .wrapContentHeight()
                                .padding(4.dp),
                            text = secondaryText,
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Start
                            ),
                            color = LocalPalette.current.codexFamily.codex3,
                            fontSize = 20.sp
                        )

                        HorizontalDivider(
                            modifier = Modifier
                                .height(2.dp),
                            color = LocalPalette.current.codexFamily.codex3
                        )

                        Text(
                            modifier = Modifier
                                .weight(1f, false)
                                .wrapContentHeight()
                                .padding(4.dp),
                            text = primaryText,
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Start
                            ),
                            color = LocalPalette.current.codexFamily.codex3,
                            fontSize = 18.sp
                        )

                    }
                },
                textFooterContent = { modifier ->
                    Column(
                        modifier = modifier,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp),
                            color = LocalPalette.current.codexFamily.codex3
                        )

                        val footerTextArr = footerText.map { it }
                        val footerText = footerTextArr.joinToString("")

                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .horizontalScroll(rememberScrollState()),
                            text = footerText,
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Start
                            ),
                            color = LocalPalette.current.codexFamily.codex3,
                            maxLines = 2,
                            fontSize = 18.sp
                        )
                    }
                },
                onDismiss = {}
            )
        }
    }
}

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun CodexItemPopupLandscapePreview() {

    val primaryText = AnnotatedString.fromHtml(
        stringResource(R.string.shop_equipment_crucifix_data_info_1))
    val secondaryText = AnnotatedString.fromHtml(
        stringResource(R.string.shop_equipment_crucifix_data_flavortext_1))
    val footerText = listOf(
        AnnotatedString.fromHtml(
            stringResource(R.string.shop_equipment_crucifix_data_flavortext_1))
    )
    val primaryTitle = AnnotatedString.fromHtml(
        stringResource(R.string.equipment_info_name_crucifix))
    val secondaryTitle = AnnotatedString.fromHtml(
        stringResource(R.string.equipment_info_name_crucifix))
    val buyCost = 3000
    val unlockLevel = 30
    val upgradeCost = 30000

    SelectiveTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            CodexItemLandscapePopup(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .fillMaxHeight()
                    .align(Alignment.CenterStart),
                primaryTitleContent = { modifier ->
                    Text(
                        modifier = modifier
                            .basicMarquee(
                                iterations = Int.MAX_VALUE,
                                initialDelayMillis = 1000,
                                repeatDelayMillis = 1000,
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        style = LocalTypography.current.quaternary.bold.copy(
                            textAlign = TextAlign.Start
                        ),
                        color = LocalPalette.current.codexFamily.codex3,
                        maxLines = 1,
                        fontSize = 20.sp,
                        text = primaryTitle
                    )
                },
                secondaryTitleContent = { modifier ->
                    Text(
                        modifier = modifier
                            .basicMarquee(
                                iterations = Int.MAX_VALUE,
                                initialDelayMillis = 1000,
                                repeatDelayMillis = 1000,
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        text = secondaryTitle,
                        style = LocalTypography.current.quaternary.bold.copy(
                            textAlign = TextAlign.Start
                        ),
                        color = LocalPalette.current.codexFamily.codex3,
                        maxLines = 1,
                        fontSize = 20.sp
                    )

                },
                primaryImageContent = { modifier ->
                    GridIcon(
                        modifier = modifier,
                        colors = IconVectorColors(
                            fillColor = LocalPalette.current.codexFamily.codex6,
                            strokeColor = LocalPalette.current.codexFamily.codex7
                        )
                    )
                    /*Image(
                        modifier = modifier,
                        painter = painterResource(id = R.drawable.itemstore_grid),
                        contentDescription = "Primary Icon",
                        contentScale = ContentScale.Fit
                    )*/
                    Image(
                        modifier = modifier
                            .padding(8.dp),
                        painter = painterResource(id = R.drawable.icon_shop_para_2),
                        contentDescription = "Primary Icon",
                        contentScale = ContentScale.Fit
                    )
                },
                primaryDataContent = { modifier ->

                    CodexItemPopupDataRow(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .heightIn(max = 32.dp),
                        icon = R.drawable.ic_shop_cost,
                        data = "$buyCost"
                    )

                    CodexItemPopupDataRow(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .heightIn(max = 32.dp),
                        icon = R.drawable.ic_shop_unlock,
                        data = "$unlockLevel"
                    )

                    CodexItemPopupDataRow(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .heightIn(max = 32.dp),
                        icon = R.drawable.ic_shop_upgrade_cost,
                        data = "$upgradeCost"
                    )

                },
                bodyContent = { modifier ->
                    Column(
                        modifier = modifier
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(1f, false)
                                .wrapContentHeight()
                                .padding(4.dp),
                            text = secondaryText,
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Start
                            ),
                            color = LocalPalette.current.codexFamily.codex3,
                            fontSize = 20.sp
                        )

                        HorizontalDivider(
                            modifier = Modifier
                                .height(2.dp),
                            color = LocalPalette.current.codexFamily.codex3
                        )

                        Text(
                            modifier = Modifier
                                .weight(1f, false)
                                .wrapContentHeight()
                                .padding(4.dp),
                            text = primaryText,
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Start
                            ),
                            color = LocalPalette.current.codexFamily.codex3,
                            fontSize = 18.sp
                        )

                    }
                },
                textFooterContent = { modifier ->
                    Column(
                        modifier = modifier,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp),
                            color = LocalPalette.current.codexFamily.codex3
                        )

                        val footerTextArr = footerText.map { it }
                        val footerText = footerTextArr.joinToString("")

                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .horizontalScroll(rememberScrollState()),
                            text = footerText,
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Start
                            ),
                            color = LocalPalette.current.codexFamily.codex3,
                            maxLines = 2,
                            fontSize = 18.sp
                        )
                    }
                },
                onDismiss = {}
            )
        }
    }
}
