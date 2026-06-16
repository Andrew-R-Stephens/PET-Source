package com.tritiumgaming.shared.data.investigation.usecase

import com.tritiumgaming.shared.data.customdifficulty.usecase.GetCustomDifficultiesUseCase
import com.tritiumgaming.shared.data.operation.usecase.GetOperationStateUseCase
import com.tritiumgaming.shared.data.operation.usecase.ResetOperationUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationDifficultyUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationEvidenceUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationGhostDetailsUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationHuntWarningUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationMapUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationMissionDataUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationPhaseUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationSanityUseCase

data class InvestigationUseCaseBundle(
    val getOperationStateUseCase: GetOperationStateUseCase,
    val updateOperationMapUseCase: UpdateOperationMapUseCase,
    val updateOperationDifficultyUseCase: UpdateOperationDifficultyUseCase,
    val updateOperationSanityUseCase: UpdateOperationSanityUseCase,
    val updateOperationPhaseUseCase: UpdateOperationPhaseUseCase,
    val updateOperationHuntWarningUseCase: UpdateOperationHuntWarningUseCase,
    val updateOperationEvidenceUseCase: UpdateOperationEvidenceUseCase,
    val updateOperationGhostDetailsUseCase: UpdateOperationGhostDetailsUseCase,
    val updateOperationMissionDataUseCase: UpdateOperationMissionDataUseCase,
    /*val updateGhostStates: UpdateInvestigationGhostStatesUseCase,*/
    val resetOperationUseCase: ResetOperationUseCase,
    val getCustomDifficultiesUseCase: GetCustomDifficultiesUseCase
)
