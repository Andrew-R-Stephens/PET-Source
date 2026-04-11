package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.investigation.InvestigationRepository
import com.tritiumgaming.shared.data.investigation.model.DifficultyData

class UpdateInvestigationDifficultyUseCase(
    private val repository: InvestigationRepository
) {
    operator fun invoke(difficulty: DifficultyData) =
        repository.updateDifficulty(difficulty)
}
