package com.tritiumgaming.feature.investigation.ui.section.footstep

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.beatline.BeatLineUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels.GraphLabelsUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphsurface.GraphSurfaceUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeplot.RealtimePlotUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeverticalmeter.RealtimeVerticalMeterColors
import com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer.FootstepVisualizer
import com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer.FootstepVisualizerUiActions
import com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer.FootstepVisualizerUiColorBundle
import com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer.FootstepVisualizerUiStateBundle
import com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer.VisualizerMeasurementType
import kotlin.time.Duration.Companion.seconds

@Composable
fun ToolbarFootstepsVisualizerSection(
    modifier: Modifier = Modifier,
    state: ToolbarFootstepsVisualizerSectionUiState,
    actions: ToolbarFootstepsVisualizerSectionUiActions
) {

    val footstepVisualizerUiStateBundle = FootstepVisualizerUiStateBundle(
        alpha = .5f,
        range = 360,
        domain = 10.seconds.inWholeMilliseconds,
        domainInterval = 10f,
        rangeInterval = 120f,
        domainSampleInterval = 3.seconds.inWholeMilliseconds
    )

    val alpha: (thisType: VisualizerMeasurementType, testType: VisualizerMeasurementType) -> Float = {
            thisType, testType -> if(thisType == testType) 1f else .25f }

    val footstepVisualizerUiColorBundle = FootstepVisualizerUiColorBundle(
        realtimePlotUiColors = RealtimePlotUiColors(
            instant = LocalPalette.current.primary.copy(
                alpha = alpha(
                    state.measurementType,
                    VisualizerMeasurementType.INSTANT
                )
            ),
            averaged = LocalPalette.current.tertiary.copy(
                alpha = alpha(
                    state.measurementType,
                    VisualizerMeasurementType.AVERAGED
                )
            ),
            weighted = LocalPalette.current.secondary.copy(
                alpha = alpha(
                    state.measurementType,
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

    Box (
        modifier = modifier
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .preferredFrameRate(FrameRateCategory.Normal),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Icon(
                modifier = Modifier
                    .size(48.dp)
                    .padding(8.dp)
                    .clickable { actions.toggleApplyMeasurement() },
                tint = LocalPalette.current.onSurface.copy(
                    alpha = if(state.applyMeasurement) 1f else .25f),
                imageVector = ImageVector.vectorResource(R.drawable.ic_ghost),
                contentDescription = null
            )

            FootstepVisualizer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                stateBundle = footstepVisualizerUiStateBundle,
                colorBundle = footstepVisualizerUiColorBundle,
                actions = FootstepVisualizerUiActions(
                    onUpdate = { newTapUiState ->
                        actions.onUpdate(newTapUiState) }
                )
            )

            Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .clickable {
                       actions.onChangeMeasurementType(VisualizerMeasurementType.INSTANT)
                   }
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp),
                    tint = LocalPalette.current.onSurface.copy(
                        alpha = alpha(state.measurementType,
                            VisualizerMeasurementType.INSTANT)
                    ),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_circle_check),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    text = "${state.state.smoothed}",
                    color = LocalPalette.current.onSurface
                )
            }

            Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .clickable {
                       actions.onChangeMeasurementType(VisualizerMeasurementType.AVERAGED)
                   }
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp),
                    tint = LocalPalette.current.onSurface.copy(
                        alpha = alpha(state.measurementType,
                            VisualizerMeasurementType.AVERAGED)
                    ),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_circle_question),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    text = "${state.state.points.tail?.data?.avg?.let { it }}",
                    color = LocalPalette.current.onSurface
                )
            }

            Row(
               modifier = Modifier
                   .fillMaxWidth()
                   .clickable {
                       actions.onChangeMeasurementType(VisualizerMeasurementType.WEIGHTED)
                   }
            ) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(8.dp),
                    tint = LocalPalette.current.onSurface.copy(
                        alpha = alpha(state.measurementType,
                            VisualizerMeasurementType.WEIGHTED)
                    ),
                    imageVector = ImageVector.vectorResource(R.drawable.ic_circle_cancel),
                    contentDescription = null
                )

                Text(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    text = "${state.state.points.tail?.data?.weightedAvg?.let { it }}",
                    color = LocalPalette.current.onSurface
                )
            }

        }
    }

}
