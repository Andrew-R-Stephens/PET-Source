package com.tritiumgaming.shared.operation.domain.codex.usecase

import com.tritiumgaming.shared.operation.domain.codex.repository.CodexRepository

class FetchPossessionTypesUseCase(
        private val codexRepository: CodexRepository
    ) {
        operator fun invoke() = codexRepository.fetchPossessions()
    }