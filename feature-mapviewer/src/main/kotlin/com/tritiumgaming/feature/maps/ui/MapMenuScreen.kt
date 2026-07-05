package com.tritiumgaming.feature.maps.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.feature.maps.app.mappers.map.toDrawableResource
import com.tritiumgaming.feature.maps.app.mappers.map.toStringResource
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.map.simple.model.SimpleWorldMap

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Small Phone", device = "id:small_phone")
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
@Preview(name = "Medium Phone Portrait", device = "spec:width=411dp,height=891dp")
@Preview(name = "Medium Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Medium Tablet Portrait", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private annotation class DevicePreviews

@DevicePreviews
@Composable
private fun MapMenuScreenPreview() {
    LocalThemeProvider {
        Surface(
            color = LocalPalette.current.surface
        ) {
            MapMenuContent(
                mapMenuUiState = MapMenuUiState(
                    maps = listOf(
                        SimpleWorldMap(
                            mapId = "tanglewood",
                            mapName = SimpleMapResources.MapTitle.TANGLEWOOD,
                            mapSize = MapModifierResources.MapSize.SMALL,
                            thumbnailImage = SimpleMapResources.MapThumbnail.TANGLEWOOD,
                            mapFloors = emptyList(),
                            defaultFloor = 0
                        ),
                        SimpleWorldMap(
                            mapId = "prison",
                            mapName = SimpleMapResources.MapTitle.PRISON,
                            mapSize = MapModifierResources.MapSize.LARGE,
                            thumbnailImage = SimpleMapResources.MapThumbnail.PRISON,
                            mapFloors = emptyList(),
                            defaultFloor = 0
                        ),
                        SimpleWorldMap(
                            mapId = "tanglewood",
                            mapName = SimpleMapResources.MapTitle.TANGLEWOOD,
                            mapSize = MapModifierResources.MapSize.SMALL,
                            thumbnailImage = SimpleMapResources.MapThumbnail.TANGLEWOOD,
                            mapFloors = emptyList(),
                            defaultFloor = 0
                        ),
                        SimpleWorldMap(
                            mapId = "prison",
                            mapName = SimpleMapResources.MapTitle.PRISON,
                            mapSize = MapModifierResources.MapSize.LARGE,
                            thumbnailImage = SimpleMapResources.MapThumbnail.PRISON,
                            mapFloors = emptyList(),
                            defaultFloor = 0
                        ),
                        SimpleWorldMap(
                            mapId = "tanglewood",
                            mapName = SimpleMapResources.MapTitle.TANGLEWOOD,
                            mapSize = MapModifierResources.MapSize.SMALL,
                            thumbnailImage = SimpleMapResources.MapThumbnail.TANGLEWOOD,
                            mapFloors = emptyList(),
                            defaultFloor = 0
                        ),
                        SimpleWorldMap(
                            mapId = "prison",
                            mapName = SimpleMapResources.MapTitle.PRISON,
                            mapSize = MapModifierResources.MapSize.LARGE,
                            thumbnailImage = SimpleMapResources.MapThumbnail.PRISON,
                            mapFloors = emptyList(),
                            defaultFloor = 0
                        ),
                        SimpleWorldMap(
                            mapId = "tanglewood",
                            mapName = SimpleMapResources.MapTitle.TANGLEWOOD,
                            mapSize = MapModifierResources.MapSize.SMALL,
                            thumbnailImage = SimpleMapResources.MapThumbnail.TANGLEWOOD,
                            mapFloors = emptyList(),
                            defaultFloor = 0
                        ),
                        SimpleWorldMap(
                            mapId = "prison",
                            mapName = SimpleMapResources.MapTitle.PRISON,
                            mapSize = MapModifierResources.MapSize.LARGE,
                            thumbnailImage = SimpleMapResources.MapThumbnail.PRISON,
                            mapFloors = emptyList(),
                            defaultFloor = 0
                        )
                    )
                ),
                onSelect = {}
            )
        }
    }
}

@Composable
fun MapMenuScreen(
    navController: NavHostController = rememberNavController(),
    mapsScreenViewModel: MapsScreenViewModel
) {

    val mapMenuUiState = MapMenuUiState(
        maps = mapsScreenViewModel.simpleMaps
    )

    val onSelect: (SimpleWorldMap) -> Unit = { map ->
            navController.navigate(
                route = "${NavRoute.SCREEN_MAP_VIEWER.route}/${map.mapId}") }

    MapMenuContent(mapMenuUiState, onSelect)

}

@Composable
private fun MapMenuContent(
    mapMenuUiState: MapMenuUiState,
    onSelect: (SimpleWorldMap) -> Unit
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when (deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> {
            MapMenuContentPortrait(
                modifier = Modifier,
                mapMenuUiState = mapMenuUiState,
                rows = 2,
                onSelect = { map -> onSelect(map) }
            )
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            MapMenuContentLandscape(
                modifier = Modifier,
                mapMenuUiState = mapMenuUiState,
                columns = 2,
                onSelect = { map -> onSelect(map) }
            )
        }

        DeviceConfiguration.TABLET_PORTRAIT -> {
            MapMenuContentPortrait(
                modifier = Modifier,
                mapMenuUiState = mapMenuUiState,
                rows = 4,
                onSelect = { map -> onSelect(map) }
            )
        }

        DeviceConfiguration.TABLET_LANDSCAPE -> {
            MapMenuContentLandscape(
                modifier = Modifier,
                mapMenuUiState = mapMenuUiState,
                columns = 3,
                onSelect = { map -> onSelect(map) }
            )
        }

        DeviceConfiguration.DESKTOP -> {
            MapMenuContentLandscape(
                modifier = Modifier,
                mapMenuUiState = mapMenuUiState,
                columns = 4,
                onSelect = { map -> onSelect(map) }
            )
        }
    }
}

@Composable
private fun MapMenuContentPortrait(
    modifier: Modifier = Modifier,
    mapMenuUiState: MapMenuUiState,
    rows: Int = 2,
    onSelect: (SimpleWorldMap) -> Unit
) {
    val rememberLazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        modifier = modifier,
        state = rememberLazyGridState,
        columns = GridCells.Fixed(rows)
    ) {
        mapCardGrids(
            modifier = Modifier.fillMaxWidth(),
            mapMenuUiState = mapMenuUiState,
            onClick = { map -> onSelect(map) }
        )
    }

}

@Composable
private fun MapMenuContentLandscape(
    modifier: Modifier = Modifier,
    mapMenuUiState: MapMenuUiState,
    columns: Int = 2,
    onSelect: (SimpleWorldMap) -> Unit,
) {
    val rememberLazyGridState = rememberLazyGridState()

    LazyHorizontalGrid(
        modifier = modifier,
        state = rememberLazyGridState,
        rows = GridCells.Fixed(count = columns)
    ) {
        mapCardGrids(
            modifier = Modifier,
            mapMenuUiState = mapMenuUiState,
            onClick = { map -> onSelect(map) }
        )
    }

}

private fun LazyGridScope.mapCardGrids(
    modifier: Modifier = Modifier,
    mapMenuUiState: MapMenuUiState,
    onClick: (SimpleWorldMap) -> Unit = {}
) {
    items(items = mapMenuUiState.maps) { map ->
        MapCard(
            modifier = modifier,
            title = map.mapName,
            thumbnail = map.thumbnailImage,
            onClick = { onClick(map) }
        )
    }
}

@Composable
private fun MapCard(
    modifier: Modifier = Modifier,
    title: SimpleMapResources.MapTitle,
    thumbnail: SimpleMapResources.MapThumbnail,
    onClick: () -> Unit = {}
) {

    Card (
        modifier = modifier
            .padding(8.dp)
            .aspectRatio(1.5f),
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalPalette.current.surfaceContainerHigh
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(thumbnail.toDrawableResource()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                alignment = Alignment.TopCenter
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(LocalPalette.current.surfaceContainerHigh)
                    .padding(8.dp)
                    .basicMarquee(
                        iterations = Int.MAX_VALUE,
                        initialDelayMillis = 1000,
                        repeatDelayMillis = 1000,
                    ),
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

}