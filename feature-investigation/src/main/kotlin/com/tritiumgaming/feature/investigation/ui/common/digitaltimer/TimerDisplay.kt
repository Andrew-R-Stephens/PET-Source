package com.tritiumgaming.feature.investigation.ui.common.digitaltimer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.FormatterUtils
import com.tritiumgaming.core.ui.theme.DigitalDreamTextStyle
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.ui.TimerUiState

internal data class DigitalTimerUiState(
    val startTime: Long = TimerUiState.TIME_DEFAULT,
    val remainingTime: Long = 0L,
    val paused: Boolean = true
)

@Composable
internal fun DigitalTimer(
    modifier: Modifier = Modifier,
    state: DigitalTimerUiState,
    color: Color = LocalPalette.current.onSurface,
    fontSize: TextUnit = 48.sp
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight(),
            text = FormatterUtils.formatMillisToTime(state.remainingTime),
            style = DigitalDreamTextStyle,
            color = color,
            fontSize = fontSize
        )
    }
}

@Composable
fun DigitalTimer(
    modifier: Modifier = Modifier,
    state: TimerUiState,
    color: Color = LocalPalette.current.onSurface
) {

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
                text = FormatterUtils.formatMillisToTime(state.remainingTime),
                style = DigitalDreamTextStyle,
                color = color,
                autoSize = TextAutoSize.StepBased(1.sp, maxFontSize = 48.sp, stepSize = 1.sp)
            )
        }
    }
}

@Composable
fun TimerToggleButton(
    modifier: Modifier = Modifier,
    state: TimerUiState,
    actions: TimerUiActions,
    primaryContent: @Composable (Modifier) -> Unit = {},
    alternateContent: @Composable (Modifier) -> Unit = {}
) {
    val state = state.paused

    Button(
        modifier = modifier,
        onClick = { actions.onToggle() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = LocalPalette.current.onSurface
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        if (state) {
            primaryContent(
                Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            )
        } else {
            alternateContent(
                Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun TimerSkipButton(
    modifier: Modifier = Modifier,
    state: TimerUiState,
    actions: TimerUiActions,
    content: @Composable (Modifier) -> Unit = {}
) {
    Button(
        modifier = modifier,
        onClick = { actions.onSkip() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = LocalPalette.current.onSurface
        ),
        contentPadding = PaddingValues(8.dp),
        //enabled = state.remainingTime > 0L
    ) {
        content(
            Modifier
                .fillMaxSize()
                .padding(4.dp)
        )
    }
}
