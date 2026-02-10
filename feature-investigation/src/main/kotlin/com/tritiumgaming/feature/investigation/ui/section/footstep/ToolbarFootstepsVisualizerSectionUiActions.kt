package com.tritiumgaming.feature.investigation.ui.section.footstep

import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.PointRecord
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.feature.investigation.ui.section.footstep.visualizer.VisualizerMeasurementType

data class ToolbarFootstepsVisualizerSectionUiActions(
    val onUpdate: (RealtimeUiState<PointRecord>) -> Unit,
    val onChangeMeasurementType: (VisualizerMeasurementType) -> Unit,
    val toggleApplyMeasurement: () -> Unit
)