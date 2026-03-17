package com.tritiumgaming.feature.investigation.ui.tool.traits

import com.tritiumgaming.shared.data.investigation.model.ValidatedGhostTrait

data class TraitListItemUiAction(
    val onToggle: (trait: ValidatedGhostTrait) -> Unit = {},
    val onInspect: () -> Unit = {}
)
