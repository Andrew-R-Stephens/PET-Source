package com.tritiumgaming.data.trait.dto

import com.tritiumgaming.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitDescription
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitIdentifier
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitState
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitWeight
import com.tritiumgaming.data.trait.model.GhostTrait

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