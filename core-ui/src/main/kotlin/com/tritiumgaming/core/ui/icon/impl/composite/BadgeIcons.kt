package com.tritiumgaming.core.ui.icon.impl.composite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.icon.impl.base.MarkCheckCircleIcon
import com.tritiumgaming.core.ui.icon.impl.base.MarkXCircleIcon
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getMarkCheckVector
import com.tritiumgaming.core.ui.vector.getMarkPriorityVector
import com.tritiumgaming.core.ui.vector.getMarkXFilledVector
import com.tritiumgaming.core.ui.vector.getMarkXVector

@Composable
fun BadgeIcon(
    modifier: Modifier = Modifier,
    badgeAlignment: Alignment = Alignment.BottomEnd,
    baseComponent: @Composable BoxScope.(modifier: Modifier) -> Unit = {},
    badgeComponent: @Composable BoxScope.() -> Unit = {}
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            baseComponent(Modifier
                .fillMaxSize()
                .aspectRatio(1f)
            )
        }

        Box(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize(.4f)
                .align(badgeAlignment)
        ) {
            badgeComponent()
        }
    }

}

@Composable
fun MarkCheckCircleIconComposite(
    modifier: Modifier = Modifier,
    filled: Boolean = false,
    color: Color,
    onColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = color,
                shape = CircleShape
            )
            .border(1.dp, onColor, CircleShape)
    ) {
        val mod = Modifier
            .fillMaxSize(.8f)
            .align(Alignment.Center)

        MarkCheckCircleIcon(
            modifier = mod,
            filled = filled,
            color = onColor
        )
    }
}

@Composable
fun MarkXCircleIconComposite(
    modifier: Modifier = Modifier,
    filled: Boolean = false,
    color: Color,
    onColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = color,
                shape = CircleShape
            )
            .border(1.dp, onColor, CircleShape)
    ) {
        val mod = Modifier
            .fillMaxSize(.8f)
            .align(Alignment.Center)

        MarkXCircleIcon(
            modifier = mod,
            filled = filled,
            color = onColor
        )
    }
}

@Composable
fun MarkPriorityCircleIcon(
    modifier: Modifier = Modifier,
    color: Color,
    onColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = color,
                shape = CircleShape
            )
            .border(1.dp, onColor, CircleShape)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(.8f)
                .align(Alignment.Center),
            imageVector = getMarkPriorityVector(
                colors = IconVectorColors.defaults(
                    fillColor = onColor
                )
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit
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
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MarkCheckCircleIconComposite(
                modifier = Modifier
                    .size(48.dp),
                color = LocalPalette.current.surfaceContainer,
                onColor = LocalPalette.current.onSurface
            )
            MarkXCircleIconComposite(
                modifier = Modifier.size(48.dp),
                color = LocalPalette.current.surfaceContainer,
                onColor = LocalPalette.current.onSurface
            )
            MarkPriorityCircleIcon(
                modifier = Modifier.size(48.dp),
                color = LocalPalette.current.surfaceContainer,
                onColor = LocalPalette.current.onSurface
            )
        }
    }
}
