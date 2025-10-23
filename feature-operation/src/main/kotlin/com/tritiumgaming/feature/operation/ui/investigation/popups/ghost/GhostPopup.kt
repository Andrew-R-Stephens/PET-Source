package com.tritiumgaming.feature.operation.ui.investigation.popups.ghost

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.operation.app.mappers.ghost.toDrawableResource
import com.tritiumgaming.feature.operation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.operation.ui.investigation.popups.common.PageButton
import com.tritiumgaming.shared.operation.domain.popup.model.GhostPopupRecord


@Composable
fun GhostPopup(
    modifier: Modifier,
    record: GhostPopupRecord,
    onClickPrevious: () -> Unit = {},
    onClickNext: () -> Unit = {},
    onDismiss: () -> Unit
) {
    GhostPopupContent(
        modifier = modifier,
        ghostPopupRecord = record,
        onClickPrevious = { onClickPrevious() },
        onClickNext = { onClickNext() },
        onDismiss = { onDismiss() }
    )
}

@Composable
private fun GhostPopupContent(
    modifier: Modifier = Modifier,
    ghostPopupRecord: GhostPopupRecord,
    onClickPrevious: () -> Unit,
    onClickNext: () -> Unit,
    onDismiss: () -> Unit = {}
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val image = ghostPopupRecord.icon.toDrawableResource()
    val title: AnnotatedString = AnnotatedString.Companion.fromHtml(
        stringResource(ghostPopupRecord.name.toStringResource()))
    val description = AnnotatedString.Companion.fromHtml(
        stringResource(ghostPopupRecord.info.toStringResource()))
    val strengths = AnnotatedString.Companion.fromHtml(
        stringResource(ghostPopupRecord.strengthData.toStringResource()))
    val weakness = AnnotatedString.Companion.fromHtml(
        stringResource(ghostPopupRecord.weaknessData.toStringResource()))
    val huntInfo = AnnotatedString.Companion.fromHtml(
        stringResource(ghostPopupRecord.huntData.toStringResource()))

    val primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            Image(
                modifier = modifier
                    .border(
                        width = 2.dp,
                        shape = RectangleShape,
                        color = LocalPalette.current.codexFamily.codex3
                    ),
                painter = painterResource(id = image),
                contentDescription = "Primary Icon",
                contentScale = ContentScale.Fit
            )
        }

    val primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            /*PopupDataRow(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .heightIn(max = 32.dp),
                icon = R.drawable.ic_shop_cost,
                data = "$buyCost"
            )*/
        }

    val primaryTitleContent: @Composable (RowScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
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
                text = title
            )
        }

    val descriptionBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    text = stringResource(R.string.popup_ghost_info).uppercase(),
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Center
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    fontSize = 24.sp
                )

                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(4.dp),
                    text = description,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    fontSize = 20.sp
                )
            }
        }

    val strengthBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    text = stringResource(R.string.popup_ghost_strength).uppercase(),
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Center
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    fontSize = 24.sp
                )

                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(4.dp),
                    text = strengths,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    fontSize = 20.sp
                )

            }
        }

    val weaknessBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    text = stringResource(R.string.popup_ghost_weakness).uppercase(),
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Center
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    fontSize = 24.sp
                )

                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(4.dp),
                    text = weakness,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    fontSize = 20.sp
                )

            }
        }

    val huntBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 8.dp),
                    text = stringResource(R.string.popup_ghost_huntdata).uppercase(),
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Center
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    fontSize = 24.sp
                )

                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(4.dp),
                    text = huntInfo,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    fontSize = 20.sp
                )

            }
        }

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            GhostTypePortraitPopup(
                modifier = modifier
                    .fillMaxWidth(),
                primaryTitleContent,
                primaryImageContent,
                primaryDataContent,
                descriptionBodyContent,
                strengthBodyContent,
                weaknessBodyContent,
                huntBodyContent,
                onClickPrevious = { onClickPrevious() },
                onClickNext = { onClickNext() },
                onDismiss = onDismiss
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            GhostTypeLandscapePopup(
                modifier = modifier
                    .fillMaxWidth(),
                primaryTitleContent,
                primaryImageContent,
                primaryDataContent,
                descriptionBodyContent,
                strengthBodyContent,
                weaknessBodyContent,
                huntBodyContent,
                onClickPrevious = { onClickPrevious() },
                onClickNext = { onClickNext() },
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
fun GhostTypePortraitPopup(
    modifier: Modifier = Modifier,
    primaryTitleContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
    primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit)? = null,
    primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    descriptionBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    strengthBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    weaknessBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    huntInfoBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    onClickPrevious: () -> Unit,
    onClickNext: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(top = 8.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 4.dp,
                    topEnd = 4.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
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

            /*Box(
                modifier = Modifier
                    .width(36.dp)
                    .height(96.dp)
                    .fillMaxHeight()
                    .clickable(onClick = { onClickPrevious() })
                    .background(LocalPalette.current.codexFamily.codex3)
                    .padding(4.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .align(Alignment.Center),
                    painter = painterResource(R.drawable.ic_arrow_fill_left),
                    contentDescription = "Previous Button",
                    colorFilter = ColorFilter.tint(LocalPalette.current.codexFamily.codex2)
                )
            }*/

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
                        .weight(1f)
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

            /*Box(
                modifier = Modifier
                    .width(36.dp)
                    //.height(96.dp)
                    .fillMaxHeight()
                    .clickable(onClick = { onClickPrevious() })
                    .background(LocalPalette.current.codexFamily.codex3)
                    .padding(4.dp)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .align(Alignment.Center),
                    painter = painterResource(R.drawable.ic_arrow_fill_right),
                    contentDescription = "Previous Button",
                    colorFilter = ColorFilter.tint(LocalPalette.current.codexFamily.codex2)
                )
            }*/

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

            var evidencePage by remember{ mutableStateOf(GhostPage.DESCRIPTION ) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    /*.border(
                        width = 1.dp,
                        shape = RectangleShape,
                        color = LocalPalette.current.codexFamily.codex3
                    )*/,
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PageButton(
                    isSelected = evidencePage == GhostPage.DESCRIPTION,
                    icon = { modifier, colors ->
                        Icon(
                            modifier = modifier
                                .padding(4.dp),
                            painter = painterResource(
                                R.drawable.ic_ghost
                            ),
                            contentDescription = "",
                            tint = colors.strokeColor,
                        )
                    }
                ) { evidencePage = GhostPage.DESCRIPTION }

                PageButton(
                    isSelected = evidencePage == GhostPage.STRENGTH,
                    icon = { modifier, colors ->
                        Icon(
                            modifier = modifier
                                .padding(4.dp),
                            painter = painterResource(
                                R.drawable.ic_thumb_up
                            ),
                            contentDescription = "",
                            tint = colors.strokeColor,
                        )
                    }
                ) { evidencePage = GhostPage.STRENGTH }

                PageButton(
                    isSelected = evidencePage == GhostPage.WEAKNESS,
                    icon = { modifier, colors ->
                        Icon(
                            modifier = modifier
                                .padding(4.dp),
                            painter = painterResource(
                                R.drawable.ic_thumb_down
                            ),
                            contentDescription = "",
                            tint = colors.strokeColor,
                        )
                    }
                ) { evidencePage = GhostPage.WEAKNESS }

                PageButton(
                    isSelected = evidencePage == GhostPage.HUNT,
                    icon = { modifier, colors ->
                        Icon(
                            modifier = modifier
                                .padding(2.dp),
                            painter = painterResource(
                                R.drawable.ic_intelligence
                            ),
                            contentDescription = "",
                            tint = colors.strokeColor,
                        )
                    }
                ) { evidencePage = GhostPage.HUNT }

            }

            when(evidencePage) {
                GhostPage.DESCRIPTION -> {
                    descriptionBodyContent?.let { content ->
                        content(
                            Modifier
                                .weight(1f, true)
                                .fillMaxWidth()
                        )
                    }
                }
                GhostPage.STRENGTH -> {
                    strengthBodyContent?.let { content ->
                        content(
                            Modifier
                                .weight(1f, true)
                                .fillMaxWidth()
                        )
                    }
                }
                GhostPage.WEAKNESS -> {
                    weaknessBodyContent?.let { content ->
                        content(
                            Modifier
                                .weight(1f, true)
                                .fillMaxWidth()
                        )
                    }
                }
                GhostPage.HUNT -> {
                    huntInfoBodyContent?.let { content ->
                        content(
                            Modifier
                                .weight(1f, true)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun GhostTypeLandscapePopup(
    modifier: Modifier = Modifier,
    primaryTitleContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
    primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit)? = null,
    primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    descriptionBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    strengthBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    weaknessBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    huntInfoBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    onClickPrevious: () -> Unit,
    onClickNext: () -> Unit,
    onDismiss: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .padding(end = 8.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 4.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 4.dp
                )
            )
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

        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    color = LocalPalette.current.codexFamily.codex3
                )
        ){
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


            var evidencePage by remember{ mutableStateOf(GhostPage.DESCRIPTION ) }

            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        shape = RectangleShape,
                        color = LocalPalette.current.codexFamily.codex3
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PageButton(
                        isSelected = evidencePage == GhostPage.DESCRIPTION,
                        icon = { modifier, colors ->
                            Icon(
                                modifier = modifier
                                    .padding(4.dp),
                                painter = painterResource(
                                    R.drawable.ic_ghost
                                ),
                                contentDescription = "",
                                tint = colors.strokeColor,
                            )
                        }
                    ) { evidencePage = GhostPage.DESCRIPTION }

                    PageButton(
                        isSelected = evidencePage == GhostPage.STRENGTH,
                        icon = { modifier, colors ->
                            Icon(
                                modifier = modifier
                                    .padding(4.dp),
                                painter = painterResource(
                                    R.drawable.ic_thumb_up
                                ),
                                contentDescription = "",
                                tint = colors.strokeColor,
                            )
                        }
                    ) { evidencePage = GhostPage.STRENGTH }

                    PageButton(
                        isSelected = evidencePage == GhostPage.WEAKNESS,
                        icon = { modifier, colors ->
                            Icon(
                                modifier = modifier
                                    .padding(4.dp),
                                painter = painterResource(
                                    R.drawable.ic_thumb_down
                                ),
                                contentDescription = "",
                                tint = colors.strokeColor,
                            )
                        }
                    ) { evidencePage = GhostPage.WEAKNESS }

                    PageButton(
                        isSelected = evidencePage == GhostPage.HUNT,
                        icon = { modifier, colors ->
                            Icon(
                                modifier = modifier
                                    .padding(2.dp),
                                painter = painterResource(
                                    R.drawable.ic_intelligence
                                ),
                                contentDescription = "",
                                tint = colors.strokeColor,
                            )
                        }
                    ) { evidencePage = GhostPage.HUNT }

                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()/*
                        .border(
                            width = 1.dp,
                            shape = RectangleShape,
                            color = LocalPalette.current.codexFamily.codex3
                        )*/
                        .padding(2.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    when(evidencePage) {
                        GhostPage.DESCRIPTION -> {
                            descriptionBodyContent?.let { content ->
                                content(
                                    Modifier
                                        .weight(1f, true)
                                        .fillMaxWidth()
                                )
                            }
                        }
                        GhostPage.STRENGTH -> {
                            strengthBodyContent?.let { content ->
                                content(
                                    Modifier
                                        .weight(1f, true)
                                        .fillMaxWidth()
                                )
                            }
                        }
                        GhostPage.WEAKNESS -> {
                            weaknessBodyContent?.let { content ->
                                content(
                                    Modifier
                                        .weight(1f, true)
                                        .fillMaxWidth()
                                )
                            }
                        }
                        GhostPage.HUNT -> {
                            huntInfoBodyContent?.let { content ->
                                content(
                                    Modifier
                                        .weight(1f, true)
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}
