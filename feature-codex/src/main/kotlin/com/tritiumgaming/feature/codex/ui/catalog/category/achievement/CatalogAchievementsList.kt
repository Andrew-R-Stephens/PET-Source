package com.tritiumgaming.feature.codex.ui.catalog.category.achievement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.feature.codex.app.mappers.codex.toDrawableResource
import com.tritiumgaming.feature.codex.app.mappers.codex.toStringResource
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogCategory
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogListUiActions
import com.tritiumgaming.feature.codex.ui.catalog.common.CodexGroup
import com.tritiumgaming.feature.codex.ui.catalog.common.CodexGroupItem

@Composable
fun CatalogAchievementsList(
    catalogUiState: CatalogCategory.Achievements,
    listUiActions: CatalogListUiActions.Achievements,
    scrollState: LazyListState
) {
    val groups = catalogUiState.list

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                state = scrollState,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    items = groups,
                    key = { it.name }
                ) { group ->
                    val itemCount = group.items.size

                    val arrangement = when {
                        (itemCount == 1) -> Arrangement.Center
                        (itemCount == 2) -> Arrangement.SpaceEvenly
                        (itemCount > 3) -> Arrangement.spacedBy(4.dp)
                        else -> Arrangement.SpaceBetween
                    }

                    CodexGroup(
                        groupTitle = group.name.toStringResource()
                    ) {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(min = 48.dp, max = 96.dp),
                            horizontalArrangement = arrangement
                        ) {
                            items(
                                items = group.items,
                                key = { it.title }
                            ) { item ->
                                CodexGroupItem(
                                    modifier = Modifier
                                        .sizeIn(
                                            minWidth = 64.dp, maxWidth = 96.dp,
                                            minHeight = 64.dp, maxHeight = 96.dp
                                        )
                                        .aspectRatio(1f),
                                    isBordered = true,
                                    image = item.icon.toDrawableResource()
                                ) {
                                    listUiActions.onSelect(group, item)
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
                    items = groups,
                    key = { it.name }
                ) { group ->
                    CodexGroup(
                        groupTitle = group.name.toStringResource()
                    ) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(96.dp)
                        ) {
                            items(
                                items = group.items,
                                key = { it.title }
                            ) { item ->
                                CodexGroupItem(
                                    modifier = Modifier
                                        .sizeIn(
                                            minWidth = 64.dp, maxWidth = 96.dp,
                                            minHeight = 64.dp, maxHeight = 96.dp
                                        )
                                        .aspectRatio(1f),
                                    isBordered = true,
                                    image = item.icon.toDrawableResource()
                                ) {
                                    listUiActions.onSelect(group, item)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}