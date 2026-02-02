package com.tritiumgaming.shared.data.codex.usecase

import com.tritiumgaming.shared.data.codex.repository.CodexRepository

class FetchEquipmentTypesUseCase(
    private val codexRepository: CodexRepository
) {
    operator fun invoke() = codexRepository.fetchEquipment()
}