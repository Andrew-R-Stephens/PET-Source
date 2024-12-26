package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.mapsmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.InvestigationScreen


@Composable
@Preview
private fun MapMenuScreenPreview() {
    MapMenuScreen{}
}

@Composable
fun MapMenuScreen(
    content: @Composable () -> Unit
) {

    InvestigationScreen (
        content = { content() }
    )

}
