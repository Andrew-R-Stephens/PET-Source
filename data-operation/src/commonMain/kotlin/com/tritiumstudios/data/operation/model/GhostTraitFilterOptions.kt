package com.tritiumstudios.data.operation.model

import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitState
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitWeight

data class GhostTraitFilterOptions(
    val categories: List<TraitCategory> = emptyList(),
    val weights: List<TraitWeight> = emptyList(),
    val states: List<TraitState> = emptyList(),
    val tags: List<TraitTag> = emptyList(),
    val uniqueOnly: Boolean = false
)