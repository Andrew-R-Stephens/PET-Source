package com.tritiumgaming.core.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.vector.getMarkPriorityVector

@Composable
fun MarkPriorityIcon(
    modifier: Modifier = Modifier
) {

    val vector = getMarkPriorityVector(
        listOf(
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
