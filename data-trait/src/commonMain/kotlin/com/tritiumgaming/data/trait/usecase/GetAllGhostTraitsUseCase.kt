package com.tritiumgaming.data.trait.usecase

import com.tritiumgaming.data.trait.model.GhostTrait
import com.tritiumgaming.data.trait.repository.GhostTraitRepository

class GetAllGhostTraitsUseCase(
    private val repository: GhostTraitRepository
) {
    operator fun invoke(): Result<List<GhostTrait>> {
        return repository.getAllTraits()
    }
}