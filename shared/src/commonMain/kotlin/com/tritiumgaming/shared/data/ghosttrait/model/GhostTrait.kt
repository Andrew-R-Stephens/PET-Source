package com.tritiumgaming.shared.data.ghosttrait.model

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitDescription
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitIdentifier
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitState
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitWeight

data class GhostTrait(
    val id: TraitIdentifier,
    val description: TraitDescription,
    val state: TraitState,
    val weight: TraitWeight,
    val category: TraitCategory,
    val affectedGhosts: List<GhostIdentifier>,
    val tags: List<TraitTag>
) {
    val isUnique: Boolean = state == TraitState.CONFIRM && weight == TraitWeight.DEFINITIVE
}
