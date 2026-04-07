package com.tritiumgaming.feature.investigation.ui.tool.timers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBar
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimerUiState

@Composable
internal fun NotchedProgressBarTimer(
    modifier: Modifier = Modifier,
    bundle: NotchedProgressBarBundle,
    icon: @Composable (Modifier) -> Unit = {}
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = bundle.title.uppercase(),
            style = LocalTypography.current.quaternary.bold,
            color = bundle.colors.label,
            fontSize = 14.sp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
            ) {
                icon(Modifier
                    .size(36.dp)
                )

                DigitalTimer(
                    modifier = Modifier,
                    state = DigitalTimerUiState(
                        startTime = bundle.state.max,
                        remainingTime = bundle.state.remaining,
                        paused = !bundle.state.running
                    ),
                    color = bundle.colors.label,
                    fontSize = 12.sp
                )

            }

            Box(
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f)
            ) {
                NotchedProgressBar(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize()
                        .padding(8.dp),
                    bundle = bundle
                )
            }

        }
    }
}
