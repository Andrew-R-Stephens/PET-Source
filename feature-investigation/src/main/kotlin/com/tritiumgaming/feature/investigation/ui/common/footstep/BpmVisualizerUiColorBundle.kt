package com.tritiumgaming.feature.investigation.ui.common.footstep

import com.tritiumgaming.feature.investigation.ui.common.footstep.beatline.BeatLineUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphlabels.GraphLabelsUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphsurface.GraphSurfaceUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeplot.RealtimePlotUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeverticalmeter.RealtimeVerticalMeterColors

internal data class BpmVisualizerUiColorBundle(
    val graphLabelsXAxisUiColors: GraphLabelsUiColors,
    val graphLabelsYAxisUiColors: GraphLabelsUiColors,
    val graphSurfaceUiColors: GraphSurfaceUiColors,
    val realtimePlotUiColors: RealtimePlotUiColors,
    val beatLineUiColors: BeatLineUiColors,
    val realtimeVerticalMeterColors: RealtimeVerticalMeterColors
)