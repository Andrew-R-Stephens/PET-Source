package com.tritiumgaming.data.trait.dto

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitDescription
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitIdentifier
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitState
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitWeight
import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait

data class GhostTraitDto(
    val id: TraitIdentifier,
    val description: TraitDescription,
    val state: TraitState,
    val weight: TraitWeight,
    val category: TraitCategory,
    val affectedGhosts: List<GhostIdentifier>,
    val tags: List<TraitTag>
)

fun GhostTraitDto.toDomain() = GhostTrait(
    id = id,
    description = description,
    state = state,
    weight = weight,
    category = category,
    affectedGhosts = affectedGhosts,
    tags = tags
)

fun List<GhostTraitDto>.toDomain() = map { it.toDomain() }