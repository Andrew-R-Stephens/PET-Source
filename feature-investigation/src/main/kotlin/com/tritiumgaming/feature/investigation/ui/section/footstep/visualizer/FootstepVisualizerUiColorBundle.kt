package com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer

import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.beatline.BeatLineUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphlabels.GraphLabelsUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.graphsurface.GraphSurfaceUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeplot.RealtimePlotUiColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.realtimeverticalmeter.RealtimeVerticalMeterColors

internal data class FootstepVisualizerUiColorBundle(
    val graphLabelsXAxisUiColors: GraphLabelsUiColors,
    val graphLabelsYAxisUiColors: GraphLabelsUiColors,
    val graphSurfaceUiColors: GraphSurfaceUiColors,
    val realtimePlotUiColors: RealtimePlotUiColors,
    val beatLineUiColors: BeatLineUiColors,
    val realtimeVerticalMeterColors: RealtimeVerticalMeterColors
)