package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.icon.vectors.getShopCostVector
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.ClassicPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.type.ClassicTypography

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

@Preview(showSystemUi = false)
@Composable
private fun Preview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Box(modifier = Modifier.padding(12.dp)) {
            ShopCostIcon()
        }
    }
}

