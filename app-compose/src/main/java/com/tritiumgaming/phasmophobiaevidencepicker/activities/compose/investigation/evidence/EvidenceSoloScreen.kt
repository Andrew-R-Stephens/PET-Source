package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.evidence

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.utilities.codex.children.itemstore.fragments.CodexItemScreen

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