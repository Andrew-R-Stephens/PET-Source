package com.tritiumgaming.core.ui.modifier

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

fun Modifier.progressGradient(
    progress: Float = .5f,
    gradientColor: Color = Color.Red,
    gradientAlpha: Float = 1f
): Modifier = this.then(
    Modifier
        // adding layer fixes issue with blending gradient and content
        .graphicsLayer { alpha = gradientAlpha }
        .drawWithContent {

            val gradientLength = 32f / size.width

            drawRect(
                brush = Brush.horizontalGradient(
                    -gradientLength to gradientColor,
                    progress to gradientColor,
                    progress+gradientLength to Color.Transparent,
                    startX = -gradientLength,
                    endX = size.width
                ),
                blendMode = BlendMode.SrcOver
            )

            drawContent()
        }
)

@Composable
@Preview
fun TestPBar() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(48.dp)
        .progressGradient(
            progress = 1f,
            gradientColor = Color.Blue,
            gradientAlpha = .5f
        )
    )
}
