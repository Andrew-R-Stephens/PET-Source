package com.tritiumgaming.core.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.theme.palette.LocalPalette
import com.tritiumgaming.core.ui.vector.getShopCostVector

@Composable
fun ShopCostIcon(
    modifier: Modifier = Modifier
) {

    val vector = getShopCostVector(
        listOf(
            LocalPalette.current.textFamily.body,
            LocalPalette.current.background.color,
        )
    )

    Image(
        modifier = modifier,
        imageVector = vector,
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}
