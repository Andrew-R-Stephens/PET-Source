package com.tritiumgaming.feature.operation.ui.codex.catalog.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.operation.app.mappers.codex.toDrawableResource
import com.tritiumgaming.feature.operation.app.mappers.codex.toStringResource
import com.tritiumgaming.feature.operation.ui.codex.CodexViewModel
import com.tritiumgaming.feature.operation.ui.codex.catalog.common.CodexGroup
import com.tritiumgaming.feature.operation.ui.codex.catalog.common.CodexGroupItem
import com.tritiumgaming.feature.operation.ui.codex.catalog.common.CodexGroupItemsLandscape
import com.tritiumgaming.feature.operation.ui.codex.catalog.common.CodexGroupItemsPortrait
import com.tritiumgaming.feature.operation.ui.codex.catalog.common.CodexItemPopup
import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources.AchievementExclusivity
import com.tritiumgaming.shared.operation.domain.codex.mappers.AchievementsResources.AchievementVisibility

@Composable
fun CatalogAchievementListComponent(
    codexViewModel: CodexViewModel,
    scrollState: LazyListState
) {
    val achievementsUiState = codexViewModel.achievementsUiState.collectAsStateWithLifecycle()

    val groups = achievementsUiState.value.list

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
                            itemCount = 1
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
                            itemCount = 1
                        ) {
                            CodexGroupItem(
                                modifier = Modifier
                                    .sizeIn(
                                        minWidth = 64.dp, maxWidth = 96.dp,
                                        minHeight = 64.dp, maxHeight = 96.dp)
                                    .aspectRatio(1f)/*
                                        .weight(1f, false)*/,
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
    }
    /*
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
                val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
                val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

                when(deviceConfiguration) {
                    DeviceConfiguration.MOBILE_PORTRAIT -> {
                        CodexGroupItemsPortrait(
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
                    DeviceConfiguration.MOBILE_LANDSCAPE,
                    DeviceConfiguration.TABLET_PORTRAIT,
                    DeviceConfiguration.TABLET_LANDSCAPE,
                    DeviceConfiguration.DESKTOP -> {
                        CodexGroupItemsLandscape(
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
    }*/

}

@Composable
fun CodexAchievementDisplay(
    modifier: Modifier = Modifier,
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
                            id = AchievementExclusivity.STEAM.toDrawableResource()),
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
                            id = AchievementExclusivity.XBOX.toDrawableResource()),
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
                            id = AchievementExclusivity.PLAYSTATION.toDrawableResource()),
                        contentDescription = "Playstation Logo",
                        tint = LocalPalette.current.codexFamily.codex3,
                    )
                }
            }

            if(selectedGroup.visibility == AchievementVisibility.HIDDEN) {
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
        onDismiss = {
            codexViewModel.setSelectedAchievement(null, null)
        }
    )

}
