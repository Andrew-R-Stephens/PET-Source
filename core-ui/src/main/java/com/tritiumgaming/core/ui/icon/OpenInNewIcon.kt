package com.tritiumgaming.core.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.vector.getOpenInNewVector

@Composable
fun OpenInNewIcon(
    modifier: Modifier = Modifier
) {

    val vector = getOpenInNewVector(
        listOf(
            LocalPalette.current.background.color,
            LocalPalette.current.textFamily.body
        )
    )

    Image(
        modifier = modifier,
        imageVector = vector,
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
