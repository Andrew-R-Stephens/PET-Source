package com.tritiumgaming.data.trait.usecase

import com.tritiumgaming.data.trait.repository.GhostTraitRepository

class GetGhostTraitDescriptionUseCase(
    private val repository: GhostTraitRepository
) {
    operator fun invoke(resId: Int): String {
        return repository.getString(resId)
    }
}
