package com.tritiumgaming.shared.data.codex.usecase

class FetchPossessionTypesUseCase(
        private val codexRepository: com.tritiumgaming.shared.data.codex.repository.CodexRepository
    ) {
        operator fun invoke() = codexRepository.fetchPossessions()
    }