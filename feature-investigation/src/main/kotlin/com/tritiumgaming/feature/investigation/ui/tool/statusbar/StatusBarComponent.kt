package com.tritiumgaming.feature.investigation.ui.tool.statusbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.app.mappers.phase.toPhaseTitle
import com.tritiumgaming.feature.investigation.app.mappers.phase.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.weather.toDrawable
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimerUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.SanityMeter
import com.tritiumgaming.feature.investigation.ui.tool.configs.FuseBoxUiState
import com.tritiumgaming.feature.investigation.ui.tool.phase.PhaseUiState
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.FuseBoxAtStartOfContract
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData
import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData.Companion.FuseBoxFlag
import com.tritiumgaming.shared.data.phase.model.PhaseResources

@Composable
internal fun OperationStatusBar(
    modifier: Modifier = Modifier,
    sanityLevel: Float,
    remainingTime: String,
    phaseType: PhaseResources.PhaseIdentifier,
    weatherType: Weather,
    temperature: String,
    fuseBoxFlag: FuseBoxFlag
) {

    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        PhaseStatusComponent(
            phaseType = phaseType,
            remainingTime = remainingTime
        )

        SanityStatusComponent(
            sanityLevel = sanityLevel
        )

        PowerStatusComponent(
            fuseBoxFlag = fuseBoxFlag
        )

        if(weatherType != Weather.RANDOM)
        WeatherStatusComponent(
            weatherType = weatherType,
            temperature = temperature
        )
    }
}

@Composable
private fun SanityStatusComponent(
    sanityLevel: Float
) {
    val density = LocalDensity.current
    var textHeight by remember { mutableStateOf(16.dp) }

    val sanityPercentString = sanityLevel.toPercentageString()

    Surface(
        modifier = Modifier,
        color = LocalPalette.current.surfaceContainerLow,
        shape = RoundedCornerShape(8.dp),
    ) {

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {

            SanityMeter(
                modifier = Modifier
                    .heightIn(min = 18.dp)
                    .height(textHeight)
                    .aspectRatio(1f),
                sanityLevel = sanityLevel,
                showText = false,
                showProgress = false
            )

            Text(
                modifier = Modifier
                    .wrapContentSize()
                    .onGloballyPositioned {
                        textHeight = with(density) { it.size.height.toDp() }
                    },
                text = sanityPercentString,
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.tertiary.regular.copy(
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1,
            )

        }
    }
}

@Composable
private fun PhaseStatusComponent(
    phaseType: PhaseResources.PhaseIdentifier,
    remainingTime: String
) {
    val density = LocalDensity.current
    var textHeight by remember { mutableStateOf(16.dp) }

    val phaseIconRes = when(phaseType) {
        PhaseResources.PhaseIdentifier.SETUP -> R.drawable.ic_truck
        PhaseResources.PhaseIdentifier.ACTION -> R.drawable.ic_door_open
        PhaseResources.PhaseIdentifier.HUNT -> R.drawable.ic_crosshairs
    }

    val phaseIconColor = when(phaseType) {
        PhaseResources.PhaseIdentifier.SETUP -> LocalPalette.current.onSurface
        PhaseResources.PhaseIdentifier.ACTION -> LocalPalette.current.onSurface
        PhaseResources.PhaseIdentifier.HUNT -> LocalPalette.current.onSurfaceVariant
    }

    Surface(
        modifier = Modifier,
        color = LocalPalette.current.surfaceContainerLow,
        shape = RoundedCornerShape(8.dp),
    ) {

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .heightIn(min = 18.dp)
                    .height(textHeight)
                    .aspectRatio(1f),
                painter = painterResource(phaseIconRes),
                contentDescription = "",
                colorFilter = ColorFilter.tint(phaseIconColor)
            )

            if (remainingTime.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .onGloballyPositioned {
                            textHeight = with(density) { it.size.height.toDp() }
                        },
                    text = remainingTime,
                    color = LocalPalette.current.onSurfaceVariant,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontFeatureSettings = "tnum"
                    ),
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun WeatherStatusComponent(
    weatherType: Weather,
    temperature: String
) {
    val density = LocalDensity.current
    var textHeight by remember { mutableStateOf(16.dp) }

    val weatherIconRes = weatherType.toDrawable()

    Surface(
        modifier = Modifier,
        color = LocalPalette.current.surfaceContainerLow,
        shape = RoundedCornerShape(8.dp),
    ) {

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                modifier = Modifier
                    .heightIn(min = 18.dp)
                    .height(textHeight)
                    .aspectRatio(1f),
                painter = painterResource(weatherIconRes),
                contentDescription = "",
                colorFilter = ColorFilter.tint(LocalPalette.current.onSurface)
            )

            if (temperature.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .onGloballyPositioned {
                            textHeight = with(density) { it.size.height.toDp() }
                        },
                    text = temperature,
                    color = LocalPalette.current.onSurfaceVariant,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontFeatureSettings = "tnum"
                    ),
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun PowerStatusComponent(
    fuseBoxFlag: FuseBoxFlag
) {
    val fuseBoxIconRes = when(fuseBoxFlag) {
        FuseBoxFlag.FUSEBOX_ENABLED -> R.drawable.ic_power_on
        FuseBoxFlag.FUSEBOX_DISABLED -> R.drawable.ic_power_off
        FuseBoxFlag.FUSEBOX_BROKEN -> R.drawable.ic_power_broken
    }

    Surface(
        modifier = Modifier
            .height(IntrinsicSize.Max),
        color = LocalPalette.current.surfaceContainerLow,
        shape = RoundedCornerShape(8.dp),
    ) {
        Image(
            modifier = Modifier
                .padding(8.dp)
                .height(18.dp)
                .aspectRatio(1f),
            painter = painterResource(fuseBoxIconRes),
            contentDescription = "",
            colorFilter = ColorFilter.tint(LocalPalette.current.onSurface)
        )
    }
}

internal data class StatusBarComponentStateBundle(
    val sanityUiState: PlayerSanityUiState,
    val digitalTimerUiState: DigitalTimerUiState,
    val phaseUiState: PhaseUiState
)