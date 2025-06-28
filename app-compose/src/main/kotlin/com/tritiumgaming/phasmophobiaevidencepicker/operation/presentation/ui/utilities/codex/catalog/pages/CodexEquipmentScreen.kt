package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.pages

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.catalog.CodexItemScreen

@Composable
@Preview
private fun CodexPossessionsScreenPreview() {
    CodexEquipmentScreen()
}

@Composable
fun CodexEquipmentScreen(
    //content: @Composable () -> Unit
) {

    CodexItemScreen(
        content = { CodexEquipmentContent() }
    )

}

@Composable
private fun CodexEquipmentContent() {}