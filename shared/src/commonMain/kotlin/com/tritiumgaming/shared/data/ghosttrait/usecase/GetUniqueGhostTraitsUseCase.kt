package com.tritiumgaming.shared.data.ghosttrait.usecase

import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait
import com.tritiumgaming.shared.data.ghosttrait.repository.GhostTraitRepository

class GetUniqueGhostTraitsUseCase(
    private val repository: GhostTraitRepository
) {
    operator fun invoke(ifUnique: Boolean): Result<List<GhostTrait>> {
        return repository.getAllTraits().map { traits ->
            traits.filter { it.isUnique }
        }
    }
}