package com.tritiumgaming.feature.investigation.ui.toolbar.component

class ToolbarUiActions(
    val onToggleCollapseToolbar: () -> Unit,
    val onChangeToolbarCategory: (ToolbarUiState.Category) -> Unit,
    val onReset: () -> Unit
)