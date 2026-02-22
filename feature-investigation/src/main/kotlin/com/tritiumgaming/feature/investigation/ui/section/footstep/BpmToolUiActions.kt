package com.tritiumgaming.feature.investigation.ui.section.footstep

import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.GraphPoint
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer.VisualizerMeasurementType

data class BpmToolUiActions(
    val onUpdate: (RealtimeUiState<GraphPoint>) -> Unit = {},
    val onChangeMeasurementType: (VisualizerMeasurementType) -> Unit = {},
    val toggleApplyMeasurement: () -> Unit = {}
)