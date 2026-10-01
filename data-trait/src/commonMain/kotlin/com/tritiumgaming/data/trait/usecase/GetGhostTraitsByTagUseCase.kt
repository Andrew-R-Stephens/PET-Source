package com.tritiumgaming.data.trait.usecase

import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.data.trait.model.GhostTrait
import com.tritiumgaming.data.trait.repository.GhostTraitRepository

class GetGhostTraitsByTagUseCase(
    private val repository: GhostTraitRepository
) {
    operator fun invoke(tag: TraitTag): Result<List<GhostTrait>> {
        return repository.getAllTraits().map { traits ->
            traits.filter { it.tags.contains(tag) }
        }
    }
}