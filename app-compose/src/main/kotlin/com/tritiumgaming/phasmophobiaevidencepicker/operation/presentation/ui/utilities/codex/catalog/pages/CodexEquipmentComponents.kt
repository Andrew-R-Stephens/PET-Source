package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentBuyCost
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentIcon
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.EquipmentTitles
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierFlavorText
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierImage
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.TierInformation
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.UnlockLevel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexEquipmentResources.UpgradeCost
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.equipment.CodexEquipmentGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.equipment.CodexEquipmentGroupItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toIntegerResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.CodexViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexGroupItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexGroupItems
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexItemPopup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexItemPopupDataRow

@Composable
fun CodexEquipmentListComponent(
    codexViewModel: CodexViewModel
) {

    val equipmentUiState = codexViewModel.equipmentUiState.collectAsStateWithLifecycle()

    val groups = equipmentUiState.value.list

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, top = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(
            items = groups
        ) { group ->
            CodexGroup(
                groupTitle = group.name.toStringResource()
            ) {
                CodexGroupItems(
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

@Composable
fun CodexEquipmentDisplay(
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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        CodexItemPopup(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.7f)
                .align(Alignment.BottomCenter),
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
                        color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
                        maxLines = 1,
                        fontSize = 20.sp
                    )
                }
            },
            primaryImageContent = { modifier ->
                Image(
                    modifier = modifier,
                    painter = painterResource(id = R.drawable.itemstore_grid),
                    contentDescription = "Primary Icon",
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
                    icon = R.drawable.ic_shop_upgrade,
                    data = "$upgradeLevel"
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
                            .wrapContentHeight()
                            .padding(4.dp),
                        text = secondaryText,
                        style = LocalTypography.current.quaternary.bold.copy(
                            textAlign = TextAlign.Start
                        ),
                        color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
                        fontSize = 20.sp
                    )

                    HorizontalDivider(
                        modifier = Modifier
                            .height(2.dp),
                        color = LocalPalette.current.codexFamily.codex3_other
                    )

                    Text(
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(4.dp),
                        text = primaryText,
                        style = LocalTypography.current.quaternary.bold.copy(
                            textAlign = TextAlign.Start
                        ),
                        color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
                        fontSize = 18.sp
                    )

                }
            },
            textFooterContent = { modifier ->

                if(footerText.isNotBlank()) {
                    Column(
                        modifier = modifier,
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        HorizontalDivider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp),
                            color = LocalPalette.current.codexFamily.codex3_other
                        )

                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .horizontalScroll(rememberScrollState()),
                            text = footerText,
                            style = LocalTypography.current.quaternary.bold.copy(
                                textAlign = TextAlign.Start
                            ),
                            color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
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


}


@Preview
@Composable
private fun CodexEquipmentItemPreview() {

    SelectiveTheme {
        val groups = listOf(
            CodexEquipmentGroup(
                name = EquipmentTitles.DOTS,
                icon = EquipmentIcon.DOTS,
                buyCostData = EquipmentBuyCost.COST_65,
                items = listOf(
                    CodexEquipmentGroupItem(
                        image = TierImage.DOTS_1,
                        flavor = TierFlavorText.DOTS_1,
                        info = TierInformation.DOTS_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_NARROW,
                            EquipmentAttribute.RANGE_5,
                            EquipmentAttribute.HANDHELD
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItem(
                        image = TierImage.DOTS_2,
                        flavor = TierFlavorText.DOTS_2,
                        info = TierInformation.DOTS_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_29,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_AREA,
                            EquipmentAttribute.RANGE_2_5,
                            EquipmentAttribute.PLACEABLE
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItem(
                        image = TierImage.DOTS_3,
                        flavor = TierFlavorText.DOTS_3,
                        info = TierInformation.DOTS_3,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_60,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_WIDE,
                            EquipmentAttribute.RANGE_7,
                            EquipmentAttribute.PLACEABLE,
                            EquipmentAttribute.PROPERTY_SCANNING
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // EMF
            CodexEquipmentGroup(
                name = EquipmentTitles.EMF,
                icon = EquipmentIcon.EMF,
                buyCostData = EquipmentBuyCost.COST_45,
                items = listOf(
                    CodexEquipmentGroupItem(
                        image = TierImage.EMF_1,
                        flavor = TierFlavorText.EMF_1,
                        info = TierInformation.EMF_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_1_7,
                            EquipmentAttribute.ACCURACY_LOW
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItem(
                        image = TierImage.EMF_2,
                        flavor = TierFlavorText.EMF_2,
                        info = TierInformation.EMF_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_20,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_1_7,
                            EquipmentAttribute.ACCURACY_HIGH,
                            EquipmentAttribute.INDICATOR_AUDIO
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItem(
                        image = TierImage.EMF_3,
                        flavor = TierFlavorText.EMF_3,
                        info = TierInformation.EMF_3,
                        upgradeCostData = UpgradeCost.COST_4500,
                        upgradeLevelData = UnlockLevel.LEVEL_52,
                        positiveAttributes = listOf(
                            EquipmentAttribute.RANGE_3_5,
                            EquipmentAttribute.ACCURACY_HIGH,
                            EquipmentAttribute.PROPERTY_DISPLAY_SCREEN,
                            EquipmentAttribute.INDICATOR_AUDIO,
                            EquipmentAttribute.INDICATOR_DISTANCE,
                            EquipmentAttribute.INDICATOR_DIRECTIONAL
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
            // Flashlight
            CodexEquipmentGroup(
                name = EquipmentTitles.FLASHLIGHT,
                icon = EquipmentIcon.FLASHLIGHT,
                buyCostData = EquipmentBuyCost.COST_30,
                items = listOf(
                    CodexEquipmentGroupItem(
                        image = TierImage.FLASHLIGHT_1,
                        flavor = TierFlavorText.FLASHLIGHT_1,
                        info = TierInformation.FLASHLIGHT_1,
                        upgradeCostData = UpgradeCost.COST_0,
                        upgradeLevelData = UnlockLevel.LEVEL_0,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_NARROW
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.INTENSITY_LOW,
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItem(
                        image = TierImage.FLASHLIGHT_2,
                        flavor = TierFlavorText.FLASHLIGHT_2,
                        info = TierInformation.FLASHLIGHT_2,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_19,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_NARROW,
                            EquipmentAttribute.INTENSITY_MED
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    ),
                    CodexEquipmentGroupItem(
                        image = TierImage.FLASHLIGHT_3,
                        flavor = TierFlavorText.FLASHLIGHT_3,
                        info = TierInformation.FLASHLIGHT_3,
                        upgradeCostData = UpgradeCost.COST_3000,
                        upgradeLevelData = UnlockLevel.LEVEL_35,
                        positiveAttributes = listOf(
                            EquipmentAttribute.SPOTLIGHT_WIDE,
                            EquipmentAttribute.INTENSITY_HIGH
                        ),
                        negativeAttributes = listOf(
                            EquipmentAttribute.PROPERTY_ELECTRONIC
                        )
                    )
                )
            ),
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(
                items = groups
            ) { group ->
                CodexGroup(
                    groupTitle = group.name.toStringResource()
                ) {
                    CodexGroupItems(
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
                                isBordered = true,
                                tierLevel = index + 1 ,
                                image = item.image.toDrawableResource()
                            ) {

                            }
                        }
                    }
                }
            }
        }

    }
}