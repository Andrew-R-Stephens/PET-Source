package com.tritiumstudios.data.operation.model

import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitState
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitWeight

data class TraitFilter(
    val category: TraitCategory = TraitCategory.ALL,
    val weight: TraitWeight? = null,
    val state: TraitState? = null,
    val tags: List<TraitTag> = emptyList(),
    val uniqueOnly: Boolean = false
)
