package com.tritiumgaming.feature.maps.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.maps.app.mappers.map.toDrawableResource
import com.tritiumgaming.feature.maps.app.mappers.map.toStringResource
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.map.simple.model.SimpleWorldMap

@Composable
fun MapMenuScreen(
    navController: NavHostController = rememberNavController(),
    mapsScreenViewModel: MapsScreenViewModel
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val mapMenuUiState = MapMenuUiState(
        maps = mapsScreenViewModel.simpleMaps
    )

    val onSelect: (SimpleWorldMap) -> Unit = { map ->
            navController.navigate(
                route = "${NavRoute.SCREEN_MAP_VIEWER.route}/${map.mapId}") }

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            MapMenuContentPortrait(
                mapMenuUiState = mapMenuUiState,
                rows = 2,
                onSelect = { map -> onSelect(map) }
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            MapMenuContentLandscape(
                mapMenuUiState = mapMenuUiState,
                columns = 2,
                onSelect = { map -> onSelect(map) }
            )
        }
        DeviceConfiguration.TABLET_PORTRAIT,
        DeviceConfiguration.TABLET_LANDSCAPE -> {
            MapMenuContentLandscape(
                mapMenuUiState = mapMenuUiState,
                columns = 3,
                onSelect = { map -> onSelect(map) }
            )
        }
        DeviceConfiguration.DESKTOP -> {
            MapMenuContentLandscape(
                mapMenuUiState = mapMenuUiState,
                columns = 4,
                onSelect = { map -> onSelect(map) }
            )
        }
    }

}

@Composable
private fun MapMenuContentPortrait(
    mapMenuUiState: MapMenuUiState,
    rows: Int = 2,
    onSelect: (SimpleWorldMap) -> Unit
) {
    val rememberLazyGridState = rememberLazyGridState()

    var maxWidth by remember { mutableStateOf(Dp.Unspecified) }
    val density = LocalDensity.current

    LazyVerticalGrid(
        state = rememberLazyGridState,
        columns = GridCells.Fixed(rows)
    ) {
        mapCardGrid(
            mapMenuUiState = mapMenuUiState,
            maxWidth = Dp.Unspecified,
            maxHeight = Dp.Unspecified,
            onClick = { map -> onSelect(map) },
            onCardSizeChanged =  { width, _ ->
                maxWidth = with(density) { width.toDp() } }
        )
    }

}

@Composable
private fun MapMenuContentLandscape(
    mapMenuUiState: MapMenuUiState,
    columns: Int = 2,
    onSelect: (SimpleWorldMap) -> Unit,
) {
    val rememberLazyGridState = rememberLazyGridState()

    var maxWidth by remember { mutableStateOf(Dp.Unspecified) }
    val density = LocalDensity.current

    LazyHorizontalGrid(
        state = rememberLazyGridState,
        rows = GridCells.Fixed(count = columns)
    ) {
        mapCardGrid(
            mapMenuUiState = mapMenuUiState,
            maxWidth = maxWidth,
            maxHeight = Dp.Unspecified,
            onClick = { map -> onSelect(map) },
            onCardSizeChanged = { width, _ ->
                maxWidth = with(density) { width.toDp() } }
        )
    }

}

private fun LazyGridScope.mapCardGrid(
    mapMenuUiState: MapMenuUiState,
    maxWidth: Dp = Dp.Unspecified,
    maxHeight: Dp = Dp.Unspecified,
    onClick: (SimpleWorldMap) -> Unit = {},
    onCardSizeChanged: (Int, Int) -> Unit = { _, _ -> }
) {
    items(mapMenuUiState.maps) { map ->

        MapCard(
            title = map.mapName,
            thumbnail = map.thumbnailImage,
            onCardSizeChanged = { width, height ->
                onCardSizeChanged(width, height) },
            maxWidth = maxWidth,
            maxHeight = maxHeight,
            onClick = { onClick(map) }
        )

    }
}

@Composable
private fun MapCard(
    modifier: Modifier = Modifier,
    title: SimpleMapResources.MapTitle,
    thumbnail: SimpleMapResources.MapThumbnail,
    onCardSizeChanged: (Int, Int) -> Unit = {w, h ->},
    maxWidth: Dp = Dp.Unspecified,
    maxHeight: Dp = Dp.Unspecified,
    onClick: () -> Unit = {}
) {

    Card (
        modifier = modifier
            .padding(8.dp)
            .then(
                if (maxWidth == Dp.Unspecified) Modifier
                else Modifier.width(maxWidth)
            )
            .then(
                if (maxHeight == Dp.Unspecified) Modifier
                else Modifier.height(maxHeight)
            ),
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalPalette.current.surfaceContainerHigh
        )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (maxWidth == Dp.Unspecified) Modifier
                    else Modifier.weight(1f, true)
                )
                .onSizeChanged { newSize ->
                    onCardSizeChanged(newSize.width, newSize.height)
                },
            painter = painterResource(thumbnail.toDrawableResource()),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(LocalPalette.current.surfaceContainerHigh)
                .padding(8.dp)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    initialDelayMillis = 1000,
                    repeatDelayMillis = 1000,
                )
                .align(Alignment.CenterHorizontally),
            text = stringResource(title.toStringResource()),
            style = LocalTypography.current.primary.bold.copy(
                textAlign = TextAlign.Center
            ),
            color = LocalPalette.current.onSurface,
            fontSize = 24.sp,
            maxLines = 1
        )
    }

}