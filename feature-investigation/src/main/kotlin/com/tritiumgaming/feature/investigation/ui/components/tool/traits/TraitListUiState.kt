package com.tritiumgaming.feature.investigation.ui.components.tool.traits

import com.tritiumstudios.data.operation.model.GhostTraitFilterUiOptions
import com.tritiumstudios.data.operation.model.ValidatedGhostTrait

data class TraitListUiState(
    val options: GhostTraitFilterUiOptions,
    val list: List<ValidatedGhostTrait>
)
