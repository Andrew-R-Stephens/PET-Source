package com.tritiumgaming.feature.investigation.ui.section

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.TruckTimeIcon
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.DifficultyUiState
import com.tritiumgaming.feature.investigation.ui.MapUiState
import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerToggleButton
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.OperationConfigCarousel
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.OperationConfigUiActions
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.SanityMeter
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources


@Composable
fun ColumnScope.ToolbarConfigurationSection(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState,
    mapUiState: MapUiState,
    difficultyUiState: DifficultyUiState,
    sanityUiState: PlayerSanityUiState,
    operationConfigUiActions: OperationConfigUiActions
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {

        OperationConfigCarousel(
            modifier = Modifier,
            primaryIcon = R.drawable.icon_nav_mapmenu2,
            label = stringResource(mapUiState.name.toStringResource(
                SimpleMapResources.MapTitleLength.ABBREVIATED)),
            textStyle = LocalTypography.current.secondary.regular,
            color = LocalPalette.current.onSurface,
            iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            onClickLeft = { operationConfigUiActions.onMapLeftClick() },
            onClickRight = { operationConfigUiActions.onMapRightClick() }
        )

        OperationConfigCarousel(
            modifier = Modifier,
            primaryIcon = R.drawable.ic_puzzle,
            label = stringResource(difficultyUiState.name.toStringResource()),
            textStyle = LocalTypography.current.secondary.regular,
            color = LocalPalette.current.onSurface,
            iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            onClickLeft = { operationConfigUiActions.onDifficultyLeftClick() },
            onClickRight = { operationConfigUiActions.onDifficultyRightClick() }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SanityMeter(
                modifier = Modifier
                    .size(64.dp),
                sanityUiState = sanityUiState
            )

            TruckTimeIcon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(4.dp),
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.onSurface,
                    strokeColor = LocalPalette.current.surface
                )
            )
            
            Column(
                modifier = Modifier
                    .weight(1f, false)
            ) {


                /*Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Remaining Time",
                    style = LocalTypography.current.primary.regular,
                    color = LocalPalette.current.onSurface
                )*/

                DigitalTimer(
                    modifier = Modifier
                        .height(48.dp)
                        .padding(8.dp)
                        .fillMaxWidth(),
                    timerUiState = timerUiState
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f, false)
            ) {
                TimerToggleButton(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(start = 4.dp),
                    timerUiState = timerUiState,
                    playContent = { modifier ->
                        Icon(
                            modifier = modifier,
                            painter = painterResource(R.drawable.ic_control_play),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    },
                    pauseContent = { modifier ->
                        Icon(
                            modifier = modifier
                                .size(48.dp),
                            painter = painterResource(R.drawable.ic_control_pause),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    },
                    onClick = {
                        operationConfigUiActions.onToggleTimer()
                    }
                )
            }
        }
    }
}
