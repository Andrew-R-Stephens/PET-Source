package com.tritiumgaming.feature.investigation.ui.toolbar.subsection

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.Ghost

@Composable
fun SmudgeTimer(
    modifier:Modifier = Modifier,
    barActiveColor: Color,
    barInactiveColor: Color,
    maxTime: Long,
    timeRemaining: Long,
    possibleGhosts: List<Ghost>,
    ghostMarkerColor: Color
) {
    val deltaTime by remember {
        mutableFloatStateOf(timeRemaining.toFloat() / maxTime)
    }

    Canvas(
        modifier = modifier
    ) {

        drawRect(
            color = barInactiveColor,
            topLeft = Offset(0f, 0f),
            size = size
        )

        drawRect(
            color = barActiveColor,
            topLeft = Offset(0f, 0f),
            size = Size(size.width * deltaTime, size.height),
        )

        //possibleGhosts.forEach { ghost ->

            //val line = ghost.smudgeTime.toLongResource().toFloat() / maxTime
            val line = 1000f / maxTime

            drawLine(
                color = ghostMarkerColor,
                start = Offset(size.width * line, 0f),
                end = Offset(size.width * line, size.height),
                strokeWidth = 3f
            )

        //}

    }

    Text(
        text = "Time Remaining: $deltaTime",
    )

}

@Composable
@Preview
fun TestSmudgeTimer() {



    SelectiveTheme {
        SmudgeTimer(
            modifier = Modifier
                .height(24.dp)
                .fillMaxWidth(),
            barActiveColor = LocalPalette.current.primary,
            barInactiveColor = LocalPalette.current.onSurface,
            maxTime = 3000,
            timeRemaining = 1500,
            possibleGhosts = emptyList(),
            ghostMarkerColor = LocalPalette.current.tertiary
        )
    }

}