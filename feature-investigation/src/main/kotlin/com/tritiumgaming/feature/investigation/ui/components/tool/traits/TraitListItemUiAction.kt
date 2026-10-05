package com.tritiumgaming.feature.investigation.ui.components.tool.traits

import com.tritiumstudios.data.operation.model.ValidatedGhostTrait

data class TraitListItemUiAction(
    val onToggle: (trait: ValidatedGhostTrait) -> Unit = {},
    val onInspect: () -> Unit = {}
)
