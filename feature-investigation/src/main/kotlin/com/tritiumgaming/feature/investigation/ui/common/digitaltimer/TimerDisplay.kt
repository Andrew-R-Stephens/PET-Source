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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.DigitalDreamTextStyle
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerUiState

internal data class DigitalTimerUiState(
    val startTime: Long = OperationTimerUiState.TIME_DEFAULT,
    val remainingTime: String = "",
    val paused: Boolean = true
)

@Composable
fun DigitalTimer(
    modifier: Modifier = Modifier,
    remainingTime: String,
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
            text = remainingTime,
            style = DigitalDreamTextStyle.copy(
                fontFeatureSettings = "tnum"
            ),
            color = color,
            autoSize = TextAutoSize.StepBased(1.sp, maxFontSize = fontSize, stepSize = 1.sp),
            maxLines = 1,
            softWrap = false
        )
    }
}

@Composable
fun TimerToggleButton(
    modifier: Modifier = Modifier,
    paused: Boolean,
    onToggle: () -> Unit,
    primaryContent: @Composable (Modifier) -> Unit = {},
    alternateContent: @Composable (Modifier) -> Unit = {}
) {

    Button(
        modifier = modifier,
        onClick = { onToggle() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = LocalPalette.current.onSurface
        ),
        contentPadding = PaddingValues(8.dp)
    ) {
        if (paused) {
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
    onSkip: () -> Unit,
    content: @Composable (Modifier) -> Unit = {}
) {
    Button(
        modifier = modifier,
        onClick = { onSkip() },
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
