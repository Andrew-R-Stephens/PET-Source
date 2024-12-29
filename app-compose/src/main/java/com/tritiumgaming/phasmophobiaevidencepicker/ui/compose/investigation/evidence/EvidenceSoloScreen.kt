package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.evidence

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
private fun EvidenceSoloScreenPreview() {
    EvidenceSoloScreen()
}

@Composable
fun EvidenceSoloScreen(
    //content: @Composable () -> Unit
) {

    EvidenceScreen (
        content = { EvidenceSoloContent() }
    )

}

@Composable
private fun EvidenceSoloContent() {}