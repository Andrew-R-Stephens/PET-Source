package com.tritiumgaming.data.trait.usecase

import com.tritiumgaming.data.trait.model.GhostTrait
import com.tritiumgaming.data.trait.repository.GhostTraitRepository

class GetUniqueGhostTraitsUseCase(
    private val repository: GhostTraitRepository
) {
    operator fun invoke(ifUnique: Boolean): Result<List<GhostTrait>> {
        return repository.getAllTraits().map { traits ->
            traits.filter { it.isUnique }
        }
    }
}