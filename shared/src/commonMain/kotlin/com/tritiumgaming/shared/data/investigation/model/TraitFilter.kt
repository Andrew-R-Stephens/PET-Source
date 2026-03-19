package com.tritiumgaming.shared.data.investigation.model

import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitState
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitWeight

data class TraitFilter(
    val category: TraitCategory? = null,
    val weight: TraitWeight? = null,
    val state: TraitState? = null,
    val tags: List<TraitTag> = emptyList(),
    val uniqueOnly: Boolean = false
)
