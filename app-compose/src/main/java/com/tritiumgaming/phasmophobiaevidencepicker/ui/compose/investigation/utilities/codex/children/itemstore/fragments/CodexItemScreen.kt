package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.children.itemstore.fragments

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.CodexScreen


@Composable
@Preview
private fun CodexItemScreenPreview() {
    CodexItemScreen{}
}

@Composable
fun CodexItemScreen(
    content: @Composable () -> Unit
) {

    CodexScreen (
        content = content
    )

}