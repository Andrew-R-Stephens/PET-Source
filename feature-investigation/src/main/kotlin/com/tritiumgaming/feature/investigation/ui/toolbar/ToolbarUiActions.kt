package com.tritiumgaming.feature.investigation.ui.toolbar

import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState

class ToolbarUiActions(
    val onToggleCollapseToolbar: () -> Unit = {},
    val onChangeToolbarCategory: (OperationToolbarUiState.Category) -> Unit = { },
    val onReset: () -> Unit = {}
)