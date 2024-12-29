package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.codexmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.investigation.utilities.codex.CodexScreen


@Composable
@Preview
private fun CodexMenuScreenPreview() {
    CodexMenuScreen()
}

@Composable
fun CodexMenuScreen(
    //content: @Composable () -> Unit
) {

    CodexScreen (
        content = { CodexMenuContent() }
    )

}


@Composable
private fun CodexMenuContent() {}