package com.tritiumgaming.feature.codex.ui.catalog.category.possession

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.GridIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.codex.app.mappers.codex.toDrawableResource
import com.tritiumgaming.feature.codex.app.mappers.codex.toStringResource
import com.tritiumgaming.feature.codex.ui.CodexViewModel
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogDisplayUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.equipment.DisplayUiActions
import com.tritiumgaming.feature.codex.ui.catalog.category.equipment.EquipmentCatalogUiState
import com.tritiumgaming.feature.codex.ui.catalog.common.CodexItemPopup
import com.tritiumgaming.feature.codex.ui.catalog.common.CodexItemPopupDataRow

@Composable
fun PossessionsCatalogDisplay(
    modifier: Modifier = Modifier,
    displayUiState: CatalogDisplayUiState.Possessions,
    displayUiActions: DisplayUiActions
) {

    val selectedGroup = displayUiState.selectedGroup ?: return
    val selectedItem = displayUiState.selectedItem ?: return

    val image = selectedItem.image.toDrawableResource()

    val primaryTitle: AnnotatedString = AnnotatedString.fromHtml(
        stringResource(selectedGroup.name.toStringResource()))
    val secondaryTitle: AnnotatedString = AnnotatedString.fromHtml(
        selectedItem.altName?.toStringResource()?.let {
            stringResource(it) } ?: "")

    val primaryText = AnnotatedString.fromHtml(
        stringResource(selectedItem.infoText.toStringResource()))

    val secondaryText = AnnotatedString.fromHtml(
        stringResource(selectedItem.flavorText.toStringResource()))

    val footerText = AnnotatedString.fromHtml(
        stringResource(selectedItem.attributesText.toStringResource()))

    val sanityDrain = AnnotatedString.fromHtml(
        stringResource(selectedItem.sanityDrain.toStringResource()))
    val drawChance = AnnotatedString.fromHtml(
        stringResource(selectedItem.drawChance.toStringResource()))

    CodexItemPopup(
        modifier = modifier,
        primaryTitleContent = { modifier ->
            Text(
                modifier = modifier
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                        initialDelayMillis = 1000,
                        repeatDelayMillis = 1000,
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                text = primaryTitle,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                color = LocalPalette.current.codexFamily.codex3,
                maxLines = 1,
                fontSize = 20.sp
            )
        },
        secondaryTitleContent = { modifier ->
            if (secondaryTitle.isNotBlank()) {
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
        onDismiss = { displayUiActions.onDismiss() }
    )
}
