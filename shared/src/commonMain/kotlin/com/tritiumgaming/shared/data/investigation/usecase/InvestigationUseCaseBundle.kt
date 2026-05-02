package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.customdifficulty.usecase.GetCustomDifficultiesUseCase

data class InvestigationUseCaseBundle(
    val getInvestigationStateUseCase: GetInvestigationStateUseCase,
    val updateInvestigationMapUseCase: UpdateInvestigationMapUseCase,
    val updateInvestigationDifficultyUseCase: UpdateInvestigationDifficultyUseCase,
    val updateInvestigationSanityUseCase: UpdateInvestigationSanityUseCase,
    val updateInvestigationEvidenceUseCase: UpdateInvestigationEvidenceUseCase,
    /*val updateGhostStates: UpdateInvestigationGhostStatesUseCase,*/
    val resetInvestigationUseCase: ResetInvestigationUseCase,
    val getCustomDifficultiesUseCase: GetCustomDifficultiesUseCase
)
