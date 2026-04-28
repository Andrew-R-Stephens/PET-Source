package com.tritiumgaming.feature.investigation.ui.tool.footstep

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.SpeedBBIcon
import com.tritiumgaming.core.ui.icon.impl.base.SpeedBIcon
import com.tritiumgaming.core.ui.icon.impl.base.SpeedIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.beatline.BeatLineUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels.GraphLabelsUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphsurface.GraphSurfaceUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeplot.RealtimePlotUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeverticalmeter.RealtimeVerticalMeterColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.GraphPoint
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.BpmVisualizer
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.BpmVisualizerColorBundle
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.BpmVisualizerStateBundle
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.BpmVisualizerUiActions
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.VisualizerMeasurementType
import kotlin.time.Duration.Companion.seconds

@Composable
internal fun BpmTool(
    modifier: Modifier = Modifier,
    realtimeState: RealtimeUiState<GraphPoint>,
    measurementType: VisualizerMeasurementType,
    applyMeasurement: Boolean,
    onUpdate: (RealtimeUiState<GraphPoint>) -> Unit,
    onChangeMeasurementType: (VisualizerMeasurementType) -> Unit,
    toggleApplyMeasurement: () -> Unit
) {

    val bpmVisualizerStateBundle = BpmVisualizerStateBundle(
        alpha = .5f,
        range = 300,
        domain = 10.seconds.inWholeMilliseconds,
        domainInterval = 10f,
        rangeInterval = 60f,
        domainSampleInterval = 3.seconds.inWholeMilliseconds
    )

    val alpha: (thisType: VisualizerMeasurementType, testType: VisualizerMeasurementType) -> Float = {
            thisType, testType -> if(thisType == testType) 1f else .25f }

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


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onChangeMeasurementType(VisualizerMeasurementType.INSTANT)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start)
        ) {
            Checkbox(
                modifier = Modifier
                    .padding(8.dp),
                checked = applyMeasurement,
                onCheckedChange = { toggleApplyMeasurement() }
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                text = "Apply",
                color = LocalPalette.current.onSurface
            )
        }

        BpmVisualizer(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            stateBundle = bpmVisualizerStateBundle,
            colorBundle = bpmVisualizerColorBundle,
            actions = BpmVisualizerUiActions(
                onUpdate = { newTapUiState ->
                    onUpdate(newTapUiState) }
            )
        )

        Row(
           modifier = Modifier
               .fillMaxWidth()
               .clickable {
                   onChangeMeasurementType(VisualizerMeasurementType.INSTANT)
               }
        ) {
            SpeedIcon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(8.dp),
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.onSurface.copy(
                        alpha = alpha(
                            measurementType,
                            VisualizerMeasurementType.INSTANT
                        )
                    ),
                    strokeColor = LocalPalette.current.onSurface.copy(
                        alpha = alpha(
                            measurementType,
                            VisualizerMeasurementType.INSTANT
                        )
                    )
                )
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                text = "%.1f".format(realtimeState.smoothed / 60f),
                color = LocalPalette.current.onSurface
            )
        }

        Row(
           modifier = Modifier
               .fillMaxWidth()
               .clickable {
                   onChangeMeasurementType(VisualizerMeasurementType.AVERAGED)
               }
        ) {
            SpeedBIcon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(8.dp),
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.onSurface.copy(
                        alpha = alpha(measurementType,
                            VisualizerMeasurementType.AVERAGED)
                    ),
                    strokeColor = LocalPalette.current.onSurface.copy(
                        alpha = alpha(measurementType,
                            VisualizerMeasurementType.AVERAGED)
                    )
                )
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                text = "%.1f".format((realtimeState.points.tail?.data?.avg ?: 0f) / 60f),
                color = LocalPalette.current.onSurface
            )
        }

        Row(
           modifier = Modifier
               .fillMaxWidth()
               .clickable {
                   onChangeMeasurementType(VisualizerMeasurementType.WEIGHTED)
               }
        ) {
            SpeedBBIcon(
                modifier = Modifier
                    .size(32.dp)
                    .padding(8.dp),
                colors = IconVectorColors(
                    fillColor = LocalPalette.current.onSurface.copy(
                        alpha = alpha(measurementType,
                            VisualizerMeasurementType.WEIGHTED)
                    ),
                    strokeColor = LocalPalette.current.onSurface.copy(
                        alpha = alpha(measurementType,
                            VisualizerMeasurementType.WEIGHTED)
                    )
                )
            )

            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                text = "%.1f".format((realtimeState.points.tail?.data?.weightedAvg?: 0f) / 60f),
                color = LocalPalette.current.onSurface
            )
        }

    }
}

internal data class BpmToolUiActions(
    val onUpdate: (RealtimeUiState<GraphPoint>) -> Unit = {},
    val onChangeMeasurementType: (VisualizerMeasurementType) -> Unit = {},
    val toggleApplyMeasurement: () -> Unit = {}
)

internal data class BpmToolUiState(
    val realtimeState: RealtimeUiState<GraphPoint> = RealtimeUiState(),
    val measurementType: VisualizerMeasurementType = VisualizerMeasurementType.INSTANT,
    val applyMeasurement: Boolean = false
)
