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
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemAltName
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemAttribute
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemDrawChance
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemFlavorText
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemImage
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemInfoText
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionItemSanityDrain
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexPossessionsResources.PossessionsIcon
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.possessions.CodexPossessionsGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.possessions.CodexPossessionsGroupItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.CodexViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexGroupItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexGroupItems
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexItemPopup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexItemPopupDataRow


@Composable
fun CodexPossessionsListComponent(
    codexViewModel: CodexViewModel
) {

    val possessionUiState = codexViewModel.possessionsUiState.collectAsStateWithLifecycle()

    val groups = possessionUiState.value.list

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
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
                    group.items.forEach { item ->
                        CodexGroupItem(
                            modifier =
                                Modifier
                                    .widthIn(min = 32.dp, max = 96.dp)
                                    .aspectRatio(1f)
                                    .then(
                                        if (group.size <= 3) {
                                            Modifier.weight(1f, false)
                                        } else {
                                            Modifier
                                        }
                                    ),
                            isBackground = true,
                            isBordered = true,
                            image = item.image.toDrawableResource()
                        ) {
                            codexViewModel.setSelectedPossession(group, item)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun CodexPossessionsDisplay(
    codexViewModel: CodexViewModel
) {

    val possessionUiState by codexViewModel.possessionsUiState.collectAsStateWithLifecycle()

    val selectedGroup = possessionUiState.selectedGroup ?: return
    val selectedItem = possessionUiState.selectedItem ?: return

    val image = selectedItem.image.toDrawableResource()

    val primaryTitle: AnnotatedString? = AnnotatedString.Companion.fromHtml(
        stringResource(selectedGroup.name.toStringResource()))
    val secondaryTitle: AnnotatedString? = AnnotatedString.Companion.fromHtml(
        selectedItem.altName?.toStringResource()?.let {
            stringResource(it) } ?: "")

    val primaryText = AnnotatedString.Companion.fromHtml(
        stringResource(selectedItem.infoText.toStringResource()))

    val secondaryText = AnnotatedString.Companion.fromHtml(
        stringResource(selectedItem.flavorText.toStringResource()))

    val footerText = AnnotatedString.Companion.fromHtml(
        stringResource(selectedItem.attributesText.toStringResource()))

    val sanityDrain = AnnotatedString.Companion.fromHtml(
        stringResource(selectedItem.sanityDrain.toStringResource()))
    val drawChance = AnnotatedString.Companion.fromHtml(
        stringResource(selectedItem.drawChance.toStringResource()))

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
            secondaryTitleContent = { modifier ->
                secondaryTitle?.let { title ->
                    if(title.isNotBlank()) {
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
                    icon = R.drawable.ic_shop_sanitydrain,
                    data = "$sanityDrain"
                )

                CodexItemPopupDataRow(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .heightIn(max = 32.dp),
                    icon = R.drawable.ic_shop_chance,
                    data = "$drawChance"
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
                codexViewModel.setSelectedPossession(null, null)
            }
        )
    }
}

@Preview
@Composable
private fun CodexPossessionsItemPreview() {

    val groups = listOf(
        CodexPossessionsGroup(
            name = PossessionTitle.HAUNTED_MIRROR,
            icon = PossessionsIcon.HAUNTED_MIRROR,
            items = listOf(
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.HAUNTED_MIRROR,
                    flavorText = PossessionItemFlavorText.HAUNTED_MIRROR,
                    infoText = PossessionItemInfoText.HAUNTED_MIRROR,
                    attributesText = PossessionItemAttribute.HAUNTED_MIRROR,
                    sanityDrain = PossessionItemSanityDrain.HAUNTED_MIRROR,
                    drawChance = PossessionItemDrawChance.HAUNTED_MIRROR
                )
            )
        ),
        // Monkey Paw
        CodexPossessionsGroup(
            name = PossessionTitle.MONKEY_PAW,
            icon = PossessionsIcon.MONKEY_PAW,
            items = listOf(

                CodexPossessionsGroupItem(
                    image = PossessionItemImage.MONKEY_PAW,
                    flavorText = PossessionItemFlavorText.MONKEY_PAW,
                    infoText = PossessionItemInfoText.MONKEY_PAW,
                    attributesText = PossessionItemAttribute.MONKEY_PAW,
                    sanityDrain = PossessionItemSanityDrain.MONKEY_PAW,
                    drawChance = PossessionItemDrawChance.MONKEY_PAW,
                    altName = PossessionItemAltName.MONKEY_PAW
                ),
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.MONKEY_PAW_WISH,
                    flavorText = PossessionItemFlavorText.MONKEY_PAW_WISH,
                    infoText = PossessionItemInfoText.MONKEY_PAW_WISH,
                    attributesText = PossessionItemAttribute.MONKEY_PAW_WISH,
                    sanityDrain = PossessionItemSanityDrain.MONKEY_PAW_WISH,
                    drawChance = PossessionItemDrawChance.MONKEY_PAW_WISH,
                    altName = PossessionItemAltName.MONKEY_PAW_WISH
                )
            )
        ),
        // Music Box
        CodexPossessionsGroup(
            name = PossessionTitle.MUSIC_BOX,
            icon = PossessionsIcon.MUSIC_BOX,
            items = listOf(
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.MUSIC_BOX,
                    flavorText = PossessionItemFlavorText.MUSIC_BOX,
                    infoText = PossessionItemInfoText.MUSIC_BOX,
                    attributesText = PossessionItemAttribute.MUSIC_BOX,
                    sanityDrain = PossessionItemSanityDrain.MUSIC_BOX,
                    drawChance = PossessionItemDrawChance.MUSIC_BOX
                )

            )
        ),
        // Ouija Board
        CodexPossessionsGroup(
            name = PossessionTitle.OUIJA_BOARD,
            icon = PossessionsIcon.OUIJA_BOARD,
            items = listOf(
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.OUIJA_BOARD,
                    flavorText = PossessionItemFlavorText.OUIJA_BOARD,
                    infoText = PossessionItemInfoText.OUIJA_BOARD,
                    attributesText = PossessionItemAttribute.OUIJA_BOARD,
                    sanityDrain = PossessionItemSanityDrain.OUIJA_BOARD,
                    drawChance = PossessionItemDrawChance.OUIJA_BOARD
                )
            )
        ),
        // Summoning  Circle
        CodexPossessionsGroup(
            name = PossessionTitle.SUMMONING_CIRCLE,
            icon = PossessionsIcon.SUMMONING_CIRCLE,
            items = listOf(
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.SUMMONING_CIRCLE,
                    flavorText = PossessionItemFlavorText.SUMMONING_CIRCLE,
                    infoText = PossessionItemInfoText.SUMMONING_CIRCLE,
                    attributesText = PossessionItemAttribute.SUMMONING_CIRCLE,
                    sanityDrain = PossessionItemSanityDrain.SUMMONING_CIRCLE,
                    drawChance = PossessionItemDrawChance.SUMMONING_CIRCLE
                )
            )
        ),
        // Tarot Cards
        CodexPossessionsGroup(
            name = PossessionTitle.TAROT_CARDS,
            icon = PossessionsIcon.TAROT_CARDS,
            items = listOf(
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.TAROT_CARDS_HANGED_MAN,
                    flavorText = PossessionItemFlavorText.TAROT_CARDS_HANGED_MAN,
                    infoText = PossessionItemInfoText.TAROT_CARDS_HANGED_MAN,
                    attributesText = PossessionItemAttribute.TAROT_CARDS_HANGED_MAN,
                    sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_HANGED_MAN,
                    drawChance = PossessionItemDrawChance.TAROT_CARDS_HANGED_MAN,
                    altName = PossessionItemAltName.TAROT_CARDS_HANGED_MAN
                ),
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.TAROT_CARDS_PRIESTESS,
                    flavorText = PossessionItemFlavorText.TAROT_CARDS_PRIESTESS,
                    infoText = PossessionItemInfoText.TAROT_CARDS_PRIESTESS,
                    attributesText = PossessionItemAttribute.TAROT_CARDS_PRIESTESS,
                    sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_PRIESTESS,
                    drawChance = PossessionItemDrawChance.TAROT_CARDS_PRIESTESS,
                    altName = PossessionItemAltName.TAROT_CARDS_PRIESTESS
                ),
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.TAROT_CARDS_DEATH,
                    flavorText = PossessionItemFlavorText.TAROT_CARDS_DEATH,
                    infoText = PossessionItemInfoText.TAROT_CARDS_DEATH,
                    attributesText = PossessionItemAttribute.TAROT_CARDS_DEATH,
                    sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_DEATH,
                    drawChance = PossessionItemDrawChance.TAROT_CARDS_DEATH,
                    altName = PossessionItemAltName.TAROT_CARDS_DEATH
                ),
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.TAROT_CARDS_DEVIL,
                    flavorText = PossessionItemFlavorText.TAROT_CARDS_DEVIL,
                    infoText = PossessionItemInfoText.TAROT_CARDS_DEVIL,
                    attributesText = PossessionItemAttribute.TAROT_CARDS_DEVIL,
                    sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_DEVIL,
                    drawChance = PossessionItemDrawChance.TAROT_CARDS_DEVIL,
                    altName = PossessionItemAltName.TAROT_CARDS_DEVIL
                ),
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.TAROT_CARDS_FORTUNE,
                    flavorText = PossessionItemFlavorText.TAROT_CARDS_FORTUNE,
                    infoText = PossessionItemInfoText.TAROT_CARDS_FORTUNE,
                    attributesText = PossessionItemAttribute.TAROT_CARDS_FORTUNE,
                    sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_FORTUNE,
                    drawChance = PossessionItemDrawChance.TAROT_CARDS_FORTUNE,
                    altName = PossessionItemAltName.TAROT_CARDS_FORTUNE
                ),
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.TAROT_CARDS_HERMIT,
                    flavorText = PossessionItemFlavorText.TAROT_CARDS_HERMIT,
                    infoText = PossessionItemInfoText.TAROT_CARDS_HERMIT,
                    attributesText = PossessionItemAttribute.TAROT_CARDS_HERMIT,
                    sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_HERMIT,
                    drawChance = PossessionItemDrawChance.TAROT_CARDS_HERMIT,
                    altName = PossessionItemAltName.TAROT_CARDS_HERMIT
                ),
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.TAROT_CARDS_JOKER,
                    flavorText = PossessionItemFlavorText.TAROT_CARDS_JOKER,
                    infoText = PossessionItemInfoText.TAROT_CARDS_JOKER,
                    attributesText = PossessionItemAttribute.TAROT_CARDS_JOKER,
                    sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_JOKER,
                    drawChance = PossessionItemDrawChance.TAROT_CARDS_JOKER,
                    altName = PossessionItemAltName.TAROT_CARDS_JOKER
                ),
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.TAROT_CARDS_MOON,
                    flavorText = PossessionItemFlavorText.TAROT_CARDS_MOON,
                    infoText = PossessionItemInfoText.TAROT_CARDS_MOON,
                    attributesText = PossessionItemAttribute.TAROT_CARDS_MOON,
                    sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_MOON,
                    drawChance = PossessionItemDrawChance.TAROT_CARDS_MOON,
                    altName = PossessionItemAltName.TAROT_CARDS_MOON
                ),
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.TAROT_CARDS_SUN,
                    flavorText = PossessionItemFlavorText.TAROT_CARDS_SUN,
                    infoText = PossessionItemInfoText.TAROT_CARDS_SUN,
                    attributesText = PossessionItemAttribute.TAROT_CARDS_SUN,
                    sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_SUN,
                    drawChance = PossessionItemDrawChance.TAROT_CARDS_SUN,
                    altName = PossessionItemAltName.TAROT_CARDS_SUN
                ),
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.TAROT_CARDS_TOWER,
                    flavorText = PossessionItemFlavorText.TAROT_CARDS_TOWER,
                    infoText = PossessionItemInfoText.TAROT_CARDS_TOWER,
                    attributesText = PossessionItemAttribute.TAROT_CARDS_TOWER,
                    sanityDrain = PossessionItemSanityDrain.TAROT_CARDS_TOWER,
                    drawChance = PossessionItemDrawChance.TAROT_CARDS_TOWER,
                    altName = PossessionItemAltName.TAROT_CARDS_TOWER
                )

            )
        ),
        // Voodoo Doll
        CodexPossessionsGroup(
            name = PossessionTitle.VOODOO_DOLL,
            icon = PossessionsIcon.VOODOO_DOLL,
            items = listOf(
                CodexPossessionsGroupItem(
                    image = PossessionItemImage.VOODOO_DOLL,
                    flavorText = PossessionItemFlavorText.VOODOO_DOLL,
                    infoText = PossessionItemInfoText.VOODOO_DOLL,
                    attributesText = PossessionItemAttribute.VOODOO_DOLL,
                    sanityDrain = PossessionItemSanityDrain.VOODOO_DOLL,
                    drawChance = PossessionItemDrawChance.VOODOO_DOLL
                )
            )
        ),
    )

    SelectiveTheme {
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
                        group.items.forEach { item ->
                            CodexGroupItem(
                                modifier = Modifier
                                    .widthIn(min = 32.dp, max = 96.dp)
                                    .aspectRatio(1f)
                                    .then(
                                        if (group.size <= 3) {
                                            Modifier.weight(1f, false)
                                        } else {
                                            Modifier
                                        }
                                    ),
                                isBackground = true,
                                isBordered = true,
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