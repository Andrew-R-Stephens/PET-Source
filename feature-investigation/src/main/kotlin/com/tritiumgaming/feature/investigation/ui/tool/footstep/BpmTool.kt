package com.tritiumgaming.feature.investigation.ui.tool.footstep

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.SpeedBBIcon
import com.tritiumgaming.core.ui.icon.impl.base.SpeedBIcon
import com.tritiumgaming.core.ui.icon.impl.base.SpeedIcon
import com.tritiumgaming.core.ui.mapper.toStringResource
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.dropdownlist.DropdownList
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.beatline.BeatLineUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels.GraphLabelsUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphsurface.GraphSurfaceUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeplot.RealtimePlotUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeverticalmeter.RealtimeVerticalMeterColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.GraphPoint
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.feature.investigation.app.mappers.weather.toDrawable
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.BpmVisualizer
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.BpmVisualizerColorBundle
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.BpmVisualizerStateBundle
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.BpmVisualizerUiActions
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.VisualizerMeasurementType
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.difficultysetting.mapper.toFloat
import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData
import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData.Companion.FuseBoxFlag
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun BpmTool(
    modifier: Modifier = Modifier,
    realtimeState: RealtimeUiState<GraphPoint>,
    measurementType: VisualizerMeasurementType,
    applyMeasurement: Boolean,
    ghostSpeedModifier: Float = 1f,
    fuseBoxFlag: FuseBoxFlag = FuseBoxFlag.FUSEBOX_ENABLED,
    domainMillis: Long = 10.seconds.inWholeMilliseconds,
    domainSampleIntervalMillis: Long = 3.seconds.inWholeMilliseconds,
    weather: Weather = Weather.RANDOM,
    range: Int = 300,
    domainOptions: List<Long> = emptyList(),
    sampleIntervalOptions: List<Long> = emptyList(),
    onUpdate: (RealtimeUiState<GraphPoint>) -> Unit,
    onChangeMeasurementType: (VisualizerMeasurementType) -> Unit,
    toggleApplyMeasurement: () -> Unit,
    onChangeDomain: (Long) -> Unit = {},
    onChangeDomainSampleInterval: (Long) -> Unit = {}
) {

    val bpmVisualizerStateBundle = BpmVisualizerStateBundle(
        alpha = .5f,
        domain = domainMillis,
        range = range,
        rangeInterval = 60f,
        domainSampleInterval = domainSampleIntervalMillis
    )

    val alpha: (thisType: VisualizerMeasurementType, testType: VisualizerMeasurementType) -> Float = {
            thisType, testType -> if(thisType == testType) 1f else .25f }

    val active: (thisType: VisualizerMeasurementType, testType: VisualizerMeasurementType) -> Boolean = {
            thisType, testType -> thisType == testType
    }

    val bpmVisualizerColorBundle = BpmVisualizerColorBundle(
        realtimePlotUiColors = RealtimePlotUiColors(
            instant = LocalPalette.current.primary.copy(
                alpha = alpha(
                    measurementType,
                    VisualizerMeasurementType.INSTANT
                )
            ),
            averaged = LocalPalette.current.tertiary.copy(
                alpha = alpha(
                    measurementType,
                    VisualizerMeasurementType.AVERAGED
                )
            ),
            weighted = LocalPalette.current.secondary.copy(
                alpha = alpha(
                    measurementType,
                    VisualizerMeasurementType.WEIGHTED
                )
            ),
            line = LocalPalette.current.onSurface
        ),
        realtimeVerticalMeterColors = RealtimeVerticalMeterColors(
            color = LocalPalette.current.onSurface,
            onColor = LocalPalette.current.tertiary,
        ),
        beatLineUiColors = BeatLineUiColors(
            color = LocalPalette.current.onSurface.copy(alpha = .75f)
        ),
        graphLabelsXAxisUiColors = GraphLabelsUiColors(
            label = LocalPalette.current.onSurface,
        ),
        graphLabelsYAxisUiColors = GraphLabelsUiColors(
            label = LocalPalette.current.onSurface,
        ),
        graphSurfaceUiColors = GraphSurfaceUiColors(
            surface = LocalPalette.current.surface,
            surfaceContainer = LocalPalette.current.surfaceContainer,
            domain = LocalPalette.current.onSurface.copy(alpha = .5f),
            range = Color.Unspecified
        )
    )

    val segmentedColors = SegmentedButtonDefaults.colors(
        activeContainerColor = LocalPalette.current.surfaceContainerLow,
        activeContentColor = LocalPalette.current.onSurfaceVariant,
        inactiveContainerColor = LocalPalette.current.surfaceContainer,
        inactiveContentColor = LocalPalette.current.onSurface,
        inactiveBorderColor = LocalPalette.current.surfaceContainerHigh,
        activeBorderColor = LocalPalette.current.onSurfaceVariant
    )

    Column (
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .preferredFrameRate(FrameRateCategory.Normal),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = stringResource(R.string.investigation_label_tool_footstep).uppercase(),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                fontSize = 18.sp,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )
        }

        /*LiveStatsRow(
            realtimeState = realtimeState,
            measurementType = measurementType,
            applyMeasurement = applyMeasurement
        )*/

        if (fuseBoxFlag == FuseBoxFlag.FUSEBOX_ENABLED || weather == Weather.BLOOD_MOON ||
            ghostSpeedModifier != DifficultySettingResources.GhostSpeed.SPEED_100.toFloat()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Modifiers".uppercase(),
                    color = LocalPalette.current.onSurfaceVariant,
                    style = LocalTypography.current.quaternary.bold.copy(
                        textAlign = TextAlign.Start
                    ),
                    fontSize = 14.sp,
                    maxLines = 1
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    if (ghostSpeedModifier != DifficultySettingResources.GhostSpeed.SPEED_100.toFloat()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_speed),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = LocalPalette.current.onSurfaceVariant
                            )
                            Text(
                                text = ghostSpeedModifier.toPercentageString(false),
                                style = LocalTypography.current.quaternary.regular,
                                fontSize = 12.sp,
                                color = LocalPalette.current.onSurfaceVariant
                            )
                        }
                    }

                    if (weather == Weather.BLOOD_MOON) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(weather.toDrawable()),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = LocalPalette.current.onSurfaceVariant
                            )
                            Text(
                                text = "+15%",
                                style = LocalTypography.current.quaternary.regular,
                                fontSize = 12.sp,
                                color = LocalPalette.current.onSurfaceVariant
                            )
                        }
                    }

                    if (fuseBoxFlag == FuseBoxFlag.FUSEBOX_ENABLED) {

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_map_power),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = LocalPalette.current.onSurfaceVariant
                            )
                            Text(
                                text = stringResource(
                                    DifficultySettingResources.FuseBoxAtStartOfContract.ON
                                        .toStringResource()
                                ),
                                style = LocalTypography.current.quaternary.regular,
                                fontSize = 12.sp,
                                color = LocalPalette.current.onSurfaceVariant
                            )
                        }
                    }

                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.footstep_label_apply).uppercase(),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                fontSize = 14.sp,
                maxLines = 1
            )

            SingleChoiceSegmentedButtonRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            ) {
                SegmentedButton(
                    modifier = Modifier.height(32.dp),
                    shape = SegmentedButtonDefaults.itemShape(
                        index = 0,
                        count = 4,
                        baseShape = RoundedCornerShape(8.dp)
                    ),
                    onClick = {
                        if(applyMeasurement)
                            toggleApplyMeasurement()
                    },
                    selected = !applyMeasurement,
                    colors = segmentedColors,
                    icon = {}
                ) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(R.string.footstep_label_none).uppercase(),
                        fontSize = 12.sp,
                        maxLines = 1
                    )
                }

                SegmentedButton(
                    modifier = Modifier.height(32.dp),
                    shape = SegmentedButtonDefaults.itemShape(
                        index = 1,
                        count = 4,
                        baseShape = RoundedCornerShape(8.dp)
                    ),
                    onClick = {
                        if(!applyMeasurement) { toggleApplyMeasurement() }
                        onChangeMeasurementType(VisualizerMeasurementType.INSTANT) },
                    selected = applyMeasurement &&
                            active(measurementType, VisualizerMeasurementType.INSTANT),
                    colors = segmentedColors,
                    icon = {}
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        SpeedIcon(
                            modifier = Modifier.size(16.dp),
                            colors = IconVectorColors(
                                fillColor = LocalPalette.current.primary,
                                strokeColor = LocalPalette.current.primary
                            )
                        )
                        Text(
                            modifier = Modifier,
                            text = stringResource(R.string.footstep_label_instant).uppercase(),
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }

                SegmentedButton(
                    modifier = Modifier.height(32.dp),
                    shape = SegmentedButtonDefaults.itemShape(
                        index = 2,
                        count = 4,
                        baseShape = RoundedCornerShape(8.dp)
                    ),
                    onClick = {
                        if(!applyMeasurement) { toggleApplyMeasurement() }
                        onChangeMeasurementType(VisualizerMeasurementType.AVERAGED) },
                    selected = applyMeasurement &&
                            active(measurementType, VisualizerMeasurementType.AVERAGED),
                    colors = segmentedColors,
                    icon = {}
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        SpeedBIcon(
                            modifier = Modifier.size(16.dp),
                            colors = IconVectorColors(
                                fillColor = LocalPalette.current.tertiary,
                                strokeColor = LocalPalette.current.tertiary
                            )
                        )
                        Text(
                            modifier = Modifier,
                            text = stringResource(R.string.footstep_label_average).uppercase(),
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }

                SegmentedButton(
                    modifier = Modifier.height(32.dp),
                    shape = SegmentedButtonDefaults.itemShape(
                        index = 3,
                        count = 4,
                        baseShape = RoundedCornerShape(8.dp)
                    ),
                    onClick = {
                        if(!applyMeasurement) { toggleApplyMeasurement() }
                        onChangeMeasurementType(VisualizerMeasurementType.WEIGHTED) },
                    selected = applyMeasurement &&
                            active(measurementType, VisualizerMeasurementType.WEIGHTED),
                    colors = segmentedColors,
                    icon = {}
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        SpeedBBIcon(
                            modifier = Modifier.size(16.dp),
                            colors = IconVectorColors(
                                fillColor = LocalPalette.current.secondary,
                                strokeColor = LocalPalette.current.secondary
                            )
                        )
                        Text(
                            modifier = Modifier,
                            text = stringResource(R.string.footstep_label_weighted).uppercase(),
                            fontSize = 12.sp,
                            maxLines = 1
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            BpmVisualizer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                stateBundle = bpmVisualizerStateBundle,
                colorBundle = bpmVisualizerColorBundle,
                actions = BpmVisualizerUiActions(
                    onUpdate = { newTapUiState ->
                        onUpdate(newTapUiState)
                    }
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier.weight(1f),
                    color = LocalPalette.current.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.footstep_label_domain).uppercase(),
                            color = LocalPalette.current.onSurfaceVariant,
                            style = LocalTypography.current.quaternary.bold,
                            fontSize = 12.sp
                        )

                        val domainLabels = domainOptions.map { "${it / 1000}s" }

                        DropdownList(
                            modifier = Modifier.fillMaxWidth(),
                            options = domainLabels,
                            label = "${domainMillis / 1000}s",
                            onSelect = { onChangeDomain(domainOptions[it]) },
                            textStyle = LocalTypography.current.quaternary.bold,
                            onColor = LocalPalette.current.onSurface,
                            color = LocalPalette.current.surfaceContainerHigh
                        )
                    }
                }

                Surface(
                    modifier = Modifier.weight(1f),
                    color = LocalPalette.current.surfaceContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.footstep_label_sample_interval).uppercase(),
                            color = LocalPalette.current.onSurfaceVariant,
                            style = LocalTypography.current.quaternary.bold,
                            fontSize = 12.sp
                        )

                        val sampleIntervalLabels = sampleIntervalOptions.map { "${it / 1000}s" }

                        DropdownList(
                            modifier = Modifier.fillMaxWidth(),
                            options = sampleIntervalLabels,
                            label = "${domainSampleIntervalMillis / 1000}s",
                            onSelect = { onChangeDomainSampleInterval(sampleIntervalOptions[it]) },
                            textStyle = LocalTypography.current.quaternary.bold,
                            onColor = LocalPalette.current.onSurface,
                            color = LocalPalette.current.surfaceContainerHigh
                        )
                    }
                }
            }
        }

    }
}

@Composable
private fun LiveStatsRow(
    realtimeState: RealtimeUiState<GraphPoint>,
    measurementType: VisualizerMeasurementType,
    applyMeasurement: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        LiveStatItem(
            modifier = Modifier.weight(1f),
            icon = {
                SpeedIcon(
                    modifier = Modifier.size(16.dp),
                    colors = IconVectorColors(
                        fillColor = LocalPalette.current.primary,
                        strokeColor = LocalPalette.current.primary
                    )
                )
            },
            value = realtimeState.smoothed / 60f,
            label = stringResource(R.string.footstep_label_instant),
            isActive = applyMeasurement && measurementType == VisualizerMeasurementType.INSTANT,
            color = LocalPalette.current.primary
        )
        LiveStatItem(
            modifier = Modifier.weight(1f),
            icon = {
                SpeedBIcon(
                    modifier = Modifier.size(16.dp),
                    colors = IconVectorColors(
                        fillColor = LocalPalette.current.tertiary,
                        strokeColor = LocalPalette.current.tertiary
                    )
                )
            },
            value = realtimeState.average / 60f,
            label = stringResource(R.string.footstep_label_average),
            isActive = applyMeasurement && measurementType == VisualizerMeasurementType.AVERAGED,
            color = LocalPalette.current.tertiary
        )
        LiveStatItem(
            modifier = Modifier.weight(1f),
            icon = {
                SpeedBBIcon(
                    modifier = Modifier.size(16.dp),
                    colors = IconVectorColors(
                        fillColor = LocalPalette.current.secondary,
                        strokeColor = LocalPalette.current.secondary
                    )
                )
            },
            value = realtimeState.weightedAverage / 60f,
            label = stringResource(R.string.footstep_label_weighted),
            isActive = applyMeasurement && measurementType == VisualizerMeasurementType.WEIGHTED,
            color = LocalPalette.current.secondary
        )
    }
}

@Composable
private fun LiveStatItem(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    value: Float,
    label: String,
    isActive: Boolean,
    color: Color
) {
    Column(
        modifier = modifier
            .background(
                if (isActive) color.copy(alpha = 0.15f) else Transparent,
                RoundedCornerShape(8.dp)
            )
            .padding(vertical = 4.dp, horizontal = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            icon()
            Text(
                text = "%.2f".format(value),
                style = LocalTypography.current.quaternary.bold,
                fontSize = 14.sp,
                color = if (isActive) color else LocalPalette.current.onSurfaceVariant
            )
        }
        Text(
            text = label.uppercase(),
            style = LocalTypography.current.quaternary.bold,
            fontSize = 10.sp,
            color = if (isActive) color.copy(alpha = 0.8f) else LocalPalette.current.onSurfaceVariant.copy(alpha = 0.6f),
            maxLines = 1,
            textAlign = TextAlign.Center
        )
    }
}

internal data class BpmToolUiActions(
    val onUpdate: (RealtimeUiState<GraphPoint>) -> Unit = {},
    val onChangeMeasurementType: (VisualizerMeasurementType) -> Unit = {},
    val toggleApplyMeasurement: () -> Unit = {},
    val onChangeDomain: (Long) -> Unit = {},
    val onChangeDomainSampleInterval: (Long) -> Unit = {}
)

internal data class BpmToolUiState(
    val realtimeState: RealtimeUiState<GraphPoint> = RealtimeUiState(),
    val measurementType: VisualizerMeasurementType = VisualizerMeasurementType.INSTANT,
    val ghostSpeedModifier: Float = 1f,
    val fuseBoxFlag: FuseBoxFlag = FuseBoxFlag.FUSEBOX_ENABLED,
    val domainMillis: Long = 10.seconds.inWholeMilliseconds,
    val domainSampleIntervalMillis: Long = 3.seconds.inWholeMilliseconds,
    val applyMeasurement: Boolean = false,
    val weather: Weather = Weather.RANDOM,
    val range: Int = 300,
    val domainOptions: List<Long> = emptyList(),
    val sampleIntervalOptions: List<Long> = emptyList()
)
