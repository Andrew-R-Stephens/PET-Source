package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.itemstore

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex.CodexScreen


@Composable
@Preview
private fun CodexItemScreenPreview() {
    CodexItemScreen{}
}

@Composable
fun CodexItemScreen(
    content: @Composable () -> Unit
) {

    CodexScreen(
        content = content
    )

}