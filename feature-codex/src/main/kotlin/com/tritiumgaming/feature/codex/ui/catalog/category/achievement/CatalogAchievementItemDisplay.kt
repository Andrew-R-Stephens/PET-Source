package com.tritiumgaming.feature.codex.ui.catalog.category.achievement

   import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
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
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.codex.app.mappers.codex.toDrawableResource
import com.tritiumgaming.feature.codex.app.mappers.codex.toStringResource
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogDisplayUiActions
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogDisplayUiState
import com.tritiumgaming.feature.codex.ui.catalog.common.CodexItemPopup
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementExclusivity
import com.tritiumgaming.shared.data.codex.mappers.AchievementsResources.AchievementVisibility

@Composable
fun CatalogAchievementItemDisplay(
    modifier: Modifier = Modifier,
    displayUiState: CatalogDisplayUiState.Achievements,
    displayUiActions: CatalogDisplayUiActions
) {

    val selectedGroup = displayUiState.selectedGroup ?: return
    val selectedItem = displayUiState.selectedItem ?: return

    val image = selectedGroup.icon.toDrawableResource()

    val primaryTitle: AnnotatedString = AnnotatedString.fromHtml(
        stringResource(selectedGroup.name.toStringResource()))

    val primaryText = AnnotatedString.fromHtml(
        stringResource(selectedItem.infoText.toStringResource()))

    CodexItemPopup(
        modifier = modifier,
        background = LocalPalette.current.scrim,
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
        primaryDataContent = { modifier ->
            Row(
                modifier = modifier
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (selectedGroup.exclusivity and AchievementExclusivity.STEAM.value != 0) {
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .heightIn(max = 24.dp)
                            .aspectRatio(1f)
                            .padding(1.dp),
                        painter = painterResource(
                            id = AchievementExclusivity.STEAM.toDrawableResource()
                        ),
                        contentDescription = "Steam Logo",
                        tint = LocalPalette.current.codexFamily.codex3,
                    )
                }
                if (selectedGroup.exclusivity and AchievementExclusivity.XBOX.value != 0) {
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .heightIn(max = 24.dp)
                            .aspectRatio(1f)
                            .padding(1.dp),
                        painter = painterResource(
                            id = AchievementExclusivity.XBOX.toDrawableResource()
                        ),
                        contentDescription = "Xbox Logo",
                        tint = LocalPalette.current.codexFamily.codex3,
                    )
                }
                if (selectedGroup.exclusivity and AchievementExclusivity.PLAYSTATION.value != 0) {
                    Icon(
                        modifier = Modifier
                            .fillMaxHeight()
                            .heightIn(max = 24.dp)
                            .aspectRatio(1f)
                            .padding(1.dp),
                        painter = painterResource(
                            id = AchievementExclusivity.PLAYSTATION.toDrawableResource()
                        ),
                        contentDescription = "Playstation Logo",
                        tint = LocalPalette.current.codexFamily.codex3,
                    )
                }
            }

            if (selectedGroup.visibility == AchievementVisibility.HIDDEN) {
                Row(
                    modifier = modifier
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp, horizontal = 2.dp),
                        text = stringResource(R.string.achievements_visibility_hidden),
                        style = LocalTypography.current.quaternary.regular.copy(
                            textAlign = TextAlign.Start
                        ),
                        color = LocalPalette.current.codexFamily.codex3,
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(minFontSize = 1.sp)
                    )
                }
            }
        },
        primaryImageContent = { modifier ->
            Image(
                modifier = modifier,
                painter = painterResource(id = image),
                contentDescription = "Primary Icon",
                contentScale = ContentScale.Fit
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
                    text = primaryText,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    color = LocalPalette.current.codexFamily.codex3,
                    fontSize = 18.sp
                )
            }
        },
        onDismiss = { displayUiActions.onDismiss() }
    )

}
