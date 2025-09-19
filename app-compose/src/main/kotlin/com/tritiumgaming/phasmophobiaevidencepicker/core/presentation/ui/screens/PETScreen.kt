package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.core.ui.theme.palette.LocalPalette

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
            .fillMaxSize(),
        color = LocalPalette.current.surface.color
    ) {

        content()

    }

}