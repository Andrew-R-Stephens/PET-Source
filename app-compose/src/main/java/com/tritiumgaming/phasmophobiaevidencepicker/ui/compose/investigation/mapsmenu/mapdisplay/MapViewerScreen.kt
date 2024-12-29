package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.mapsmenu.mapdisplay

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.InvestigationScreen


@Composable
@Preview
private fun MapViewerScreenPreview() {
    MapViewerScreen()
}

@Composable
fun MapViewerScreen(
    //content: @Composable () -> Unit
) {

    InvestigationScreen (
        content = { MapViewerContent() }
    )

}

@Composable
private fun MapViewerContent() {}