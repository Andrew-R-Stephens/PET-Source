package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.evidence

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.InvestigationScreen


@Composable
@Preview
private fun EvidenceScreenPreview() {
    EvidenceScreen{}
}

@Composable
fun EvidenceScreen(
    content: @Composable () -> Unit
) {

    EvidenceScreen (
        content = { content() }
    )

}
