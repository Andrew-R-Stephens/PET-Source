package com.tritiumgaming.shared.data.investigation.model

import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources

data class GhostTraitFilterOptions(
    val categories: List<GhostTraitResources.TraitCategory> = emptyList(),
    val weights: List<GhostTraitResources.TraitWeight> = emptyList(),
    val states: List<GhostTraitResources.TraitState> = emptyList(),
    val tags: List<GhostTraitResources.TraitTag> = emptyList(),
    val uniqueOnly: Boolean = false
)