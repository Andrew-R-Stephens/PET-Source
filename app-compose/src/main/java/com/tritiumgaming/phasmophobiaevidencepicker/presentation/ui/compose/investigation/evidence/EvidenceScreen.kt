package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.evidence

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation.InvestigationScreen

@Composable
@Preview
private fun EvidenceScreenPreview() {
    EvidenceScreen{}
}

@Composable
fun EvidenceScreen(
    content: @Composable () -> Unit
) {

    InvestigationScreen (
        content = { content() }
    )

}
