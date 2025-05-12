package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.OperationScreen

@Composable
@Preview
private fun InvestigationScreenPreview() {
    OperationScreen {}
}

@Composable
fun InvestigationScreen(
    content: @Composable () -> Unit
) {

    OperationScreen(
        content = { content() }
    )

}
