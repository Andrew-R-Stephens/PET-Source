package com.tritiumgaming.feature.investigation.ui.tool.traits

import com.tritiumgaming.shared.data.investigation.model.GhostTraitFilterUiOptions
import com.tritiumgaming.shared.data.investigation.model.ValidatedGhostTrait

data class TraitListUiState(
    val options: GhostTraitFilterUiOptions,
    val list: List<ValidatedGhostTrait>
)
