package com.tritiumgaming.shared.data.mission.usecase

import com.tritiumgaming.shared.data.operation.usecase.GetOperationStateUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationGhostDetailsUseCase
import com.tritiumgaming.shared.data.operation.usecase.UpdateOperationMissionDataUseCase

data class MissionsUseCaseBundle(
    val getOperationStateUseCase: GetOperationStateUseCase,
    val updateOperationGhostDetailsUseCase: UpdateOperationGhostDetailsUseCase,
    val updateOperationMissionDataUseCase: UpdateOperationMissionDataUseCase
)
