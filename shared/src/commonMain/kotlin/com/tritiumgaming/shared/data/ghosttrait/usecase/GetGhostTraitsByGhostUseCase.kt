package com.tritiumgaming.shared.data.ghosttrait.usecase

import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghosttrait.model.GhostTrait
import com.tritiumgaming.shared.data.ghosttrait.repository.GhostTraitRepository

class GetGhostTraitsByGhostUseCase(
    private val repository: GhostTraitRepository
) {
    operator fun invoke(ghostId: GhostIdentifier): Result<List<GhostTrait>> {
        return repository.getAllTraits().map { traits ->
            traits.filter { it.affectedGhosts.contains(ghostId) }
        }
    }
}