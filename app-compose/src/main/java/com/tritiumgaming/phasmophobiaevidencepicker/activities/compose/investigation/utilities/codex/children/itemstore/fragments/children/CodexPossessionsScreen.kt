package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.utilities.codex.children.itemstore.fragments.children

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.investigation.utilities.codex.children.itemstore.fragments.CodexItemScreen


@Composable
@Preview
private fun CodexPossessionsScreenPreview() {
    CodexPossessionsScreen()
}

@Composable
fun CodexPossessionsScreen(
    //content: @Composable () -> Unit
) {

    CodexItemScreen (
        content = { CodexPossessionsContent() }
    )

}

@Composable
private fun CodexPossessionsContent() {}