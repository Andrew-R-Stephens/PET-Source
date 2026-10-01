package com.tritiumgaming.data.trait.usecase

import com.tritiumgaming.data.trait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.data.trait.model.GhostTrait
import com.tritiumgaming.data.trait.repository.GhostTraitRepository

class GetGhostTraitsByCategoryUseCase(
    private val repository: GhostTraitRepository
) {
    operator fun invoke(category: TraitCategory): Result<List<GhostTrait>> {
        return repository.getByCategory(category)
    }
}