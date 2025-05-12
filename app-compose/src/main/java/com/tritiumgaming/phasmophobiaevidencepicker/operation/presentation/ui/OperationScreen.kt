package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.screens.PETScreen


@Composable
@Preview
private fun OperationScreenPreview() {
    OperationScreen {}
}

@Composable
fun OperationScreen(
    content: @Composable () -> Unit
) {

    PETScreen(
        content = content
    )

}