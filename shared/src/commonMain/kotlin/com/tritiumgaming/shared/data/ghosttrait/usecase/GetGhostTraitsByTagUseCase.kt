package com.tritiumgaming.shared.data.ghosttrait.usecase

import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitTag
import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait
import com.tritiumgaming.shared.data.ghosttrait.repository.GhostTraitRepository

class GetGhostTraitsByTagUseCase(
    private val repository: GhostTraitRepository
) {
    operator fun invoke(tag: TraitTag): Result<List<GhostTrait>> {
        return repository.getAllTraits().map { traits ->
            traits.filter { it.tags.contains(tag) }
        }
    }
}