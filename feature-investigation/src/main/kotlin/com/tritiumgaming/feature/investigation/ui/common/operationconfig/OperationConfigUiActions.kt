package com.tritiumgaming.feature.investigation.ui.common.operationconfig

class OperationConfigUiActions(
    val onMapLeftClick: () -> Unit = {},
    val onMapRightClick: () -> Unit = {},
    val onDifficultyLeftClick: () -> Unit = {},
    val onDifficultyRightClick: () -> Unit = {},
    val onToggleTimer: () -> Unit = {}
)