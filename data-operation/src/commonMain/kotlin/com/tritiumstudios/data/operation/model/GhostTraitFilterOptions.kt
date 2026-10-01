package com.tritiumstudios.data.operation.model

import com.tritiumgaming.data.trait.mapper.GhostTraitResources

data class GhostTraitFilterOptions(
    val categories: List<GhostTraitResources.TraitCategory> = emptyList(),
    val weights: List<GhostTraitResources.TraitWeight> = emptyList(),
    val states: List<GhostTraitResources.TraitState> = emptyList(),
    val tags: List<GhostTraitResources.TraitTag> = emptyList(),
    val uniqueOnly: Boolean = false
)