package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.navigation.NavRoute
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.config.DeviceConfiguration
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.app.mappers.toDrawableResource
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
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mapsViewModel: MapsViewModel,
) {
    val rememberLazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = rememberLazyGridState,
        columns = GridCells.Fixed(2),

    ) {
        itemsIndexed(mapsViewModel.mapThumbnails) { index, thumbnail ->
            MapCard(
                drawableResource = thumbnail.toDrawableResource()
            ) {
                Log.d("MapMenuScreen", "click!!")
                mapsViewModel.currentMapIndex = index
                val id = mapsViewModel.currentSimpleMap.mapId
                Log.d("MapMenuScreen", "mapId: $id")
                navController.navigate(route = "${NavRoute.SCREEN_MAP_VIEWER.route}/${id}")
            }
        }
    }

}

@Composable
private fun MapMenuContentLandscape(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    mapsViewModel: MapsViewModel,
) {
    val rememberLazyGridState = rememberLazyGridState()

    LazyHorizontalGrid(
        state = rememberLazyGridState,
        rows = GridCells.Adaptive(minSize = 128.dp)
    ) {
        itemsIndexed(mapsViewModel.mapThumbnails) { index, thumbnail ->
            MapCard(
                drawableResource = thumbnail.toDrawableResource()
            ) {
                Log.d("MapMenuScreen", "click!!")
                mapsViewModel.currentMapIndex = index
                val id = mapsViewModel.currentSimpleMap.mapId
                Log.d("MapMenuScreen", "mapId: $id")
                navController.navigate(route = "${NavRoute.SCREEN_MAP_VIEWER.route}/${id}")
            }

        }
    }

}

@Composable
private fun MapCard(
    modifier: Modifier = Modifier,
    drawableResource: Int,
    onClick: () -> Unit = {}
) {

    Card (
        modifier = modifier
            .padding(8.dp),
        onClick = {
            Log.d("MapMenuScreen", "click!")
            onClick()
        }
    ) {
        Image(
            painter = painterResource(drawableResource),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

}