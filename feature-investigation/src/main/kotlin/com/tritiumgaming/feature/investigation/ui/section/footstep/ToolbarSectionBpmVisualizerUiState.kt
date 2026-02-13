package com.tritiumgaming.feature.investigation.ui.section.footstep

import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.PointRecord
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer.VisualizerMeasurementType

data class ToolbarSectionBpmVisualizerUiState(
    val state: RealtimeUiState<PointRecord> = RealtimeUiState(),
    val measurementType: VisualizerMeasurementType = VisualizerMeasurementType.INSTANT,
    val applyMeasurement: Boolean = false
)