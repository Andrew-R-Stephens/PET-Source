package com.tritiumgaming.shared.data.investigation.model

import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitState
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitWeight

data class GhostTraitFilterUiOptions(
    val category: List<CategoryOption> = emptyList(),
    val weight: List<WeightOption> = emptyList(),
    val state: List<StateOption> = emptyList(),
    val tags: List<TagOption> = emptyList(),
    val uniqueOnly: Boolean? = null
)

data class CategoryOption(
    val data: TraitCategory? = null,
    val state: Boolean = false
)

data class WeightOption(
    val data: TraitWeight? = null,
    val state: Boolean = false
)

data class StateOption(
    val data: TraitState? = null,
    val state: Boolean = false
)

data class TagOption(
    val data: TraitTag? = null,
    val state: Boolean = false
)
