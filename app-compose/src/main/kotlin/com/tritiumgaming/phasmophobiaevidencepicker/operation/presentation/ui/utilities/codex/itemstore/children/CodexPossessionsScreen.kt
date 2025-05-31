package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.itemstore.children

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.itemstore.CodexItemScreen


@Composable
@Preview
private fun CodexPossessionsScreenPreview() {
    CodexPossessionsScreen()
}

@Composable
fun CodexPossessionsScreen(
    //content: @Composable () -> Unit
) {

    CodexItemScreen(
        content = { CodexPossessionsContent() }
    )

}

@Composable
private fun CodexPossessionsContent() {}