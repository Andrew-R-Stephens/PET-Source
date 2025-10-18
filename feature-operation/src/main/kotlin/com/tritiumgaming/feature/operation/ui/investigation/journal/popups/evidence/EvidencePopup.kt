package com.tritiumgaming.feature.operation.ui.investigation.journal.popups.evidence

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.GridIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.palette.Whiteboard
import com.tritiumgaming.core.ui.theme.type.CleanTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.operation.app.mappers.codex.toDrawableResource
import com.tritiumgaming.feature.operation.app.mappers.codex.toIntegerResource
import com.tritiumgaming.feature.operation.app.mappers.evidence.toDrawableResource
import com.tritiumgaming.feature.operation.app.mappers.evidence.toStringResource
import com.tritiumgaming.feature.operation.ui.investigation.journal.popups.InvestigationPopupUiState
import com.tritiumgaming.feature.operation.ui.investigation.journal.popups.common.InvestigationPopup
import com.tritiumgaming.shared.operation.domain.codex.mappers.EquipmentResources
import com.tritiumgaming.shared.operation.domain.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.operation.domain.popup.model.EvidencePopupRecord
import kotlinx.coroutines.flow.StateFlow

@Composable
fun EvidencePopup(
    modifier: Modifier,
    state: StateFlow<InvestigationPopupUiState>,
    onDismiss: () -> Unit
) {
    val recordState by state.collectAsStateWithLifecycle()

    InvestigationPopup(
        modifier = modifier,
        shown = recordState.isShown,
        {
            recordState.evidencePopupRecord?.let { record ->
                EvidencePopupContent(
                    modifier = modifier,
                    evidencePopupRecord = record,
                    onDismiss = { onDismiss() }
                )
            }
        },
    )

}

@Composable
private fun EvidencePopupContent(
    modifier: Modifier = Modifier,
    evidencePopupRecord: EvidencePopupRecord,
    onDismiss: () -> Unit = {}
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val image = evidencePopupRecord.icon.toDrawableResource()
    val evidenceTitle: AnnotatedString = AnnotatedString.Companion.fromHtml(
        stringResource(evidencePopupRecord.name.toStringResource()))
    val evidenceDescription = AnnotatedString.Companion.fromHtml(
        stringResource(evidencePopupRecord.description.toStringResource()))

    val buyCost = integerResource(evidencePopupRecord.equipmentType.buyCostData.toIntegerResource())

    val primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            GridIcon(
                modifier = modifier,
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.codexFamily.codex6_gridBackground,
                    strokeColor = LocalPalette.current.codexFamily.codex7_gridStroke
                ),
                contentScale = ContentScale.Fit
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
                painter = painterResource(id = image),
                contentDescription = "Primary Icon",
                contentScale = ContentScale.Fit
            )
        }

    val primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            PopupDataRow(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .heightIn(max = 32.dp),
                icon = R.drawable.ic_shop_cost,
                data = "$buyCost"
            )
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
                color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
                maxLines = 1,
                fontSize = 20.sp,
                text = evidenceTitle
            )
        }

    val bodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
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
                        .padding(4.dp),
                    text = evidenceDescription,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
                    fontSize = 20.sp
                )
            }
        }

    val equipmentTypeIcon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit =
        @Composable { modifier, colors ->
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                painter = painterResource(
                    evidencePopupRecord.equipmentType.icon.toDrawableResource()),
                contentDescription = "",
                tint = colors.strokeColor,
            )
        }

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            EvidenceTypePortraitPopup(
                modifier = modifier
                    .fillMaxWidth(),
                primaryTitleContent,
                primaryImageContent,
                primaryDataContent,
                bodyContent,
                equipmentTypeIcon = { modifier, colors ->
                    equipmentTypeIcon(modifier, colors) },
                onDismiss = onDismiss
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            EvidenceTypeLandscapePopup(
                modifier = modifier
                    .fillMaxSize(),
                primaryTitleContent,
                primaryImageContent,
                primaryDataContent,
                bodyContent,
                equipmentTypeIcon = { modifier, colors ->
                    equipmentTypeIcon(modifier, colors) },
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
fun EvidenceTypePortraitPopup(
    modifier: Modifier = Modifier,
    primaryTitleContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
    primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit)? = null,
    primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    bodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    equipmentTypeIcon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit,
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
            .background(LocalPalette.current.codexFamily.codex4_border)
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
                    color = LocalPalette.current.codexFamily.codex3_itemBorder
                )
                .background(LocalPalette.current.codexFamily.codex3_popupCloseBackground),
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
                    .background(LocalPalette.current.codexFamily.codex3_popupCloseBackground)
                    .clickable { onDismiss() },
                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                contentDescription = "Close Button",
                colorFilter = ColorFilter.tint(
                    color = LocalPalette.current.codexFamily.codex2_popupCloseIcon,
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
                    color = LocalPalette.current.codexFamily.codex3_itemBorder
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

        var evidencePage by remember{ mutableStateOf(EvidenceTypePage.EVIDENCE) }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    color = LocalPalette.current.codexFamily.codex3_itemBorder
                ),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EvidencePageButton(
                isSelected = evidencePage == EvidenceTypePage.EVIDENCE,
                icon = { modifier, colors ->
                    Icon(
                        modifier = modifier,
                        painter = painterResource(
                            R.drawable.ic_mystery
                        ),
                        contentDescription = "",
                        tint = colors.strokeColor,
                    )
                }
            ) { evidencePage = EvidenceTypePage.EVIDENCE }

            EvidencePageButton(
                isSelected = evidencePage == EvidenceTypePage.EQUIPMENT,
                icon = { modifier, colors ->
                    equipmentTypeIcon(modifier, colors) }
            ) { evidencePage = EvidenceTypePage.EQUIPMENT }

        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    color = LocalPalette.current.codexFamily.codex3_itemBorder
                )
                .padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            when(evidencePage) {
                EvidenceTypePage.EVIDENCE -> {
                    bodyContent?.let { content ->
                        content(
                            Modifier
                                .weight(1f, true)
                                .fillMaxWidth()
                        )
                    }
                }
                EvidenceTypePage.EQUIPMENT -> {

                }
            }
        }


    }
}

@Composable
fun EvidenceTypeLandscapePopup(
    modifier: Modifier = Modifier,
    primaryTitleContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
    primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit)? = null,
    primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    bodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    equipmentTypeIcon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit,
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
            .background(LocalPalette.current.codexFamily.codex4_border)
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
                    color = LocalPalette.current.codexFamily.codex3_itemBorder
                )
                .background(LocalPalette.current.codexFamily.codex3_popupCloseBackground),
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
                    .background(LocalPalette.current.codexFamily.codex3_popupCloseBackground)
                    .clickable { onDismiss() },
                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                contentDescription = "Close Button",
                colorFilter = ColorFilter.tint(
                    color = LocalPalette.current.codexFamily.codex2_popupCloseIcon,
                    blendMode = BlendMode.SrcIn
                ),
                contentScale = ContentScale.Fit
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
                        color = LocalPalette.current.codexFamily.codex3_itemBorder
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
                        color = LocalPalette.current.codexFamily.codex3_itemBorder
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

            }
        }
    }
}

@Composable
fun PopupDataRow(
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
                color = LocalPalette.current.codexFamily.codex3_popupAttrIcons,
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
            color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
            maxLines = 1,
            autoSize = TextAutoSize.StepBased(minFontSize = 1.sp)
        )

    }
}

@Composable fun EvidencePageButton(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit = {}
) {
    if(isSelected) {
        EvidencePageButtonSelected(text) { onClick() }
    } else {
        EvidencePageButtonUnselected(text) { onClick() }
    }
}

@Composable fun EvidencePageButton(
    isSelected: Boolean,
    icon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit,
    onClick: () -> Unit = {}
) {
    if(isSelected) {
        EvidencePageButtonSelected(icon) { onClick() }
    } else {
        EvidencePageButtonUnselected(icon) { onClick() }
    }
}

@Composable
private fun EvidencePageButtonUnselected(
    text: String,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = Modifier
            .height(48.dp)
            .padding(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        }
    ) {
        Text(
            text = text,
            style = LocalTypography.current.quaternary.bold.copy(
                textAlign = TextAlign.Center,
            ),
            color = LocalPalette.current.codexFamily.codex3
        )
    }
}

@Composable
private fun EvidencePageButtonSelected(
    text: String,
    onClick: () -> Unit = {}
) {
    TextButton(
        modifier = Modifier
            .height(48.dp)
            .padding(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.textButtonColors(
            containerColor = LocalPalette.current.codexFamily.codex4_border
        )
    ) {
        Text(
            text = text,
            style = LocalTypography.current.quaternary.bold.copy(
                textAlign = TextAlign.Center,
            ),
            color = LocalPalette.current.codexFamily.codex2
        )
    }

}

@Composable
private fun EvidencePageButtonUnselected(
    icon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit,
    onClick: () -> Unit = {}
) {
    OutlinedIconButton(
        modifier = Modifier
            .size(48.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        },
        border = BorderStroke(
            width = 2.dp,
            color = LocalPalette.current.codexFamily.codex3_itemBorder
        )
    ) {
        icon(
            Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(2.dp),
            IconVectorColors(
                fillColor = LocalPalette.current.codexFamily.codex3_itemBorder,
                strokeColor = LocalPalette.current.codexFamily.codex3_itemBorder
            )
        )
    }
}

@Composable
private fun EvidencePageButtonSelected(
    icon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit,
    onClick: () -> Unit = {}
) {
    IconButton(
        modifier = Modifier
            .size(48.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        },
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = LocalPalette.current.codexFamily.codex4_border
        )
    ) {
        icon(
            Modifier
                .fillMaxSize()
                .aspectRatio(1f)
                .padding(2.dp),
            IconVectorColors(
                fillColor = LocalPalette.current.codexFamily.codex2,
                strokeColor = LocalPalette.current.codexFamily.codex2
            )
        )
    }
}

@Preview
@Composable
fun EvidenceTypePortraitPreview() {

    val image = EvidenceResources.EvidenceIcon.DOTS.toDrawableResource()
    val evidenceTitle: AnnotatedString = AnnotatedString.Companion.fromHtml(
        stringResource(EvidenceResources.EvidenceTitle.DOTS.toStringResource()))
    val evidenceDescription = AnnotatedString.Companion.fromHtml(
        stringResource(EvidenceResources.EvidenceDescription.DOTS.toStringResource()))
    val equipmentTypeImage = EquipmentResources.EquipmentIcon.DOTS.toDrawableResource()

    val buyCost = integerResource(EquipmentResources.EquipmentBuyCost.DOTS.toIntegerResource())

    val primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            GridIcon(
                modifier = modifier,
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.codexFamily.codex6_gridBackground,
                    strokeColor = LocalPalette.current.codexFamily.codex7_gridStroke
                ),
                contentScale = ContentScale.Fit
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
                painter = painterResource(id = image),
                contentDescription = "Primary Icon",
                contentScale = ContentScale.Fit
            )
        }

    val primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            PopupDataRow(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .heightIn(max = 32.dp),
                icon = R.drawable.ic_shop_cost,
                data = "$buyCost"
            )
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
                color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
                maxLines = 1,
                fontSize = 20.sp,
                text = evidenceTitle
            )
        }

    val bodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
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
                        .padding(4.dp),
                    text = evidenceDescription,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
                    fontSize = 20.sp
                )
            }
        }

    val equipmentTypeIcon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit =
        @Composable { modifier, colors ->
            Icon(
                modifier = modifier
                    .fillMaxSize()
                    .padding(4.dp),
                painter = painterResource(equipmentTypeImage),
                contentDescription = "",
                tint = colors.strokeColor,
            )
        }

    SelectiveTheme(
        palette = Whiteboard,
        typography = CleanTypography
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            EvidenceTypePortraitPopup(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 0.dp)
                    .align(Alignment.BottomCenter),
                primaryTitleContent = primaryTitleContent,
                primaryImageContent = primaryImageContent,
                primaryDataContent = primaryDataContent,
                bodyContent = bodyContent,
                equipmentTypeIcon = { modifier, colors -> equipmentTypeIcon(modifier, colors) },
                onDismiss = {}
            )
        }
    }
}

@Preview(device = "spec:width=411dp,height=891dp,orientation=landscape")
@Composable
fun EvidenceTypePopupLandscapePreview() {

    val image = EvidenceResources.EvidenceIcon.DOTS.toDrawableResource()
    val evidenceTitle: AnnotatedString = AnnotatedString.Companion.fromHtml(
        stringResource(EvidenceResources.EvidenceTitle.DOTS.toStringResource()))
    val evidenceDescription = AnnotatedString.Companion.fromHtml(
        stringResource(EvidenceResources.EvidenceDescription.DOTS.toStringResource()))
    val equipmentTypeImage = EquipmentResources.EquipmentIcon.DOTS.toDrawableResource()

    val buyCost = integerResource(EquipmentResources.EquipmentBuyCost.DOTS.toIntegerResource())

    val primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            GridIcon(
                modifier = modifier,
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.codexFamily.codex6_gridBackground,
                    strokeColor = LocalPalette.current.codexFamily.codex7_gridStroke
                ),
                contentScale = ContentScale.Fit
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
                painter = painterResource(id = image),
                contentDescription = "Primary Icon",
                contentScale = ContentScale.Fit
            )
        }

    val primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            PopupDataRow(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .heightIn(max = 32.dp),
                icon = R.drawable.ic_shop_cost,
                data = "$buyCost"
            )

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
                color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
                maxLines = 1,
                fontSize = 20.sp,
                text = evidenceTitle
            )
        }

    val bodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
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
                    text = evidenceDescription,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
                    fontSize = 20.sp
                )

            }

        }

    val equipmentTypeIcon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit =
        @Composable { modifier, colors ->
            Icon(
                modifier = modifier
                    .fillMaxSize()
                    .padding(4.dp),
                painter = painterResource(equipmentTypeImage),
                contentDescription = "",
                tint = colors.strokeColor,
            )
        }

    SelectiveTheme(
        palette = Whiteboard,
        typography = CleanTypography
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            EvidenceTypeLandscapePopup(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, end = 16.dp, bottom = 16.dp)
                    .align(Alignment.CenterStart),
                primaryTitleContent = { modifier -> primaryTitleContent(modifier) },
                primaryImageContent = { modifier -> primaryImageContent(modifier) },
                primaryDataContent = { modifier -> primaryDataContent(modifier) },
                bodyContent = { modifier -> bodyContent(modifier) },
                equipmentTypeIcon = { modifier, colors ->
                    equipmentTypeIcon(modifier, colors) },
                onDismiss = {}
            )
        }
    }
}
