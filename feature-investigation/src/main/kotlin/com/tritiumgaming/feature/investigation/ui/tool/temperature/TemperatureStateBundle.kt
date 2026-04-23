package com.tritiumgaming.feature.investigation.ui.tool.temperature

import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData

data class TemperatureStateBundle(
    val temperatureUiState: TemperatureUiState,
    val fuseBoxState: DifficultyOverridesData.Companion.FuseBoxFlag,
    val temperatureUiActions: TemperatureUiActions
)

data class TemperatureUiActions(
    val onTogglePower: () -> Unit
)