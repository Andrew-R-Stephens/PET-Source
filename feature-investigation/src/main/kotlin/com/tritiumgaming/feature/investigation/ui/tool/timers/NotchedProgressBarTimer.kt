package com.tritiumgaming.feature.investigation.ui.tool.timers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBar
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimerUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerToggleButton
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiActions

@Composable
internal fun NotchedProgressBarTimer(
    modifier: Modifier = Modifier,
    title: String,
    max: Long,
    remaining: Long,
    timeText: String,
    running: Boolean,
    onToggle: () -> Unit,
    notches: List<ProgressBarNotch>,
    colors: NotchedProgressBarUiColors,
    icon: @Composable (Modifier) -> Unit = {}
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = title.uppercase(),
            style = LocalTypography.current.quaternary.bold,
            color = colors.label,
            fontSize = 14.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .width(56.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                icon(Modifier
                    .size(36.dp)
                )

                DigitalTimer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp),
                    remainingTime = timeText,
                    color = colors.label,
                    fontSize = 12.sp
                )

            }

            Box(
                modifier = Modifier
                    .height(36.dp)
                    .weight(1f)
            ) {
                NotchedProgressBar(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(8.dp),
                    max = max,
                    remaining = remaining,
                    notches = notches,
                    colors = colors
                )
            }

            TimerToggleButton(
                modifier = Modifier
                    .size(48.dp),
                paused = !running,
                onToggle = { onToggle() },
                primaryContent = { modifier ->
                    Icon(
                        modifier = modifier
                            .padding(2.dp),
                        painter = painterResource(R.drawable.ic_control_play),
                        contentDescription = null,
                        tint = LocalPalette.current.onSurface
                    )
                },
                alternateContent = { modifier ->
                    Icon(
                        modifier = modifier
                            .padding(2.dp),
                        painter = painterResource(R.drawable.ic_control_reset),
                        contentDescription = null,
                        tint = LocalPalette.current.onSurface
                    )
                }
            )

        }
    }
}
