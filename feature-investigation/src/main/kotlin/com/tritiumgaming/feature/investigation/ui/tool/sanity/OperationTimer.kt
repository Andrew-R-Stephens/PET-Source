package com.tritiumgaming.feature.investigation.ui.tool.sanity


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerSkipButton
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerToggleButton
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiActions
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.phase.PhaseComponent


@Composable
internal fun TimerComponentColumn(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState,
    timerUiActions: TimerUiActions,
    phaseUiState: OperationDetailsUiState.PhaseDetails
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            DigitalTimer(
                modifier = Modifier
                    .height(36.dp)
                    .padding(8.dp),
                state = timerUiState,
            )
        }

        Row (
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            TimerToggleButton(
                modifier = Modifier
                    .size(48.dp),
                state = timerUiState,
                actions = timerUiActions,
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

            TimerSkipButton(
                modifier = Modifier
                    .size(48.dp),
                state = timerUiState,
                actions = timerUiActions,
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

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = LocalPalette.current.surfaceContainerLowest,
            shape = RoundedCornerShape(8.dp),
        ) {
            PhaseComponent(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                phaseUiState = phaseUiState
            )
        }
    }
}

@Composable
internal fun TimerComponentRow(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState,
    timerUiActions: TimerUiActions,
    phaseUiState: OperationDetailsUiState.PhaseDetails
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Column(
            modifier = Modifier
                .wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DigitalTimer(
                modifier = Modifier
                    .height(36.dp)
                    .padding(8.dp),
                state = timerUiState,
            )

            Surface(
                modifier = Modifier,
                color = LocalPalette.current.surfaceContainerLowest,
                shape = RoundedCornerShape(8.dp),
            ) {
                PhaseComponent(
                    Modifier
                        .padding(8.dp),
                    phaseUiState = phaseUiState
                )
            }
        }

        Row (
            modifier = Modifier
                .wrapContentSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            TimerToggleButton(
                modifier = Modifier
                    .size(48.dp),
                state = timerUiState,
                actions = timerUiActions,
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

            TimerSkipButton(
                modifier = Modifier
                    .size(48.dp),
                state = timerUiState,
                actions = timerUiActions,
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