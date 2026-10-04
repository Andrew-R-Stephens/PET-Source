package com.tritiumgaming.core.ui.widgets.indicator

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun InfiniteThrobber(
    color1: Color,
    color2: Color,
    isLoading: Boolean = true
) {
    var loading by remember { mutableStateOf(isLoading) }

    if (!loading) return

    CircularProgressIndicator(
        color = color1,
        trackColor = color2,
    )
}
