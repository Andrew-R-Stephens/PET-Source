package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.usecase

import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.codex.repository.CodexRepository

class FetchCodexEquipmentUseCase(
        private val codexRepository: CodexRepository
    ) {
        operator fun invoke() = codexRepository.fetchEquipment()
    }