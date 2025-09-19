package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu

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
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.tritiumgaming.core.ui.config.DeviceConfiguration
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.NavRoute
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen
import com.tritiumgaming.shared.operation.domain.map.simple.mappers.SimpleMapResources

@Composable
fun MapMenuScreen(
    navController: NavHostController = rememberNavController(),
    mapsViewModel: MapsViewModel
) {

    OperationScreen (
        navController = navController,
    ) {

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        when(deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                MapMenuContentPortrait(
                    navController = navController,
                    mapsViewModel = mapsViewModel,
                    rows = 2
                )
            }
            DeviceConfiguration.MOBILE_LANDSCAPE -> {
                MapMenuContentLandscape(
                    navController = navController,
                    mapsViewModel = mapsViewModel,
                    columns = 2
                )
            }
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE -> {
                MapMenuContentLandscape(
                    navController = navController,
                    mapsViewModel = mapsViewModel,
                    columns = 3
                )
            }
            DeviceConfiguration.DESKTOP -> {
                MapMenuContentLandscape(
                    navController = navController,
                    mapsViewModel = mapsViewModel,
                    columns = 4
                )
            }
        }
    }

}

@Composable
private fun MapMenuContentPortrait(
    navController: NavHostController,
    mapsViewModel: MapsViewModel,
    rows: Int = 2
) {
    val rememberLazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = rememberLazyGridState,
        columns = GridCells.Fixed(rows)
    ) {
        mapCardGrid(
            navController = navController,
            mapsViewModel = mapsViewModel,
            maxWidth = Dp.Unspecified,
            maxHeight = Dp.Unspecified,
        )
    }

}

@Composable
private fun MapMenuContentLandscape(
    navController: NavHostController,
    mapsViewModel: MapsViewModel,
    columns: Int = 2
) {
    val rememberLazyGridState = rememberLazyGridState()

    var maxWidth by remember { mutableStateOf(Dp.Unspecified) }
    val density = LocalDensity.current

    LazyHorizontalGrid(
        state = rememberLazyGridState,
        rows = GridCells.Fixed(count = columns)
    ) {
        mapCardGrid(
            navController = navController,
            mapsViewModel = mapsViewModel,
            onCardSizeChanged = { width, height ->
                maxWidth = with(density) { width.toDp() }
            },
            maxWidth = maxWidth,
            maxHeight = Dp.Unspecified
        )
    }

}

private fun LazyGridScope.mapCardGrid(
    navController: NavHostController,
    mapsViewModel: MapsViewModel,
    onCardSizeChanged: (Int, Int) -> Unit = {w, h ->},
    maxWidth: Dp = Dp.Unspecified,
    maxHeight: Dp = Dp.Unspecified
) {
    itemsIndexed(mapsViewModel.simpleMaps) { index, map ->

        MapCard(
            title = map.mapName,
            thumbnail = map.thumbnailImage,
            onCardSizeChanged = { width, height ->
                onCardSizeChanged(width, height)
            },
            maxWidth = maxWidth,
            maxHeight = maxHeight
        ) {
            navController.navigate(route = "${NavRoute.SCREEN_MAP_VIEWER.route}/${map.mapId}")
        }

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
                if(maxWidth == Dp.Unspecified) Modifier
                else Modifier.width(maxWidth)
            )
            .then (
                if(maxHeight == Dp.Unspecified) Modifier
                else Modifier.height(maxHeight)
            ),
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalPalette.current.surface.onColor
        )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if(maxWidth == Dp.Unspecified) Modifier
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
                .background(LocalPalette.current.surface.onColor)
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
            color = LocalPalette.current.textFamily.body,
            fontSize = 24.sp,
            maxLines = 1
        )
    }

}