package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.mapsmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.InvestigationScreen


@Composable
@Preview
private fun MapMenuScreenPreview() {
    MapMenuScreen()
}

@Composable
fun MapMenuScreen(
    //content: @Composable () -> Unit
) {

    InvestigationScreen (
        content = { MapMenuContent() }
    )

}

@Composable
private fun MapMenuContent() {

}