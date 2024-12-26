package com.tritiumgaming.phasmophobiaevidencepicker.activities.compose.pet

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
@Preview
private fun PETScreenPreview() {
    PETScreen{}
}

@Composable
fun PETScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Surface(
        modifier = modifier
    ) {

        content()

    }

}