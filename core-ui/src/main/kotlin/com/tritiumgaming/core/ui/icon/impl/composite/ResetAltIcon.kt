package com.tritiumgaming.core.ui.icon.impl.composite

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.ConfigIcon
import com.tritiumgaming.core.ui.vector.color.IconVectorColors

@Composable
fun ResetAltIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults(),
    subImage: @Composable (Modifier, IconVectorColors) -> Unit = { _, _ -> }
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.ic_reset_alt),
            colorFilter = ColorFilter.tint(colors.fillColor),
            contentDescription = ""
        )

        Box(
            modifier = Modifier.fillMaxSize(.75f),
            contentAlignment = Alignment.BottomEnd
        ) {
            subImage(
                Modifier
                    .fillMaxSize(.5f),
                colors
            )
        }
    }
}

@Composable
fun ResetAltIcon(
    modifier: Modifier = Modifier,
    colors: IconVectorColors = IconVectorColors.defaults(),
    @DrawableRes subImage: Int
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.ic_reset_alt),
            colorFilter = ColorFilter.tint(colors.fillColor),
            contentDescription = ""
        )

        Box(
            modifier = Modifier.fillMaxSize(.75f),
            contentAlignment = Alignment.BottomEnd
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize(.5f),
                painter = painterResource(subImage),
                colorFilter = ColorFilter.tint(colors.fillColor),
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
private fun PreviewA() {
    ResetAltIcon(
        modifier = Modifier
            .size(48.dp),
        colors = IconVectorColors.defaults().copy(
            fillColor = Color.White,
            strokeColor = Color.White
        )
    ) { modifier, colors ->
        ConfigIcon(
            modifier = modifier,
            colors = colors
        )
    }
}
@Preview
@Composable
private fun PreviewB() {
    ResetAltIcon(
        modifier = Modifier
            .size(48.dp),
        colors = IconVectorColors.defaults().copy(
            fillColor = Color.White,
            strokeColor = Color.White
        ),
        R.drawable.ic_footprints
    )
}