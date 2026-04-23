package com.tritiumgaming.feature.investigation.ui.tool.temperature


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureUiState.TemporalGradientDirection.COOLING
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureUiState.TemporalGradientDirection.HEATING


@Composable
internal fun TemperatureComponent(
    modifier: Modifier = Modifier,
    state: TemperatureStateBundle
) {
    val temperatureUiState = state.temperatureUiState

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

            Text(
                modifier = Modifier
                    .wrapContentSize(),
                text = "${temperatureUiState.range.high}",
                color = LocalPalette.current.onSurface,
                style = LocalTypography.current.tertiary.regular.copy(
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                ),
                maxLines = 1
            )

            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp),
                painter = painterResource(R.drawable.ic_thermostat),
                contentDescription = null,
                tint = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .wrapContentSize(),
                text = "${temperatureUiState.range.low}",
                color = LocalPalette.current.onSurface,
                style = LocalTypography.current.tertiary.regular.copy(
                    fontSize = 8.sp,
                    textAlign = TextAlign.Center,
                ),
                maxLines = 1
            )

        }

        Column(
            modifier = Modifier
                .wrapContentWidth()
                .height(IntrinsicSize.Min),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(R.drawable.ic_arrow_keyboard_up_single),
                contentDescription = null,
                tint = if(temperatureUiState.gradientDirection == HEATING) {
                    LocalPalette.current.tertiary
                } else { Color.Transparent }
            )

            Text(
                modifier = Modifier
                    .widthIn(min = with(LocalDensity.current) { 48.sp.toDp() }),
                text = temperatureUiState.currentAsString,
                color = LocalPalette.current.onSurface,
                style = LocalTypography.current.tertiary.regular.copy(
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1
            )

            Icon(
                modifier = Modifier
                    .size(20.dp),
                painter = painterResource(R.drawable.ic_arrow_keyboard_down_single),
                contentDescription = null,
                tint = if(temperatureUiState.gradientDirection == COOLING) {
                    LocalPalette.current.primary
                } else { Color.Transparent }
            )

        }

    }
}