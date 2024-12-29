package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.children.itemstore.fragments.children

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.children.itemstore.fragments.CodexItemScreen

@Composable
@Preview
private fun CodexPossessionsScreenPreview() {
    CodexEquipmentScreen()
}

@Composable
fun CodexEquipmentScreen(
    //content: @Composable () -> Unit
) {

    CodexItemScreen (
        content = { CodexEquipmentContent() }
    )

}

@Composable
private fun CodexEquipmentContent() {}