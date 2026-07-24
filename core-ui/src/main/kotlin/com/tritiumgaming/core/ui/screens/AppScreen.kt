package com.tritiumgaming.core.ui.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tritiumgaming.core.ui.theme.LocalPalette

@Composable
@Preview
private fun AppScreenPreview() {
    AppScreen{}
}

@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxSize(),
        color = LocalPalette.current.surface
    ) {
        content()
    }

}