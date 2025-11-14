package com.tritiumstudios.feature.codex.ui.catalog.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.GridIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumstudios.feature.codex.app.mappers.codex.toDrawableResource
import com.tritiumstudios.feature.codex.app.mappers.codex.toIntegerResource
import com.tritiumstudios.feature.codex.app.mappers.codex.toStringResource
import com.tritiumstudios.feature.codex.ui.CodexViewModel
import com.tritiumstudios.feature.codex.ui.catalog.common.CodexGroup
import com.tritiumstudios.feature.codex.ui.catalog.common.CodexGroupItem
import com.tritiumstudios.feature.codex.ui.catalog.common.CodexGroupItemsLandscape
import com.tritiumstudios.feature.codex.ui.catalog.common.CodexGroupItemsPortrait
import com.tritiumstudios.feature.codex.ui.catalog.common.CodexItemPopup
import com.tritiumstudios.feature.codex.ui.catalog.common.CodexItemPopupDataRow

@Composable
fun CatalogEquipmentListComponent(
    codexViewModel: CodexViewModel,
    scrollState: LazyListState
) {
    val equipmentUiState = codexViewModel.equipmentUiState.collectAsStateWithLifecycle()
    val groups = equipmentUiState.value.list

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                state = scrollState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = groups
                ) { group ->
                    CodexGroup(
                        groupTitle = group.name.toStringResource()
                    ) {
                        CodexGroupItemsPortrait(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 48.dp, max = 96.dp),
                            itemCount = group.size
                        ) {
                            group.items.forEachIndexed { index, item ->
                                CodexGroupItem(
                                    modifier = Modifier
                                        .widthIn(min = 32.dp, max = 96.dp)
                                        .aspectRatio(1f)
                                        .weight(1f, false),
                                    isBackground = true,
                                    isBordered = true,
                                    tierLevel = index + 1,
                                    image = item.image.toDrawableResource()
                                ) {
                                    codexViewModel.setSelectedEquipment(group, item)
                                }
                            }
                        }
                    }
                }
            }

        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            LazyRow(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                state = scrollState,
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = groups
                ) { group ->
                    CodexGroup(
                        groupTitle = group.name.toStringResource()
                    ) {
                        CodexGroupItemsLandscape(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(96.dp),
                            itemCount = group.size
                        ) {
                            group.items.forEachIndexed { index, item ->
                                CodexGroupItem(
                                    modifier = Modifier
                                        .sizeIn(
                                            minWidth = 64.dp, maxWidth = 96.dp,
                                            minHeight = 64.dp, maxHeight = 96.dp
                                        )
                                        .aspectRatio(1f)/*
                                        .weight(1f, false)*/,
                                    isBackground = true,
                                    isBordered = true,
                                    tierLevel = index + 1,
                                    image = item.image.toDrawableResource()
                                ) {
                                    codexViewModel.setSelectedEquipment(group, item)
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
fun CodexEquipmentDisplay(
    modifier: Modifier = Modifier,
    codexViewModel: CodexViewModel
) {

    val equipmentUiState by codexViewModel.equipmentUiState.collectAsStateWithLifecycle()

    val selectedGroup = equipmentUiState.selectedGroup ?: return
    val selectedItem = equipmentUiState.selectedItem ?: return

    val image = selectedItem.image.toDrawableResource()

    val primaryTitle: AnnotatedString? = AnnotatedString.Companion.fromHtml(
        stringResource(selectedGroup.name.toStringResource()))

    val primaryText = AnnotatedString.Companion.fromHtml(
        stringResource(selectedItem.info.toStringResource()))

    val secondaryText = AnnotatedString.Companion.fromHtml(
        stringResource(selectedItem.flavor.toStringResource()))

    val positiveAttributes = selectedItem.positiveAttributes.map {
        stringResource(it.toStringResource()) }.joinToString { "$it " }
    val negativeAttributes = selectedItem.negativeAttributes.map {
        stringResource(it.toStringResource()) }.joinToString { "$it " }

    val footerText = AnnotatedString.Companion.fromHtml(
        "$positiveAttributes $negativeAttributes")

    val buyCost = integerResource(selectedGroup.buyCostData.toIntegerResource())
    val upgradeLevel = integerResource(selectedItem.upgradeLevelData.toIntegerResource())
    val upgradeCost = integerResource(selectedItem.upgradeCostData.toIntegerResource())

    CodexItemPopup(
        modifier = modifier,
        primaryTitleContent = { modifier ->
            primaryTitle?.let { title ->
                Text(
                    modifier = modifier
                        .basicMarquee(
                            iterations = Int.MAX_VALUE,
                            initialDelayMillis = 1000,
                            repeatDelayMillis = 1000,
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = title,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    maxLines = 1,
                    fontSize = 20.sp
                )
            }
        },
        primaryImageContent = { modifier ->
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
                contentScale = ContentScale.Fit
            )
        },
        primaryDataContent = { modifier ->

            CodexItemPopupDataRow(
                modifier = Modifier.Companion
                    .weight(1f)
                    .fillMaxWidth()
                    .heightIn(max = 32.dp),
                icon = R.drawable.ic_shop_cost,
                data = "$buyCost"
            )

            CodexItemPopupDataRow(
                modifier = Modifier.Companion
                    .weight(1f)
                    .fillMaxWidth()
                    .heightIn(max = 32.dp),
                icon = R.drawable.ic_shop_upgrade,
                data = "$upgradeLevel"
            )

            CodexItemPopupDataRow(
                modifier = Modifier.Companion
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

            if (footerText.isNotBlank()) {
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
            }
        },
        onDismiss = {
            codexViewModel.setSelectedEquipment(null, null)
        }
    )
}
