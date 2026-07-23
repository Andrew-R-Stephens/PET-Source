package com.tritiumgaming.feature.home.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.screens.AppScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AppScreen(
        modifier = modifier
            .safeDrawingPadding()
            .padding(horizontal = 8.dp)
    ) {
        content()
    }
}