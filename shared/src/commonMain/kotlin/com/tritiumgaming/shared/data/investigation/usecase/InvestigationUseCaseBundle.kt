package com.tritiumgaming.shared.data.investigation.usecase

data class InvestigationUseCaseBundle(
    val getInvestigationStateUseCase: GetInvestigationStateUseCase,
    val updateInvestigationMapUseCase: UpdateInvestigationMapUseCase,
    val updateInvestigationDifficultyUseCase: UpdateInvestigationDifficultyUseCase,
    val updateInvestigationSanityUseCase: UpdateInvestigationSanityUseCase,
    val updateInvestigationEvidenceUseCase: UpdateInvestigationEvidenceUseCase,
    /*val updateGhostStates: UpdateInvestigationGhostStatesUseCase,*/
    val resetInvestigationUseCase: ResetInvestigationUseCase
)
