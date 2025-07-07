package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen


@Composable
@Preview
private fun MapViewerScreenPreview() {
    MapViewerScreen()
}

@Composable
fun MapViewerScreen(
    navController: NavHostController = rememberNavController(),
    //content: @Composable () -> Unit
) {

    OperationScreen(
        navController = navController,
        content = { MapViewerContent() }
    )

}

@Composable
private fun MapViewerContent() {}