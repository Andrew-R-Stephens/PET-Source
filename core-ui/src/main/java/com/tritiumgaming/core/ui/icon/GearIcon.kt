package com.tritiumgaming.core.ui.icon


import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.vector.getGearVector

@Composable
fun GearIcon(
    modifier: Modifier = Modifier,
) {
    Image(
        modifier = modifier,
        imageVector = getGearVector(
            listOf(
                LocalPalette.current.background.color,
                LocalPalette.current.textFamily.body
            )
        ),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
