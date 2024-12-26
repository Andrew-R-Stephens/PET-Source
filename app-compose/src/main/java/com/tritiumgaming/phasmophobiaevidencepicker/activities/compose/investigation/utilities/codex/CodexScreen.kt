package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.utilities.codex

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.InvestigationScreen


@Composable
@Preview
private fun CodexScreenPreview() {
    CodexScreen{}
}

@Composable
fun CodexScreen(
    content: @Composable () -> Unit
) {

    InvestigationScreen (
        content = { content() }
    )

}
