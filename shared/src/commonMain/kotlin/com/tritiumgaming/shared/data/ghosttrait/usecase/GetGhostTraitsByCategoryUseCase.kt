package com.tritiumgaming.shared.data.ghosttrait.usecase

import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait
import com.tritiumgaming.shared.data.ghosttrait.repository.GhostTraitRepository

class GetGhostTraitsByCategoryUseCase(
    val repository: GhostTraitRepository
) {
    operator fun invoke(category: TraitCategory): Result<List<GhostTrait>> {
        return repository.getByCategory(category)
    }
}