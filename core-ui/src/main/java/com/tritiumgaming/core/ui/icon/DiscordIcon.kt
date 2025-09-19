package com.tritiumgaming.core.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.vector.getDiscordVector

@Composable
fun DiscordIcon(
    modifier: Modifier = Modifier,
    colors: List<Color> = emptyList()
) {

    Image(
        modifier = modifier,
        imageVector = getDiscordVector(
            listOf(
                LocalPalette.current.background.color,
                LocalPalette.current.textFamily.body
            )
        ),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}