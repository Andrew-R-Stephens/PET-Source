package com.tritiumgaming.data.codex.usecase

import com.tritiumgaming.data.codex.repository.CodexRepository

class FetchEquipmentTypesUseCase(
    private val codexRepository: CodexRepository
) {
    operator fun invoke() = codexRepository.fetchEquipment()
}