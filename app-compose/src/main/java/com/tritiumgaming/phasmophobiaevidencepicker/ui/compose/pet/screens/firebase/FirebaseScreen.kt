package com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.screens.firebase

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.screens.PETScreen

@Composable
@Preview
private fun FirebaseScreenPreview() {
    FirebaseScreen {}
}

@Composable
fun FirebaseScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    PETScreen(
        modifier = modifier,
        content = content
    )

}