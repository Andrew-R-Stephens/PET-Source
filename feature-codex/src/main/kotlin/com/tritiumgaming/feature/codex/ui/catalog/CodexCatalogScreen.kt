package com.tritiumgaming.feature.codex.ui.catalog

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.codex.ui.CodexScreen
import com.tritiumgaming.feature.codex.ui.CodexScreenUiActions
import com.tritiumgaming.feature.codex.ui.CodexScreenUiState
import com.tritiumgaming.feature.codex.ui.CodexViewModel
import com.tritiumgaming.feature.codex.ui.catalog.category.achievement.AchievementsCatalogUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.achievement.AchievementCatalogList
import com.tritiumgaming.feature.codex.ui.catalog.category.equipment.CatalogEquipmentListComponent
import com.tritiumgaming.feature.codex.ui.catalog.category.achievement.AchievementCatalogDisplay
import com.tritiumgaming.feature.codex.ui.catalog.category.equipment.EquipmentCatalogDisplay
import com.tritiumgaming.feature.codex.ui.catalog.category.equipment.CatalogListUiActions
import com.tritiumgaming.feature.codex.ui.catalog.category.equipment.EquipmentCatalogUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.equipment.DisplayUiActions
import com.tritiumgaming.feature.codex.ui.catalog.category.possession.PossessionsCatalogDisplay
import com.tritiumgaming.feature.codex.ui.catalog.category.possession.PossessionsCatalogList
import com.tritiumgaming.feature.codex.ui.catalog.category.possession.PossessionsCatalogUiState
import com.tritiumgaming.shared.data.codex.mappers.CodexResources

@Composable
fun CodexCatalogScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    codexViewModel: CodexViewModel,
    category: CodexResources.Category
) {

    val categoryTitle = when(category) {
        CodexResources.Category.EQUIPMENT -> R.string.store_title_equipment
        CodexResources.Category.POSSESSIONS -> R.string.store_title_cursedpossessions
        CodexResources.Category.ACHIEVEMENTS -> R.string.store_title_achievements
    }

    codexViewModel.cacheCategory(category)

    val codexScreenUiState = CodexScreenUiState(
        headerTitle = categoryTitle,
        showBackButton = true
    )

    val codexScreenUiActions = CodexScreenUiActions(
        onBackClicked = {
            navController.popBackStack()
        }
    )

    val scrollUiState by codexViewModel.scrollUiState.collectAsStateWithLifecycle()
    val equipmentUiState by codexViewModel.equipmentUiState.collectAsStateWithLifecycle()
    val possessionsUiState by codexViewModel.possessionsUiState.collectAsStateWithLifecycle()
    val achievementUiState by codexViewModel.achievementsUiState.collectAsStateWithLifecycle()

    val equipmentListUiActions = CatalogListUiActions.Equipment(
        onSelect = { group, item ->
            codexViewModel.setSelectedEquipment(group, item)
        }
    )

    val possessionsListUiActions = CatalogListUiActions.Possessions(
        onSelect = { group, item ->
            codexViewModel.setSelectedPossession(group, item)
        }
    )

    val achievementsListUiActions = CatalogListUiActions.Achievements(
        onSelect = { group, item ->
            codexViewModel.setSelectedAchievement(group, item)
        }
    )

    val displayUiActions = DisplayUiActions(
        onDismiss = {
            codexViewModel.setSelectedEquipment(null, null)
            codexViewModel.setSelectedPossession(null, null)
            codexViewModel.setSelectedAchievement(null, null)
        }
    )

    val paginatorUiState = PaginatorUiState(
        scrollUiState = scrollUiState,
        images = when(category) {
            CodexResources.Category.EQUIPMENT -> equipmentUiState.icons
            CodexResources.Category.POSSESSIONS -> possessionsUiState.icons
            CodexResources.Category.ACHIEVEMENTS -> achievementUiState.icons
        }
    )

    val paginatorUiActions = PaginatorUiActions(
        onScrollUpdate = { offset, index ->
            codexViewModel.setScrollOffset(offset, index) }
    )

    val rememberScrollState = rememberLazyListState()

    LaunchedEffect(scrollUiState.offset) {
        rememberScrollState.scrollToItem(
            index = (scrollUiState.offset *
                    (rememberScrollState.layoutInfo.totalItemsCount+1 -
                            rememberScrollState.layoutInfo.visibleItemsInfo.size+1)
                    ).toInt()
        )
    }

    LaunchedEffect(rememberScrollState) {
        snapshotFlow { rememberScrollState.firstVisibleItemScrollOffset }
            .collect { firstVisibleIndex ->
                rememberScrollState.layoutInfo.viewportSize

                val firstVisibleIndex = rememberScrollState.firstVisibleItemIndex.toFloat()

                val currentVisibleItems = (rememberScrollState.layoutInfo.visibleItemsInfo.size).toFloat()

                val totalItems = (rememberScrollState.layoutInfo.totalItemsCount).toFloat()

                codexViewModel.setScrollOffset(
                    index = (
                            (((totalItems - (currentVisibleItems - firstVisibleIndex+1)) / totalItems)).toInt()
                    ),
                )
            }
    }

    CodexScreen(
        modifier = modifier,
        codexScreenUiState = codexScreenUiState,
        codexScreenUiActions = codexScreenUiActions
    ) {

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {

                CodexItemScreenContentPortrait(
                    category = category,
                    scrollState = rememberScrollState,
                    paginatorUiState = paginatorUiState,
                    paginatorUiActions = paginatorUiActions,
                    equipmentUiState = equipmentUiState,
                    possessionsUiState = possessionsUiState,
                    achievementUiState = achievementUiState,
                    equipmentListUiActions = equipmentListUiActions,
                    possessionsListUiActions = possessionsListUiActions,
                    achievementsListUiActions = achievementsListUiActions,
                    displayUiActions = displayUiActions
                )
            }

            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {

                CodexItemScreenContentLandscape(
                    category = category,
                    scrollState = rememberScrollState,
                    paginatorUiState = paginatorUiState,
                    paginatorUiActions = paginatorUiActions,
                    equipmentUiState = equipmentUiState,
                    possessionsUiState = possessionsUiState,
                    achievementUiState = achievementUiState,
                    equipmentListUiActions = equipmentListUiActions,
                    possessionsListUiActions = possessionsListUiActions,
                    achievementsListUiActions = achievementsListUiActions,
                    displayUiActions = displayUiActions
                )
            }
        }
    }

}

@Composable
private fun CodexItemScreenContentPortrait(
    category: CodexResources.Category,
    scrollState: LazyListState,
    paginatorUiState: PaginatorUiState,
    paginatorUiActions: PaginatorUiActions,
    equipmentUiState: EquipmentCatalogUiState,
    possessionsUiState: PossessionsCatalogUiState,
    achievementUiState: AchievementsCatalogUiState,
    equipmentListUiActions: CatalogListUiActions.Equipment,
    possessionsListUiActions: CatalogListUiActions.Possessions,
    achievementsListUiActions: CatalogListUiActions.Achievements,
    displayUiActions: DisplayUiActions
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        VerticalPaginator(
            modifier = Modifier
                .fillMaxHeight()
                .width(48.dp),
            paginatorUiState = paginatorUiState,
            paginatorUiActions = paginatorUiActions
        )

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopCenter
        ) {
            when(category) {
                CodexResources.Category.EQUIPMENT -> {

                    CatalogEquipmentListComponent(
                        scrollState = scrollState,
                        equipmentCatalogUiState = equipmentUiState,
                        equipmentListUiActions = equipmentListUiActions
                    )

                    EquipmentCatalogDisplay(
                        modifier = Modifier
                            .fillMaxHeight(.8f)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        equipmentCatalogUiState = equipmentUiState,
                        displayUiActions = displayUiActions
                    )
                }
                CodexResources.Category.POSSESSIONS -> {

                    PossessionsCatalogList(
                        possessionsCatalogUiState = possessionsUiState,
                        possessionsListUiActions = possessionsListUiActions,
                        scrollState = scrollState
                    )

                    PossessionsCatalogDisplay(
                        modifier = Modifier
                            .fillMaxHeight(.8f)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        possessionsCatalogUiState = possessionsUiState,
                        displayUiActions = displayUiActions
                    )
                }
                CodexResources.Category.ACHIEVEMENTS -> {

                    AchievementCatalogList(
                        scrollState = scrollState,
                        achievementCatalogUiState = achievementUiState,
                        achievementsListUiActions = achievementsListUiActions
                    )

                    AchievementCatalogDisplay(
                        modifier = Modifier
                            .fillMaxHeight(.8f)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        achievementsCatalogUiState = achievementUiState,
                        displayUiActions = displayUiActions
                    )
                }
            }

        }
    }
}

data class PaginatorUiActions(
    val onScrollUpdate: (Float, Int) -> Unit
)

@Composable
private fun CodexItemScreenContentLandscape(
    category: CodexResources.Category,
    scrollState: LazyListState,
    paginatorUiState: PaginatorUiState,
    paginatorUiActions: PaginatorUiActions,
    equipmentUiState: EquipmentCatalogUiState,
    possessionsUiState: PossessionsCatalogUiState,
    achievementUiState: AchievementsCatalogUiState,
    equipmentListUiActions: CatalogListUiActions.Equipment,
    possessionsListUiActions: CatalogListUiActions.Possessions,
    achievementsListUiActions: CatalogListUiActions.Achievements,
    displayUiActions: DisplayUiActions
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        HorizontalPaginator(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            paginatorUiState = paginatorUiState,
            paginatorUiActions = paginatorUiActions
        )

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopCenter
        ) {
            when(category) {
                CodexResources.Category.EQUIPMENT -> {

                    CatalogEquipmentListComponent(
                        scrollState = scrollState,
                        equipmentCatalogUiState = equipmentUiState,
                        equipmentListUiActions = equipmentListUiActions
                    )

                    EquipmentCatalogDisplay(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(.8f)
                            .align(Alignment.CenterStart),
                        equipmentCatalogUiState = equipmentUiState,
                        displayUiActions = displayUiActions
                    )
                }
                CodexResources.Category.POSSESSIONS -> {

                    PossessionsCatalogList(
                        possessionsCatalogUiState = possessionsUiState,
                        possessionsListUiActions = possessionsListUiActions,
                        scrollState = scrollState
                    )

                    PossessionsCatalogDisplay(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(.8f)
                            .align(Alignment.CenterStart),
                        possessionsCatalogUiState = possessionsUiState,
                        displayUiActions = displayUiActions
                    )
                }
                CodexResources.Category.ACHIEVEMENTS -> {

                    AchievementCatalogList(
                        scrollState = scrollState,
                        achievementCatalogUiState = achievementUiState,
                        achievementsListUiActions = achievementsListUiActions
                    )

                    AchievementCatalogDisplay(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(.8f)
                            .align(Alignment.CenterStart),
                        achievementsCatalogUiState = achievementUiState,
                        displayUiActions = displayUiActions
                    )
                }
            }

        }
    }
}

@Composable
private fun VerticalPaginator(
    modifier: Modifier = Modifier,
    paginatorUiState: PaginatorUiState,
    paginatorUiActions: PaginatorUiActions
) {
    var paginatorHeight by remember {
        mutableFloatStateOf(0f)
    }

    Log.d("CodexItemstoreScreen",
        "VerticalPaginator: ${paginatorUiState.scrollUiState.itemIndex}")

    val images = paginatorUiState.images
    val scrollUiState = paginatorUiState.scrollUiState

    Column(
        modifier = modifier
            .background(LocalPalette.current.surface)
            .padding(8.dp)
            .onSizeChanged { paginatorHeight = it.height.toFloat() }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    val newOffset = (change.position.y / (paginatorHeight - 1)).coerceIn(0f, 1f)
                    val newIndex = (images.size * scrollUiState.offset).toInt()

                    paginatorUiActions.onScrollUpdate(newOffset, newIndex)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    val newOffset = (it.y / (paginatorHeight - 1)).coerceIn(0f, 1f)
                    val newIndex = (images.size * scrollUiState.offset).toInt()

                    paginatorUiActions.onScrollUpdate(newOffset, newIndex)
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        images.forEachIndexed { index, image ->
            Image(
                modifier = Modifier
                    .weight(1f, true)
                    .aspectRatio(1f)
                    .padding(vertical = 2.dp),
                painter = painterResource(id = image),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    if (index == scrollUiState.itemIndex)
                        LocalPalette.current.codexFamily.codex4
                    else {
                        LocalPalette.current.codexFamily.codex5
                    }
                )
            )
        }
    }
}

@Composable
private fun HorizontalPaginator(
    modifier: Modifier = Modifier,
    paginatorUiState: PaginatorUiState,
    paginatorUiActions: PaginatorUiActions
) {
    var paginatorWidth by remember {
        mutableFloatStateOf(0f)
    }

    Log.d("CodexItemstoreScreen",
        "VerticalPaginator: ${paginatorUiState.scrollUiState.itemIndex}")

    val images = paginatorUiState.images
    val scrollUiState = paginatorUiState.scrollUiState

    Row(
        modifier = modifier
            .background(LocalPalette.current.surface)
            .padding(8.dp)
            .onSizeChanged { paginatorWidth = it.width.toFloat() }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    val newOffset = (change.position.x / (paginatorWidth - 1)).coerceIn(0f, 1f)
                    val newIndex = (images.size * scrollUiState.offset).toInt()

                    paginatorUiActions.onScrollUpdate(newOffset, newIndex)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    val newOffset = (it.x / (paginatorWidth - 1)).coerceIn(0f, 1f)
                    val newIndex = (images.size * scrollUiState.offset).toInt()

                    paginatorUiActions.onScrollUpdate(newOffset, newIndex)
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        images.forEachIndexed { index, image ->
            Image(
                modifier = Modifier
                    .weight(1f, true)
                    .fillMaxHeight()
                    .padding(horizontal = 2.dp),
                painter = painterResource(id = image),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    if (index == scrollUiState.itemIndex)
                        LocalPalette.current.codexFamily.codex4
                    else {
                        LocalPalette.current.codexFamily.codex5
                    }
                ),
                contentScale = ContentScale.Fit
            )
        }
    }
}
