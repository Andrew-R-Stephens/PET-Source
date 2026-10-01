package com.tritiumgaming.data.trait.model

import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.data.trait.mapper.GhostTraitResources
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitDescription
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitIdentifier
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitWeight

data class GhostTrait(
    val id: TraitIdentifier,
    val description: TraitDescription,
    val state: GhostTraitResources.TraitState,
    val weight: TraitWeight,
    val category: TraitCategory,
    val affectedGhosts: List<GhostIdentifier>,
    val tags: List<TraitTag>
) {
    val isUnique: Boolean = state == GhostTraitResources.TraitState.CONFIRM && weight == TraitWeight.DEFINITIVE
}
