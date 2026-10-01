package com.tritiumgaming.feature.investigation.ui.tool.traits

import com.tritiumgaming.data.trait.mapper.GhostTraitResources
import com.tritiumstudios.data.operation.model.ValidatedGhostTrait

data class TraitListUiActions(
    val onSelectCategory: (GhostTraitResources.TraitCategory) -> Unit,
    val onSelectTrait: (ValidatedGhostTrait) -> Unit,
    val onToggleUniqueOnly: () -> Unit
)
