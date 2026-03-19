package com.tritiumgaming.feature.investigation.ui.tool.traits

import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.investigation.model.ValidatedGhostTrait

data class TraitListUiActions(
    val onSelectCategory: (TraitCategory) -> Unit,
    val onSelectTrait: (ValidatedGhostTrait) -> Unit,
    val onToggleUniqueOnly: () -> Unit
)
