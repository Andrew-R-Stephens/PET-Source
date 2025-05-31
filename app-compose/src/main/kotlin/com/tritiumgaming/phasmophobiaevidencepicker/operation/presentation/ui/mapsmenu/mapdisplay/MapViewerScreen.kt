package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu.mapdisplay

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen


@Composable
@Preview
private fun MapViewerScreenPreview() {
    MapViewerScreen()
}

@Composable
fun MapViewerScreen(
    //content: @Composable () -> Unit
) {

    OperationScreen(
        content = { MapViewerContent() }
    )

}

@Composable
private fun MapViewerContent() {}