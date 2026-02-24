package com.tritiumgaming.core.ui.icon.impl.composite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.vector.getMarkCheckVector
import com.tritiumgaming.core.ui.vector.getMarkPriorityVector
import com.tritiumgaming.core.ui.vector.getMarkXVector

@Composable
fun MarkCheckCircleIcon(
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
            imageVector = getMarkCheckVector(
                colors = IconVectorColors.defaults(
                    fillColor = onColor
                )
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun MarkXCircleIcon(
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
            imageVector = getMarkXVector(
                colors = IconVectorColors.defaults(
                    fillColor = onColor
                )
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit
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
            MarkCheckCircleIcon(
                modifier = Modifier
                    .size(48.dp),
                color = LocalPalette.current.surfaceContainer,
                onColor = LocalPalette.current.onSurface
            )
            MarkXCircleIcon(
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
