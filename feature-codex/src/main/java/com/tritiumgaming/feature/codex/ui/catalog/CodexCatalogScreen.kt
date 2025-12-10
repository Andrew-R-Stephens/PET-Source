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
import androidx.compose.runtime.mutableIntStateOf
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
import com.tritiumgaming.shared.data.codex.mappers.CodexResources
import com.tritiumgaming.feature.codex.ui.CodexScreen
import com.tritiumgaming.feature.codex.ui.CodexViewModel
import com.tritiumgaming.feature.codex.ui.catalog.pages.CatalogAchievementListComponent
import com.tritiumgaming.feature.codex.ui.catalog.pages.CatalogEquipmentListComponent
import com.tritiumgaming.feature.codex.ui.catalog.pages.CodexAchievementDisplay
import com.tritiumgaming.feature.codex.ui.catalog.pages.CodexEquipmentDisplay
import com.tritiumgaming.feature.codex.ui.catalog.pages.CodexPossessionsDisplay
import com.tritiumgaming.feature.codex.ui.catalog.pages.CodexPossessionsListComponent

@Composable
fun CodexCatalogScreen(
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

    val rememberScrollState = rememberLazyListState()
    var rememberVisibleItems by remember { mutableIntStateOf(0) }

    val scrollUiState by codexViewModel.scrollUiState.collectAsStateWithLifecycle()
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
        navController = navController,
        headerTitle = categoryTitle,
        showBackButton = true,
        onBackClicked = {
            codexViewModel.flushCategory(category)
            navController.popBackStack()
        }
    ) {

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {

                CodexItemScreenContentPortrait(
                    codexViewModel = codexViewModel,
                    category = category,
                    scrollState = rememberScrollState
                )
            }

            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {

                CodexItemScreenContentLandscape(
                    codexViewModel = codexViewModel,
                    category = category,
                    scrollState = rememberScrollState
                )
            }
        }
    }

}

@Composable
private fun CodexItemScreenContentPortrait(
    codexViewModel: CodexViewModel,
    category: CodexResources.Category,
    scrollState: LazyListState
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        VerticalPaginator(
            modifier = Modifier
                .fillMaxHeight()
                .width(48.dp),
            codexViewModel = codexViewModel,
            images = codexViewModel.getCategoryIcons(category)
        )

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopCenter
        ) {
            when(category) {
                CodexResources.Category.EQUIPMENT -> CatalogEquipmentListComponent(
                    codexViewModel = codexViewModel,
                    scrollState = scrollState
                )
                CodexResources.Category.POSSESSIONS -> CodexPossessionsListComponent(
                    codexViewModel = codexViewModel,
                    scrollState = scrollState
                )
                CodexResources.Category.ACHIEVEMENTS -> CatalogAchievementListComponent(
                    codexViewModel = codexViewModel,
                    scrollState = scrollState
                )
            }

            when(category) {
                CodexResources.Category.EQUIPMENT -> CodexEquipmentDisplay(
                    modifier = Modifier
                        .fillMaxHeight(.8f)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    codexViewModel = codexViewModel
                )
                CodexResources.Category.POSSESSIONS -> CodexPossessionsDisplay(
                    modifier = Modifier
                        .fillMaxHeight(.8f)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    codexViewModel = codexViewModel
                )
                CodexResources.Category.ACHIEVEMENTS -> CodexAchievementDisplay(
                    modifier = Modifier
                        .fillMaxHeight(.8f)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    codexViewModel = codexViewModel
                )
            }
        }
    }
}

@Composable
private fun CodexItemScreenContentLandscape(
    codexViewModel: CodexViewModel,
    category: CodexResources.Category,
    scrollState: LazyListState
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        HorizontalPaginator(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            codexViewModel = codexViewModel,
            images = codexViewModel.getCategoryIcons(category)
        )

        Box(
            modifier = Modifier,
            contentAlignment = Alignment.TopCenter
        ) {
            when(category) {
                CodexResources.Category.EQUIPMENT -> CatalogEquipmentListComponent(
                    codexViewModel = codexViewModel,
                    scrollState = scrollState
                )
                CodexResources.Category.POSSESSIONS -> CodexPossessionsListComponent(
                    codexViewModel = codexViewModel,
                    scrollState = scrollState
                )
                CodexResources.Category.ACHIEVEMENTS -> CatalogAchievementListComponent(
                    codexViewModel = codexViewModel,
                    scrollState = scrollState
                )
            }

            when(category) {
                CodexResources.Category.EQUIPMENT -> CodexEquipmentDisplay(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(.8f)
                        .align(Alignment.CenterStart),
                    codexViewModel = codexViewModel
                )
                CodexResources.Category.POSSESSIONS -> CodexPossessionsDisplay(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(.8f)
                        .align(Alignment.CenterStart),
                    codexViewModel = codexViewModel
                )
                CodexResources.Category.ACHIEVEMENTS -> CodexAchievementDisplay(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(.8f)
                        .align(Alignment.CenterStart),
                    codexViewModel = codexViewModel
                )
            }
        }
    }
}

@Composable
private fun VerticalPaginator(
    modifier: Modifier = Modifier,
    codexViewModel: CodexViewModel,
    @DrawableRes images: List<Int>
) {
    val scrollUiState by codexViewModel.scrollUiState.collectAsStateWithLifecycle()

    var paginatorHeight by remember {
        mutableFloatStateOf(0f)
    }

    Log.d("CodexItemstoreScreen", "VerticalPaginator: ${scrollUiState.itemIndex}")

    Column(
        modifier = modifier
            .background(LocalPalette.current.surface)
            .padding(8.dp)
            .onSizeChanged { paginatorHeight = it.height.toFloat() }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    codexViewModel.setScrollOffset(
                        offset = (change.position.y / (paginatorHeight - 1)).coerceIn(0f, 1f),
                        index = (images.size * scrollUiState.offset).toInt()
                    )
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    codexViewModel.setScrollOffset(
                        offset = (it.y / (paginatorHeight - 1)).coerceIn(0f, 1f),
                        index = (images.size * scrollUiState.offset).toInt()
                    )
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
    codexViewModel: CodexViewModel,
    @DrawableRes images: List<Int>
) {

    val scrollUiState by codexViewModel.scrollUiState.collectAsStateWithLifecycle()

    var paginatorWidth by remember {
        mutableFloatStateOf(0f)
    }

    Row(
        modifier = modifier
            .background(LocalPalette.current.surface)
            .padding(8.dp)
            .onSizeChanged { paginatorWidth = it.width.toFloat() }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    codexViewModel.setScrollOffset(
                        offset = (change.position.x / (paginatorWidth - 1)).coerceIn(0f, 1f),
                        index = (images.size * scrollUiState.offset).toInt()
                    )
                }
            }
            .pointerInput(Unit) {
                detectTapGestures {
                    codexViewModel.setScrollOffset(
                        offset = (it.x / (paginatorWidth - 1)).coerceIn(0f, 1f),
                        index = (images.size * scrollUiState.offset).toInt()
                    )
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
