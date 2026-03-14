package com.tritiumgaming.shared.data.ghosttrait.usecase

import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait
import com.tritiumgaming.shared.data.ghosttrait.repository.GhostTraitRepository

class GetAllGhostTraitsUseCase(
    val repository: GhostTraitRepository
) {
    operator fun invoke(): Result<List<GhostTrait>> {
        return repository.getAllTraits()
    }
}