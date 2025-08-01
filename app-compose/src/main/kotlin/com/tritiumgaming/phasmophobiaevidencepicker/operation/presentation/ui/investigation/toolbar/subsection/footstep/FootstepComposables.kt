package com.tritiumgaming.phasmophobiaevidencepicker.operation.presentation.ui.investigation.toolbar.subsection.footstep

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.SelectiveTheme
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.ui.theme.palette.LocalPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.ColorUtils
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.ColorUtils.getColorFromAttribute
import kotlin.math.absoluteValue
import kotlin.math.max

@Composable
@Preview
fun FootstepTool(
    modifier: Modifier = Modifier,
    bpm: Float = 1f,
) {

    val millisPerMinute = 60000f
    val timeWindowMillis = 120000f

    val bps = bpm

    val scale = (timeWindowMillis / millisPerMinute).coerceAtLeast(millisPerMinute / timeWindowMillis)

    val bpms = bps * scale

    Box(
        modifier
            .fillMaxWidth()
    ) {
        Background()
        Grid(scale)
        FootIcons(bpms, scale)
        Scanline(scale)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = "$bpm",
                fontSize = 12.sp,
                color = LocalPalette.current.textFamily.body
            )

            Text(
                text = "$bpms",
                fontSize = 12.sp,
                color = LocalPalette.current.textFamily.body
            )
        }
        //Foreground()
    }

}

@Composable
fun Background() {
    Canvas(modifier = Modifier
        .fillMaxSize()) {
        drawRect(Color.Black, Offset(0f, 0f), Size(size.width, size.height))
    }
}

@Composable
fun Grid(
    scale: Float = 1f
) {
    val linesPerSecond = ((5 + 1) * scale).toInt()

    val lineColorSecond = Color(
        ColorUtils.interpolate(
            getColorFromAttribute(LocalContext.current, R.attr.textColorBody),
            Color.Transparent.toArgb(),
            .65f)
    )

    val lineColorBeat = Color(
        ColorUtils.interpolate(
            getColorFromAttribute(LocalContext.current, R.attr.textColorBody),
            Color.Transparent.toArgb(),
            .4f)
    )

    Canvas(modifier = Modifier
        .fillMaxSize()
    ) {
        val count = linesPerSecond
        for(i in -count ..count) {
            val xPos = (size.width * .5f) + (i * (size.width / scale / linesPerSecond))
            drawLine(
                start = Offset(xPos, 0f),
                end = Offset(xPos, size.height),
                color =
                    if(i == 0 || i.absoluteValue == (count*.5f).toInt())
                        Color.Green
                    else lineColorBeat,
                strokeWidth = 1f
            )
        }
    }
}

@Composable
fun FootIcons(
    bpms: Float = 1f,
    scale: Float = 1f
) {
    val footCount = bpms * scale

    val lineColor = LocalPalette.current.coreFamily.primary

    Canvas(modifier = Modifier.fillMaxSize()) {
        val count = (footCount).toInt() * 2
        for(i in -count ..count) {
            drawCircle(
                lineColor,
                radius = max(size.height * .05f, 5f),
                Offset(
                    (size.width * .5f) + (i * (size.width / footCount)),
                    size.height / 2f
                ),
                style = Fill
            )
        }

    }
}

@Composable
fun Scanline(
    scale: Float = 3f
) {
    val time = (1000 * scale).toInt()
    val targetValue = 1f

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val xPos = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(time, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    Canvas(modifier = Modifier.fillMaxSize()) {

        drawRect(
            Color.Red,
            Offset(
                (xPos.value * size.width),
                0f),
            Size(1f, size.height),
            style = Stroke(width = 1f)
        )

    }
}

@Composable
fun Foreground() {
    Canvas(modifier = Modifier
        .fillMaxSize()) {
        drawRect(
            Color.White,
            Offset(0f, 0f),
            Size(size.width, size.height),
            style = Stroke(
                width = 10f
            )
        )
        drawRect(Color.Transparent, Offset(0f, 0f), Size(size.width, size.height))
    }
}

@Preview
@Composable
fun FootstepToolPreview() {

    var bpm by remember { mutableFloatStateOf(0f) }

    SelectiveTheme {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FootstepTool(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable(onClick = {
                        bpm = bpm + 1
                    }),
                bpm = bpm
            )
        }
    }
}