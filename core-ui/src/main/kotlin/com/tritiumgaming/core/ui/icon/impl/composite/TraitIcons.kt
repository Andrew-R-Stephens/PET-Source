package com.tritiumgaming.core.ui.icon.impl.composite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.impl.base.ArrowKeyboardDownSingleIcon
import com.tritiumgaming.core.ui.icon.impl.base.ArrowKeyboardUpSingleIcon
import com.tritiumgaming.core.ui.icon.impl.base.GeneticsIcon
import com.tritiumgaming.core.ui.icon.impl.base.MarkCheckCircleIcon
import com.tritiumgaming.core.ui.icon.impl.base.MarkXCircleIcon
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors

@Composable
private fun TraitIcon(
    modifier: Modifier = Modifier,
    badgeAlignment: Alignment = Alignment.BottomEnd,
    baseComponent: @Composable BoxScope.(modifier: Modifier) -> Unit = {},
    badgeComponent: @Composable BoxScope.(modifier: Modifier) -> Unit = {}
) {
    Box(
        modifier = modifier
            .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen),
        contentAlignment = Alignment.Center,
    ) {
        baseComponent(Modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .align(Alignment.Center)
        )

        badgeComponent(Modifier
            .fillMaxSize(.6f)
            .align(badgeAlignment)
            .drawWithCache {
                val path = Path()
                path.addOval(
                    Rect(
                        topLeft = Offset.Zero,
                        bottomRight = Offset(size.width, size.height)
                    )
                )
                onDrawWithContent {
                    val dotSize = size.width * .4f
                    // Clear the baseComponent area behind the badge
                    drawCircle(
                        Color.Black,
                        radius = dotSize * 1.2f,
                        center = Offset(
                            x = size.width / 2f,
                            y = size.height / 2f
                        ),
                        blendMode = BlendMode.Clear
                    )

                    clipPath(path) {
                        this@onDrawWithContent.drawContent()
                    }
                }
            }
        )
    }

}

@Composable
fun TraitConfirmIcon(
    modifier: Modifier = Modifier,
    color: Color
) {
    TraitIcon(
        modifier = modifier,
        baseComponent = { modifier ->
            GeneticsIcon(
                modifier = modifier,
                colors = IconVectorColors.defaults(
                    strokeColor = color,
                    fillColor = color
                ),
            )
        }
    ) { modifier ->
        MarkCheckCircleIcon(
            modifier = modifier,
            filled = true,
            color = color
        )
    }
}

@Composable
fun TraitRejectIcon(
    modifier: Modifier = Modifier,
    color: Color
) {
    TraitIcon(
        modifier = modifier,
        baseComponent = { modifier ->
            GeneticsIcon(
                modifier = modifier,
                colors = IconVectorColors.defaults(
                    strokeColor = color,
                    fillColor = color
                ),
            )
        }
    ) { modifier ->
        MarkXCircleIcon(
            modifier = modifier,
            filled = true,
            color = color
        )
    }
}

@Composable
fun TraitLikelyIcon(
    modifier: Modifier = Modifier,
    color: Color
) {
    TraitIcon(
        modifier = modifier,
        baseComponent = { modifier ->
            GeneticsIcon(
                modifier = modifier,
                colors = IconVectorColors.defaults(
                    strokeColor = color,
                    fillColor = color
                ),
            )
        }
    ) { modifier ->
        ArrowKeyboardUpSingleIcon(
            modifier = modifier,
            color = color
        )
    }
}

@Composable
fun TraitUnlikelyIcon(
    modifier: Modifier = Modifier,
    color: Color
) {
    TraitIcon(
        modifier = modifier,
        baseComponent = { modifier ->
            GeneticsIcon(
                modifier = modifier,
                colors = IconVectorColors.defaults(
                    strokeColor = color,
                    fillColor = color
                ),
            )
        }
    ) { modifier ->
        ArrowKeyboardDownSingleIcon(
            modifier = modifier,
            color = color
        )
    }
}

@Preview
@Composable
private fun Preview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        Column(
            modifier = Modifier
                .background(Color.Magenta)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TraitConfirmIcon(
                modifier = Modifier
                    .size(48.dp),
                color = LocalPalette.current.onSurface
            )
            TraitRejectIcon(
                modifier = Modifier.size(48.dp),
                color = LocalPalette.current.onSurface
            )
            TraitLikelyIcon(
                modifier = Modifier.size(48.dp),
                color = LocalPalette.current.onSurface
            )
            TraitUnlikelyIcon(
                modifier = Modifier.size(48.dp),
                color = LocalPalette.current.onSurface
            )
        }
    }
}
