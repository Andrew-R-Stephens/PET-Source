package com.tritiumgaming.feature.investigation.ui.tool.operationtimer


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.widgets.tooltip.CommonTooltip
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerSkipButton
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerToggleButton
import com.tritiumgaming.feature.investigation.ui.tool.phase.PhaseComponent
import com.tritiumgaming.feature.investigation.ui.tool.phase.PhaseUiState

@Composable
internal fun OperationTimerColumn(
    modifier: Modifier = Modifier,
    remainingTime: String,
    paused: Boolean,
    onToggle: () -> Unit,
    onSkip: () -> Unit,
    phaseUiState: PhaseUiState
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {

            CommonTooltip(
                modifier = Modifier,
                tooltipText = stringResource(R.string.investigation_timer_label)
            ) {
                DigitalTimer(
                    modifier = Modifier
                        .height(36.dp)
                        .padding(8.dp),
                    remainingTime = remainingTime,
                )
            }
        }

        CommonTooltip(
            modifier = Modifier,
            tooltipText = stringResource(R.string.investigation_label_phase)
        ) {
            PhaseComponent(
                Modifier
                    .fillMaxWidth(),
                state = phaseUiState
            )
        }

        Row (
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ){
            val toggleTimerTooltip = if(paused) R.string.investigation_label_timer_run else R.string.investigation_label_timer_pause

            CommonTooltip(
                modifier = Modifier,
                tooltipText = stringResource(toggleTimerTooltip)
            ) {
                TimerToggleButton(
                    modifier = Modifier
                        .size(48.dp),
                    paused = paused,
                    onToggle = onToggle,
                    primaryContent = { modifier ->
                        Icon(
                            modifier = modifier,
                            painter = painterResource(R.drawable.ic_control_play),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    },
                    alternateContent = { modifier ->
                        Icon(
                            modifier = modifier,
                            painter = painterResource(R.drawable.ic_control_pause),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    }
                )
            }

            CommonTooltip(
                modifier = Modifier,
                tooltipText = stringResource(R.string.investigation_label_timer_skip)
            ) {
                TimerSkipButton(
                    modifier = Modifier
                        .size(48.dp),
                    onSkip = onSkip,
                    content = { modifier ->
                        Icon(
                            modifier = modifier,
                            painter = painterResource(R.drawable.ic_control_skip),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    },
                )
            }
        }
    }
}

@Composable
internal fun OperationTimerRow(
    modifier: Modifier = Modifier,
    remainingTime: String,
    paused: Boolean,
    onToggle: () -> Unit,
    onSkip: () -> Unit,
    phaseUiState: PhaseUiState
) {

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        itemVerticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonTooltip(
                modifier = Modifier,
                tooltipText = stringResource(R.string.investigation_timer_label)
            ) {
                DigitalTimer(
                    modifier = Modifier
                        .height(36.dp),
                    remainingTime = remainingTime,
                )
            }

            CommonTooltip(
                modifier = Modifier,
                tooltipText = stringResource(R.string.investigation_label_phase)
            ) {
                PhaseComponent(
                    Modifier
                        .fillMaxWidth(),
                    state = phaseUiState
                )
            }
        }

        Row (
            modifier = Modifier
                .wrapContentSize(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ){
            TimerToggleButton(
                modifier = Modifier
                    .size(48.dp),
                paused = paused,
                onToggle = onToggle,
                primaryContent = { modifier ->
                    CommonTooltip(
                        modifier = Modifier,
                        tooltipText = stringResource(R.string.investigation_label_timer_run)
                    ) {
                        Icon(
                            modifier = modifier,
                            painter = painterResource(R.drawable.ic_control_play),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    }
                },
                alternateContent = { modifier ->

                    CommonTooltip(
                        modifier = Modifier,
                        tooltipText = stringResource(R.string.investigation_label_timer_pause)
                    ) {
                        Icon(
                            modifier = modifier,
                            painter = painterResource(R.drawable.ic_control_pause),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    }
                }
            )

            CommonTooltip(
                modifier = Modifier,
                tooltipText = stringResource(R.string.investigation_label_timer_skip)
            ) {
                TimerSkipButton(
                    modifier = Modifier
                        .size(48.dp),
                    onSkip = onSkip,
                    content = { modifier ->
                        Icon(
                            modifier = modifier,
                            painter = painterResource(R.drawable.ic_control_skip),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    },
                )
            }
        }

    }
}
