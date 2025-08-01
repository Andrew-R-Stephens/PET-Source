package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.LocalTypography
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.MapsViewModel


@Composable
@Preview
private fun MapViewerScreenPreview() {
    SelectiveTheme {
        MapViewerScreen(
            mapsViewModel = viewModel ( factory = MapsViewModel.Factory ),
            mapId = "0"
        )
    }
}

@Composable
fun MapViewerScreen(
    navController: NavHostController = rememberNavController(),
    mapsViewModel: MapsViewModel,
    mapId: String
) {

    OperationScreen(
        navController = navController,
    ) {
        MapViewerContent(
            mapsViewModel = mapsViewModel,
            mapId = mapId
        )
    }

}

@Composable
private fun MapViewerContent(
    mapsViewModel: MapsViewModel,
    mapId: String
) {

    Log.d("MapViewerScreen", "mapId: $mapId")

    Text(
        text = "$mapId " +
                "${mapsViewModel.currentMapIndex} " +
                "${mapsViewModel.currentSimpleMap} " +
                "${mapsViewModel.currentComplexMap}",
        style = LocalTypography.current.primary.regular,
        color = LocalPalette.current.textFamily.primary
    )

}