package com.tritiumgaming.feature.investigation.ui.section.configs

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
import com.tritiumgaming.core.ui.icon.impl.base.TruckTimeIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerToggleButton
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.ConfigCarouselUiState
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.OperationConfigCarousel
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.SanityMeter
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources


@Composable
fun ColumnScope.OperationConfigs(
    modifier: Modifier = Modifier,
    timerComponent: @Composable (Modifier) -> Unit = {},
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier) -> Unit = {},
    phaseComponent: @Composable (Modifier) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {

        mapConfigComponent(Modifier)

        difficultyConfigComponent(Modifier)

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            sanityMeterComponent(
                Modifier
                    .size(64.dp)
            )

            timerComponent(
                Modifier
                    .weight(1f, false)
            )
        }

        phaseComponent(Modifier)
    }
}
/*

@Composable
fun ColumnScope.OperationConfigs(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState,
    mapUiState: MapUiState,
    mapUiActions: CarouselUiActions,
    difficultyUiState: DifficultyUiState,
    difficultyUiActions: CarouselUiActions,
    sanityUiState: PlayerSanityUiState,
    timerUiActions: TimerUiActions
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {

        OperationConfigCarousel(
            modifier = Modifier,
            primaryIcon = R.drawable.icon_nav_mapmenu2,
            textStyle = LocalTypography.current.secondary.regular,
            color = LocalPalette.current.onSurface,
            iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            state = ConfigCarouselUiState(
                label = stringResource(mapUiState.name.toStringResource(
                    SimpleMapResources.MapTitleLength.ABBREVIATED)),
            ),
            actions = mapUiActions
        )

        OperationConfigCarousel(
            modifier = Modifier,
            primaryIcon = R.drawable.ic_puzzle,
            state = ConfigCarouselUiState(
            label = stringResource(difficultyUiState.name.toStringResource()),
            ),
            textStyle = LocalTypography.current.secondary.regular,
            color = LocalPalette.current.onSurface,
            iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            actions = difficultyUiActions
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


                */
/*Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Remaining Time",
                    style = LocalTypography.current.primary.regular,
                    color = LocalPalette.current.onSurface
                )*//*


                DigitalTimer(
                    modifier = Modifier
                        .height(48.dp)
                        .padding(8.dp)
                        .fillMaxWidth(),
                    state = timerUiState
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
                    state = timerUiState,
                    actions = timerUiActions,
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
                            modifier = modifier,
                            painter = painterResource(R.drawable.ic_control_pause),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    }
                )
            }
        }
    }
}
*/
