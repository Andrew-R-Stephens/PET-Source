package com.tritiumgaming.shared.data.ghosttrait.usecase

import com.tritiumgaming.shared.data.ghosttrait.repository.GhostTraitRepository

class GetGhostTraitDescriptionUseCase(
    private val repository: GhostTraitRepository
) {
    operator fun invoke(resId: Int): String {
        return repository.getString(resId)
    }
}
