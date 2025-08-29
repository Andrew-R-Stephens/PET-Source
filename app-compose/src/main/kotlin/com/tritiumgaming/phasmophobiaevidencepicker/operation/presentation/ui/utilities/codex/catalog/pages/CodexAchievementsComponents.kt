package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexAchievementsResources.AchievementContent
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexAchievementsResources.AchievementIcon
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.mappers.CodexAchievementsResources.AchievementTitle
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.achievements.CodexAchievementsGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.model.achievements.CodexAchievementsGroupItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.CodexViewModel
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexGroup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexGroupItem
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexGroupItems
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.common.CodexItemPopup

@Composable
fun CodexAchievementListComponent(
    codexViewModel: CodexViewModel
) {

    val achievementsUiState = codexViewModel.achievementsUiState.collectAsStateWithLifecycle()

    val groups = achievementsUiState.value.list

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
                        .heightIn(min = 48.dp, max = 96.dp)
                ) {
                    CodexGroupItem(
                        modifier = Modifier
                            .widthIn(min = 32.dp, max = 96.dp)
                            .aspectRatio(1f)
                            .weight(1f, false),
                        isBordered = true,
                        image =  group.icon.toDrawableResource()
                    ) {
                        codexViewModel.setSelectedAchievement(group, group.item)
                    }
                }
            }
        }
    }

}

@Composable
fun CodexAchievementDisplay(
    codexViewModel: CodexViewModel
) {
    val achievementsUiState by codexViewModel.achievementsUiState.collectAsStateWithLifecycle()

    val selectedGroup = achievementsUiState.selectedGroup ?: return
    val selectedItem = achievementsUiState.selectedItem ?: return

    val image = selectedGroup.icon.toDrawableResource()

    val primaryTitle: AnnotatedString? = AnnotatedString.Companion.fromHtml(
        stringResource(selectedGroup.name.toStringResource()))

    val primaryText = AnnotatedString.Companion.fromHtml(
        stringResource(selectedItem.infoText.toStringResource()))

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
                        color = LocalPalette.current.codexFamily.codex3_popupHeaderText,
                        fontSize = 18.sp
                    )
                }
            },
            onDismiss = {
                codexViewModel.setSelectedAchievement(null, null)
            }
        )
    }

}


@Preview
@Composable
private fun CodexAchievementItemPreview() {

    SelectiveTheme {
        val groups = listOf(
            CodexAchievementsGroup(
                name = AchievementTitle.FULLY_LOADED,
                icon = AchievementIcon.FULLY_LOADED,
                item = CodexAchievementsGroupItem(
                    infoText = AchievementContent.FULLY_LOADED
                )
            ),
            CodexAchievementsGroup(
                name = AchievementTitle.DIRECTOR,
                icon = AchievementIcon.DIRECTOR,
                item = CodexAchievementsGroupItem(
                    infoText = AchievementContent.DIRECTOR
                )
            ),
            CodexAchievementsGroup(
                name = AchievementTitle.BRONZE_HUNTER,
                icon = AchievementIcon.BRONZE_HUNTER,
                item = CodexAchievementsGroupItem(
                    infoText = AchievementContent.BRONZE_HUNTER
                )
            ),
            CodexAchievementsGroup(
                name = AchievementTitle.SILVER_HUNTER,
                icon = AchievementIcon.SILVER_HUNTER,
                item = CodexAchievementsGroupItem(
                    infoText = AchievementContent.SILVER_HUNTER
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
                            .heightIn(min = 48.dp, max = 96.dp)
                    ) {
                        CodexGroupItem(
                            modifier = Modifier
                                .widthIn(min = 32.dp, max = 96.dp)
                                .aspectRatio(1f)
                                .weight(1f, false),
                            isBackground = false,
                            isBordered = false,
                            image =  group.icon.toDrawableResource()
                        ) {

                        }
                    }
                }
            }
        }

    }
}
