package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.missions

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.InvestigationScreen


@Composable
@Preview
private fun MissionsScreenPreview() {
    MissionsScreen()
}

@Composable
fun MissionsScreen(
    //content: @Composable () -> Unit
) {

    InvestigationScreen (
        content = { MissionsContent() }
    )

}

@Composable
private fun MissionsContent() {

}
