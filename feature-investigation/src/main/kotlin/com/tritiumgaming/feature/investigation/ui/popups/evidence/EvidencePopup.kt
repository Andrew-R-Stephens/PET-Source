package com.tritiumgaming.feature.investigation.ui.popups.evidence

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.GridIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.app.mappers.codex.toDrawableResource
import com.tritiumgaming.feature.investigation.app.mappers.codex.toIntegerResource
import com.tritiumgaming.feature.investigation.app.mappers.codex.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toDrawableResource
import com.tritiumgaming.feature.investigation.app.mappers.evidence.toStringResource
import com.tritiumgaming.feature.investigation.ui.popups.common.AnimatedGif
import com.tritiumgaming.feature.investigation.ui.popups.common.InvestigationPopup
import com.tritiumgaming.feature.investigation.ui.popups.common.PageButton
import com.tritiumgaming.feature.investigation.ui.popups.common.PopupDataRow
import com.tritiumgaming.shared.data.codex.mappers.EquipmentResources
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.data.popup.model.EvidencePopupRecord

@Composable
fun EvidencePopup(
    modifier: Modifier,
    record: EvidencePopupRecord,
    onDismiss: () -> Unit
) {
    EvidencePopupContent(
        modifier = modifier,
        evidencePopupRecord = record,
        onDismiss = { onDismiss() }
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
    val evidenceTitle: AnnotatedString = AnnotatedString.fromHtml(
        stringResource(evidencePopupRecord.name.toStringResource()))
    val evidenceDescription = AnnotatedString.fromHtml(
        stringResource(evidencePopupRecord.description.toStringResource()))

    val buyCost = integerResource(evidencePopupRecord.equipmentType.buyCostData.toIntegerResource())

    val primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            GridIcon(
                modifier = modifier,
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.codexFamily.codex6,
                    strokeColor = LocalPalette.current.codexFamily.codex7
                ),
                contentScale = ContentScale.Fit
            )
            Image(
                modifier = modifier
                    .padding(8.dp),
                painter = painterResource(id = image),
                contentDescription = "Primary Icon",
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = LocalPalette.current.codexFamily.codex3
                )
            )
        }

    val primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            PopupDataRow(
                modifier = modifier
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
                color = LocalPalette.current.codexFamily.codex3,
                maxLines = 1,
                fontSize = 20.sp,
                text = evidenceTitle
            )
        }

    val evidenceBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
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
                    text = stringResource(R.string.evidence_section_overview).uppercase(),
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
                    text = evidenceDescription,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    fontSize = 20.sp
                )

                AnimatedGif(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    animatedGifRes = evidencePopupRecord.animation.toDrawableResource()
                )
            }
        }

    val equipmentBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->

            var rememberEquipmentTier by remember {
                mutableStateOf(EvidenceTierPage.TIER_1) }

            val equipmentTier = evidencePopupRecord.equipmentType.items
                .getOrNull(rememberEquipmentTier.ordinal)

            val equipmentTierAnimation = evidencePopupRecord.equipmentTierAnimations
                .getOrNull(rememberEquipmentTier.ordinal)?.animation

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
                    isSelected = rememberEquipmentTier == EvidenceTierPage.TIER_1,
                    icon = { modifier, colors ->
                        Icon(
                            modifier = modifier
                                .padding(4.dp),
                            painter = painterResource(
                                R.drawable.ic_tier_1
                            ),
                            contentDescription = "",
                            tint = colors.strokeColor,
                        )
                    }
                ) { rememberEquipmentTier = EvidenceTierPage.TIER_1 }

                PageButton(
                    isSelected = rememberEquipmentTier == EvidenceTierPage.TIER_2,
                    icon = { modifier, colors ->
                        Icon(
                            modifier = modifier
                                .padding(4.dp),
                            painter = painterResource(
                                R.drawable.ic_tier_2
                            ),
                            contentDescription = "",
                            tint = colors.strokeColor,
                        )
                    }
                ) { rememberEquipmentTier = EvidenceTierPage.TIER_2 }

                PageButton(
                    isSelected = rememberEquipmentTier == EvidenceTierPage.TIER_3,
                    icon = { modifier, colors ->
                        Icon(
                            modifier = modifier
                                .padding(4.dp),
                            painter = painterResource(
                                R.drawable.ic_tier_3
                            ),
                            contentDescription = "",
                            tint = colors.strokeColor,
                        )
                    }
                ) { rememberEquipmentTier = EvidenceTierPage.TIER_3 }

            }

            equipmentTier?.let { tier ->

                val equipmentFlavor: AnnotatedString = AnnotatedString.fromHtml(
                    stringResource(tier.flavor.toStringResource()))

                val equipmentInfo: AnnotatedString = AnnotatedString.fromHtml(
                    stringResource(tier.info.toStringResource()))

                Column(
                    modifier = modifier
                        .fillMaxHeight()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {

                        Text(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 8.dp),
                            text = stringResource(rememberEquipmentTier.label).uppercase(),
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Center
                            ),
                            color = LocalPalette.current.codexFamily.codex3,
                            fontSize = 24.sp
                        )

                        Text(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(bottom = 8.dp, start = 4.dp, end = 4.dp, top = 4.dp),
                            text = equipmentFlavor,
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Start
                            ),
                            color = LocalPalette.current.codexFamily.codex3,
                            fontSize = 20.sp
                        )

                        HorizontalDivider(
                            modifier = Modifier
                                .height(2.dp),
                            color = LocalPalette.current.codexFamily.codex3.copy(
                                alpha = .5f
                            )
                        )

                        Text(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(top = 8.dp, start = 4.dp, end = 4.dp, bottom = 4.dp),
                            text = equipmentInfo,
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Start
                            ),
                            color = LocalPalette.current.codexFamily.codex3,
                            fontSize = 20.sp
                        )
                    }

                    equipmentTierAnimation?.let { animationRes ->
                        AnimatedGif(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            animatedGifRes = animationRes.toDrawableResource()
                        )
                    }
                }

            }

        }

    val equipmentTypeIcon: @Composable (modifier: Modifier, colors: IconVectorColors) -> Unit =
        @Composable { modifier, colors ->
            Icon(
                modifier = modifier
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
                evidenceBodyContent,
                equipmentBodyContent,
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
                evidenceBodyContent,
                equipmentBodyContent,
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
    evidenceBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    equipmentBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
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
                    color = LocalPalette.current.codexFamily.codex3
                ),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageButton(
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

            PageButton(
                isSelected = evidencePage == EvidenceTypePage.EQUIPMENT,
                icon = { modifier, colors ->
                    equipmentTypeIcon(modifier, colors)
                }
            ) { evidencePage = EvidenceTypePage.EQUIPMENT }

        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    shape = RectangleShape,
                    color = LocalPalette.current.codexFamily.codex3
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            when(evidencePage) {
                EvidenceTypePage.EVIDENCE -> {
                    evidenceBodyContent?.let { content ->
                        content(
                            Modifier
                                .padding(2.dp)
                                .weight(1f, true)
                                .fillMaxWidth()
                        )
                    }
                }
                EvidenceTypePage.EQUIPMENT -> {
                    equipmentBodyContent?.let { content ->
                        content(
                            Modifier
                                .padding(2.dp)
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
fun EvidenceTypeLandscapePopup(
    modifier: Modifier = Modifier,
    primaryTitleContent: @Composable (RowScope.(modifier: Modifier) -> Unit)? = null,
    primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit)? = null,
    primaryDataContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    evidenceBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
    equipmentBodyContent: @Composable (ColumnScope.(modifier: Modifier) -> Unit)? = null,
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

                evidenceBodyContent?.let { content ->
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

@Preview
@Composable
fun EvidenceTypePortraitPreview() {

    val image = EvidenceResources.EvidenceIcon.DOTS.toDrawableResource()
    val evidenceTitle: AnnotatedString = AnnotatedString.fromHtml(
        stringResource(EvidenceResources.EvidenceTitle.DOTS.toStringResource()))
    val evidenceDescription = AnnotatedString.fromHtml(
        stringResource(EvidenceResources.EvidenceDescription.DOTS.toStringResource()))
    val equipmentTypeImage = EquipmentResources.EquipmentIcon.DOTS.toDrawableResource()

    val buyCost = integerResource(EquipmentResources.EquipmentBuyCost.DOTS.toIntegerResource())

    val primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            GridIcon(
                modifier = modifier,
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.codexFamily.codex6,
                    strokeColor = LocalPalette.current.codexFamily.codex7
                ),
                contentScale = ContentScale.Fit
            )
            /*Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.itemstore_grid),
                contentDescription = "Primary Icon",
                contentScale = ContentScale.Fit
            )*/
            Icon(
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp),
                painter = painterResource(id = image),
                contentDescription = "Primary Icon",
                tint = LocalPalette.current.codexFamily.codex3
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
                color = LocalPalette.current.codexFamily.codex3,
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
                    color = LocalPalette.current.codexFamily.codex3,
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

        SelectiveTheme {
            InvestigationPopup(
                modifier = Modifier
                    .fillMaxSize(),
                shown = true,
                backgroundColor = LocalPalette.current.surfaceContainer
            ) {
                EvidenceTypePortraitPopup(
                    modifier = Modifier
                        .fillMaxSize(),
                    primaryTitleContent = primaryTitleContent,
                    primaryImageContent = primaryImageContent,
                    primaryDataContent = primaryDataContent,
                    evidenceBodyContent = bodyContent,
                    equipmentTypeIcon = { modifier, colors -> equipmentTypeIcon(modifier, colors) },
                    onDismiss = {}
                )
            }
        }
    }

@Preview(device = "spec:width=411dp,height=891dp,orientation=landscape")
@Composable
fun EvidenceTypePopupLandscapePreview() {

    val evidenceImage = EvidenceResources.EvidenceIcon.DOTS.toDrawableResource()
    val evidenceTitle: AnnotatedString = AnnotatedString.fromHtml(
        stringResource(EvidenceResources.EvidenceTitle.DOTS.toStringResource()))
    val evidenceDescription = AnnotatedString.fromHtml(
        stringResource(EvidenceResources.EvidenceDescription.DOTS.toStringResource()))
    val equipmentTypeImage = EquipmentResources.EquipmentIcon.DOTS.toDrawableResource()

    val buyCost = integerResource(EquipmentResources.EquipmentBuyCost.DOTS.toIntegerResource())

    val primaryImageContent: @Composable (BoxScope.(modifier: Modifier) -> Unit) =
        @Composable { modifier ->
            GridIcon(
                modifier = modifier,
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.codexFamily.codex6,
                    strokeColor = LocalPalette.current.codexFamily.codex7
                ),
                contentScale = ContentScale.Fit
            )
            /*Image(
                modifier = modifier,
                painter = painterResource(id = R.drawable.itemstore_grid),
                contentDescription = "Primary Icon",
                contentScale = ContentScale.Fit
            )*/
            Icon(
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp),
                painter = painterResource(id = evidenceImage),
                contentDescription = "Primary Icon",
                tint = LocalPalette.current.codexFamily.codex3
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
                color = LocalPalette.current.codexFamily.codex3,
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
                    color = LocalPalette.current.codexFamily.codex3,
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


    SelectiveTheme {
        InvestigationPopup(
            modifier = Modifier
                .fillMaxSize(),
            shown = true,
            backgroundColor = LocalPalette.current.surfaceContainer
        ) {
            EvidenceTypeLandscapePopup(
                modifier = Modifier
                    .fillMaxSize(),
                primaryTitleContent = primaryTitleContent,
                primaryImageContent = primaryImageContent,
                primaryDataContent = primaryDataContent,
                evidenceBodyContent = bodyContent,
                equipmentTypeIcon = { modifier, colors ->
                    equipmentTypeIcon(modifier, colors) },
                onDismiss = {}
            )
        }
    }

}
