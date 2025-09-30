package com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.icon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.vector.getMarkCheckVector
import com.tritiumgaming.core.ui.vector.getMarkPriorityVector
import com.tritiumgaming.core.ui.vector.getMarkXVector

@Composable
fun MarkCheckCircleIcon(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    foregroundColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(.8f)
                .align(Alignment.Center),
            imageVector = getMarkCheckVector(
                colors = IconVectorColors.defaults(
                    fillColor = foregroundColor
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
    backgroundColor: Color,
    foregroundColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(.8f)
                .align(Alignment.Center),
            imageVector = getMarkXVector(
                colors = IconVectorColors.defaults(
                    fillColor = foregroundColor
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
    backgroundColor: Color,
    foregroundColor: Color
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = CircleShape
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(.8f)
                .align(Alignment.Center),
            imageVector = getMarkPriorityVector(
                colors = IconVectorColors.defaults(
                    fillColor = foregroundColor
                )
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}
