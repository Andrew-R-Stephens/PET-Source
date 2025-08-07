package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListScope
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.NavRoute
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.config.DeviceConfiguration
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen


@Composable
@Preview
private fun MapMenuScreenPreview() {
    MapMenuScreen(
        mapsViewModel = viewModel(factory = MapsViewModel.Factory)
    )
}

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
                    mapsViewModel = mapsViewModel
                )
            }
            DeviceConfiguration.MOBILE_LANDSCAPE,
            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP -> {
                MapMenuContentLandscape(
                    navController = navController,
                    mapsViewModel = mapsViewModel
                )
            }
        }
    }

}

@Composable
private fun MapMenuContentPortrait(
    navController: NavHostController,
    mapsViewModel: MapsViewModel,
) {
    val rememberLazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = rememberLazyGridState,
        columns = GridCells.Fixed(2),

    ) {
        mapCardGrid(
            navController = navController,
            mapsViewModel = mapsViewModel
        )
    }

}

@Composable
private fun MapMenuContentLandscape(
    navController: NavHostController,
    mapsViewModel: MapsViewModel,
) {
    val rememberLazyGridState = rememberLazyGridState()

    LazyHorizontalGrid(
        state = rememberLazyGridState,
        rows = GridCells.Adaptive(minSize = 128.dp)
    ) {
        mapCardGrid(
            navController = navController,
            mapsViewModel = mapsViewModel
        )
    }

}

private fun LazyGridScope.mapCardGrid(
    navController: NavHostController,
    mapsViewModel: MapsViewModel
) {
    itemsIndexed(mapsViewModel.simpleMaps) { index, map ->
        MapCard(
            title = map.mapName,
            thumbnail = map.thumbnailImage
        ) {
            Log.d("MapMenuScreen", "mapId: ${map.mapId}")
            navController.navigate(route = "${NavRoute.SCREEN_MAP_VIEWER.route}/${map.mapId}")
        }

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
            .padding(8.dp),
        onClick = {
            Log.d("MapMenuScreen", "click!")
            onClick()
        },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = LocalPalette.current.surface.onColor
        )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(thumbnail.toDrawableResource()),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(LocalPalette.current.surface.onColor)
                .padding(8.dp),
            text = stringResource(title.toStringResource()),
            style = LocalTypography.current.primary.bold.copy(
                textAlign = TextAlign.Center
            ),
            color = LocalPalette.current.textFamily.body,
            fontSize = 24.sp
        )
    }

}