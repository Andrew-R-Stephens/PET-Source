package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.utilities.codex

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen


@Composable
@Preview
private fun CodexScreenPreview() {
    CodexScreen{}
}

@Composable
fun CodexScreen(
    content: @Composable () -> Unit
) {

    OperationScreen(
        content = { content() }
    )

}
