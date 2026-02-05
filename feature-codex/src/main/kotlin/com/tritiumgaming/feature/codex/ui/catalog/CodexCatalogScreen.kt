package com.tritiumgaming.feature.codex.ui.catalog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.stringResource
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
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogCategory
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogCategoryUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogDisplayUiActions
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogDisplayUiState
import com.tritiumgaming.feature.codex.ui.catalog.category.achievement.CatalogAchievementItemDisplay
import com.tritiumgaming.feature.codex.ui.catalog.category.achievement.CatalogAchievementsList
import com.tritiumgaming.feature.codex.ui.catalog.category.equipment.CatalogEquipmentList
import com.tritiumgaming.feature.codex.ui.catalog.category.CatalogListUiActions
import com.tritiumgaming.feature.codex.ui.catalog.category.equipment.CatalogEquipmentItemDisplay
import com.tritiumgaming.feature.codex.ui.catalog.category.possession.CatalogPossessionItemDisplay
import com.tritiumgaming.feature.codex.ui.catalog.category.possession.CatalogPossessionsList
import com.tritiumgaming.shared.data.codex.mappers.CodexResources
import kotlin.math.roundToInt

@Composable
fun CodexCatalogScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    codexCatalogScreenViewModel: CodexCatalogScreenViewModel,
    category: CodexResources.Category
) {
    LaunchedEffect(category) {
        codexCatalogScreenViewModel.loadCategory(category)
    }

    val catalogUiState by codexCatalogScreenViewModel.catalogUiState.collectAsStateWithLifecycle()
    val displayUiState by codexCatalogScreenViewModel.displayUiState.collectAsStateWithLifecycle()
    val scrollUiState by codexCatalogScreenViewModel.scrollUiState.collectAsStateWithLifecycle()

    val rememberScrollState = rememberLazyListState()

    LaunchedEffect(scrollUiState.offset) {
        val index = (scrollUiState.offset *
                (rememberScrollState.layoutInfo.totalItemsCount + 1 -
                        rememberScrollState.layoutInfo.visibleItemsInfo.size+1)
                ).toInt()

        rememberScrollState.scrollToItem(
            index = index
        )
    }

    LaunchedEffect(rememberScrollState) {
        snapshotFlow {
            Pair(rememberScrollState.firstVisibleItemIndex,
                rememberScrollState.firstVisibleItemScrollOffset
            )
        }
        .collect { (firstVisibleIndex, firstVisibleOffset) ->
            val layoutInfo = rememberScrollState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo

            if (visibleItemsInfo.isNotEmpty()) {
                val totalItems = layoutInfo.totalItemsCount

                val avgItemSize = visibleItemsInfo.map { it.size }.average().toFloat()

                val currentScrollPx = (firstVisibleIndex * avgItemSize) + firstVisibleOffset

                val viewportSize = if (layoutInfo.orientation == Orientation.Vertical)
                    layoutInfo.viewportSize.height else layoutInfo.viewportSize.width

                val totalContentSize = totalItems * avgItemSize
                val maxScrollPx = (totalContentSize - viewportSize).coerceAtLeast(1f)

                val absoluteOffset = (currentScrollPx / maxScrollPx).coerceIn(0f, 1f)

                val iconCount = catalogUiState.catalog.icons.size
                val targetIndex = if (iconCount > 0) {
                    (absoluteOffset * (iconCount - 1)).roundToInt().coerceIn(0, iconCount - 1)
                } else 0

                codexCatalogScreenViewModel.setScrollOffset(
                    index = targetIndex
                )
            }
        }
    }

    val categoryTitle = when(catalogUiState.catalog.category) {
        CodexResources.Category.EQUIPMENT ->
            stringResource(R.string.store_title_equipment)
        CodexResources.Category.POSSESSIONS ->
            stringResource(R.string.store_title_cursedpossessions)
        CodexResources.Category.ACHIEVEMENTS ->
            stringResource(R.string.store_title_achievements)
        else -> { "" }
    }

    val codexScreenUiState = CodexScreenUiState(
        headerTitle = categoryTitle,
        showBackButton = true
    )

    val codexScreenUiActions = CodexScreenUiActions(
        onBackClicked = {
            navController.popBackStack()
        }
    )

    val equipmentListUiActions = CatalogListUiActions.Equipment(
        onSelect = { group, item ->
            codexCatalogScreenViewModel.setSelectedEquipment(group, item)
        }
    )

    val possessionsListUiActions = CatalogListUiActions.Possessions(
        onSelect = { group, item ->
            codexCatalogScreenViewModel.setSelectedPossession(group, item)
        }
    )

    val achievementsListUiActions = CatalogListUiActions.Achievements(
        onSelect = { group, item ->
            codexCatalogScreenViewModel.setSelectedAchievement(group, item)
        }
    )

    val displayUiActions = CatalogDisplayUiActions(
        onDismiss = {
            codexCatalogScreenViewModel.clearDisplay()
        }
    )

    val paginatorUiState = PaginatorUiState(
        scrollUiState = scrollUiState,
        images = catalogUiState.catalog.icons
    )

    val paginatorUiActions = PaginatorUiActions(
        onScrollUpdate = { offset, index ->

            codexCatalogScreenViewModel.setScrollOffset(
                offset = offset
            )
        }
    )

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
                    scrollState = rememberScrollState,
                    paginatorUiState = paginatorUiState,
                    paginatorUiActions = paginatorUiActions,
                    catalogUiState = catalogUiState,
                    equipmentListUiActions = equipmentListUiActions,
                    possessionsListUiActions = possessionsListUiActions,
                    achievementsListUiActions = achievementsListUiActions,
                    displayUiState = displayUiState,
                    displayUiActions = displayUiActions
                )

            }
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {

                CodexItemScreenContentLandscape(
                    scrollState = rememberScrollState,
                    paginatorUiState = paginatorUiState,
                    paginatorUiActions = paginatorUiActions,
                    catalogUiState = catalogUiState,
                    equipmentListUiActions = equipmentListUiActions,
                    possessionsListUiActions = possessionsListUiActions,
                    achievementsListUiActions = achievementsListUiActions,
                    displayUiState = displayUiState,
                    displayUiActions = displayUiActions
                )

            }

        }
    }
}

@Composable
private fun CodexItemScreenContentPortrait(
    scrollState: LazyListState,
    paginatorUiState: PaginatorUiState,
    paginatorUiActions: PaginatorUiActions,
    catalogUiState: CatalogCategoryUiState,
    equipmentListUiActions: CatalogListUiActions.Equipment,
    possessionsListUiActions: CatalogListUiActions.Possessions,
    achievementsListUiActions: CatalogListUiActions.Achievements,
    displayUiState: CatalogDisplayUiState,
    displayUiActions: CatalogDisplayUiActions
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
            when(catalogUiState.catalog) {
                is CatalogCategory.Equipment -> {

                    CatalogEquipmentList(
                        scrollState = scrollState,
                        catalogUiState = catalogUiState.catalog,
                        listUiActions = equipmentListUiActions
                    )

                    if(displayUiState is CatalogDisplayUiState.Equipment) {
                        CatalogEquipmentItemDisplay(
                            modifier = Modifier
                                .fillMaxHeight(.8f)
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            displayUiState = displayUiState,
                            displayUiActions = displayUiActions
                        )
                    }
                }
                is CatalogCategory.Possessions -> {

                    CatalogPossessionsList(
                        scrollState = scrollState,
                        catalogUiState = catalogUiState.catalog,
                        listUiActions = possessionsListUiActions
                    )

                    if(displayUiState is CatalogDisplayUiState.Possessions) {
                        CatalogPossessionItemDisplay(
                            modifier = Modifier
                                .fillMaxHeight(.8f)
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            displayUiState = displayUiState,
                            displayUiActions = displayUiActions
                        )
                    }
                }
                is CatalogCategory.Achievements -> {

                    CatalogAchievementsList(
                        scrollState = scrollState,
                        catalogUiState = catalogUiState.catalog,
                        listUiActions = achievementsListUiActions
                    )

                    if(displayUiState is CatalogDisplayUiState.Achievements) {
                        CatalogAchievementItemDisplay(
                            modifier = Modifier
                                .fillMaxHeight(.8f)
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter),
                            displayUiState = displayUiState,
                            displayUiActions = displayUiActions
                        )
                    }
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier,
                            color = LocalPalette.current.codexFamily.codex4
                        )
                    }

                }
            }

        }
    }
}

@Composable
private fun CodexItemScreenContentLandscape(
    scrollState: LazyListState,
    paginatorUiState: PaginatorUiState,
    paginatorUiActions: PaginatorUiActions,
    catalogUiState: CatalogCategoryUiState,
    equipmentListUiActions: CatalogListUiActions.Equipment,
    possessionsListUiActions: CatalogListUiActions.Possessions,
    achievementsListUiActions: CatalogListUiActions.Achievements,
    displayUiState: CatalogDisplayUiState,
    displayUiActions: CatalogDisplayUiActions
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
            when(catalogUiState.catalog) {
                is CatalogCategory.Equipment -> {

                    CatalogEquipmentList(
                        scrollState = scrollState,
                        catalogUiState = catalogUiState.catalog,
                        listUiActions = equipmentListUiActions
                    )

                    if(displayUiState is CatalogDisplayUiState.Equipment) {
                        CatalogEquipmentItemDisplay(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(.8f)
                                .align(Alignment.CenterStart),
                            displayUiState = displayUiState,
                            displayUiActions = displayUiActions
                        )
                    }
                }
                is CatalogCategory.Possessions -> {

                    CatalogPossessionsList(
                        scrollState = scrollState,
                        catalogUiState = catalogUiState.catalog,
                        listUiActions = possessionsListUiActions
                    )

                    if(displayUiState is CatalogDisplayUiState.Possessions) {
                        CatalogPossessionItemDisplay(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(.8f)
                                .align(Alignment.CenterStart),
                            displayUiState = displayUiState,
                            displayUiActions = displayUiActions
                        )
                    }
                }
                is CatalogCategory.Achievements -> {

                    CatalogAchievementsList(
                        scrollState = scrollState,
                        catalogUiState = catalogUiState.catalog,
                        listUiActions = achievementsListUiActions
                    )

                    if(displayUiState is CatalogDisplayUiState.Achievements) {
                        CatalogAchievementItemDisplay(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(.8f)
                                .align(Alignment.CenterStart),
                            displayUiState = displayUiState,
                            displayUiActions = displayUiActions
                        )
                    }
                }
                else -> { /* DO NOTHING */ }
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
                    //val newIndex = (images.size * scrollUiState.offset).toInt()

                    paginatorUiActions.onScrollUpdate(newOffset, paginatorUiState.scrollUiState.itemIndex)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    val newOffset = (it.y / (paginatorHeight - 1)).coerceIn(0f, 1f)
                    //val newIndex = (images.size * scrollUiState.offset).toInt()

                    paginatorUiActions.onScrollUpdate(newOffset, paginatorUiState.scrollUiState.itemIndex)
                    //paginatorUiActions.onScrollUpdate(newOffset, newIndex)
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

                    paginatorUiActions.onScrollUpdate(newOffset, paginatorUiState.scrollUiState.itemIndex)
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    val newOffset = (it.x / (paginatorWidth - 1)).coerceIn(0f, 1f)

                    paginatorUiActions.onScrollUpdate(newOffset, paginatorUiState.scrollUiState.itemIndex)
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
