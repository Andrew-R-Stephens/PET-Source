package com.tritiumgaming.shared.data.operation.usecase

import com.tritiumgaming.shared.data.customdifficulty.usecase.GetCustomDifficultiesUseCase

data class InvestigationUseCaseBundle(
    val getOperationStateUseCase: GetOperationStateUseCase,
    val updateInvestigationMapUseCase: UpdateInvestigationMapUseCase,
    val updateInvestigationDifficultyUseCase: UpdateInvestigationDifficultyUseCase,
    val updateInvestigationSanityUseCase: UpdateInvestigationSanityUseCase,
    val updateInvestigationPhaseUseCase: UpdateInvestigationPhaseUseCase,
    val updateInvestigationHuntWarningUseCase: UpdateInvestigationHuntWarningUseCase,
    val updateInvestigationEvidenceUseCase: UpdateInvestigationEvidenceUseCase,
    /*val updateGhostStates: UpdateInvestigationGhostStatesUseCase,*/
    val resetInvestigationUseCase: ResetInvestigationUseCase,
    val getCustomDifficultiesUseCase: GetCustomDifficultiesUseCase
)
