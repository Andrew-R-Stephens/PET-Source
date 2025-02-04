package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.investigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.compose.pet.screens.PETScreen


@Composable
@Preview
private fun InvestigationScreenPreview() {
    InvestigationScreen {}
}

@Composable
fun InvestigationScreen(
    content: @Composable () -> Unit
) {

    PETScreen(
        content = content
    )

}