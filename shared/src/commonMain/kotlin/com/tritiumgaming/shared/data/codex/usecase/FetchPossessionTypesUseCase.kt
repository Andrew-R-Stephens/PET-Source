package com.tritiumgaming.shared.data.codex.usecase

import com.tritiumgaming.shared.data.codex.repository.CodexRepository

class FetchPossessionTypesUseCase(
    private val codexRepository: CodexRepository
) {
    operator fun invoke() = codexRepository.fetchPossessions()
}