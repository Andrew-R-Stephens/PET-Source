package com.tritiumgaming.feature.investigation.ui.toolbar.component

class OperationConfigUiActions(
    val onMapLeftClick: () -> Unit = {},
    val onMapRightClick: () -> Unit = {},
    val onDifficultyLeftClick: () -> Unit = {},
    val onDifficultyRightClick: () -> Unit = {},
    val onToggleTimer: () -> Unit = {}
)
