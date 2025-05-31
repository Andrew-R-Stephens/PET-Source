package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.mapsmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen


@Composable
@Preview
private fun MapMenuScreenPreview() {
    MapMenuScreen()
}

@Composable
fun MapMenuScreen(
    //content: @Composable () -> Unit
) {

    OperationScreen(
        content = { MapMenuContent() }
    )

}

@Composable
private fun MapMenuContent() {

}