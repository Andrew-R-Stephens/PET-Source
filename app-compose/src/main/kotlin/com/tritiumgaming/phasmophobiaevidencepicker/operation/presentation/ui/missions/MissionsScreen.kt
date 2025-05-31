package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.missions

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen


@Composable
@Preview
private fun MissionsScreenPreview() {
    MissionsScreen()
}

@Composable
fun MissionsScreen(
    //content: @Composable () -> Unit
) {

    OperationScreen(
        content = { MissionsContent() }
    )

}

@Composable
private fun MissionsContent() {

}
