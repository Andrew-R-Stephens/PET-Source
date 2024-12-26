package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.pet

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

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