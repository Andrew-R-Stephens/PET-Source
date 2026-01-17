package com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.FormatterUtils
import com.tritiumgaming.core.ui.theme.DigitalDreamTextStyle
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import kotlin.time.Duration.Companion.seconds

@Composable
fun DigitalTimer(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState,
    color: Color = LocalPalette.current.onSurface
) {
    /*var remainingTime = timerUiState.remainingTime
    LaunchedEffect(timerUiState) {
        remainingTime = timerUiState.remainingTime
    }*/

    var size by remember{
        mutableStateOf(IntSize.Zero)
    }

    Box(
        modifier = modifier
            .onSizeChanged{ size = it },
        contentAlignment = Alignment.Center
    ) {

        key(size) {
            Text(
                modifier = Modifier
                    .wrapContentHeight(),
                text = FormatterUtils.formatMillisToTime(timerUiState.remainingTime),
                style = DigitalDreamTextStyle,
                color = color,
                autoSize = TextAutoSize.StepBased(12.sp, maxFontSize = 48.sp, stepSize = 1.sp)
            )
        }
    }
}

@Composable
fun TimerToggleButton(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState,
    playContent: @Composable (modifier: Modifier) -> Unit = {},
    pauseContent: @Composable (modifier: Modifier) -> Unit = {},
    onClick: () -> Unit = {}
) {
    val state = timerUiState.paused

    Button(
        modifier = modifier,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = LocalPalette.current.onSurface
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        if (state) {
            playContent(Modifier)
        } else {
            pauseContent(Modifier)
        }
    }
}

@Preview
@Composable
private fun DigitalTimerPreview() {

    SelectiveTheme {
        DigitalTimer(
            modifier = Modifier
                .wrapContentWidth()
                .height(48.dp)
                .padding(4.dp),
            timerUiState = TimerUiState(
                remainingTime = 10.seconds.inWholeMilliseconds
            )
        )
    }
}